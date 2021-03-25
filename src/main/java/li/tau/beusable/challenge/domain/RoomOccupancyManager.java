package li.tau.beusable.challenge.domain;

import lombok.Value;
import org.apache.commons.lang3.ArrayUtils;

import java.util.EnumMap;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;
import static java.util.Comparator.naturalOrder;
import static java.util.Map.of;
import static li.tau.beusable.challenge.domain.RoomType.ECONOMY;
import static li.tau.beusable.challenge.domain.RoomType.PREMIUM;

@Value
public class RoomOccupancyManager {

    int premium;
    int economy;

    public RoomOccupancyManager(int premium, int economy) {
        checkArgument(premium >= 0, "Premium rooms number should be positive or zero.");
        checkArgument(economy >= 0, "Economy rooms number should be positive or zero.");
        this.premium = premium;
        this.economy = economy;
    }

    public EnumMap<RoomType, Occupancy> bid(int[] guestsBids) {
        Stack<Integer> bids = new Stack<>();
        bids.addAll(asList(ArrayUtils.toObject(guestsBids)));
        bids.sort(naturalOrder());

        int availablePremium = premium;
        Occupancy premiumOccupancy = new Occupancy();

        int availableEconomy = economy;
        Occupancy economyOccupancy = new Occupancy();

        // Fill premium rooms
        while (!bids.isEmpty()
                && bids.peek() >= 100
                && availablePremium > 0) {
            premiumOccupancy.book(bids.pop());
            --availablePremium;
        }

        // No more premium bids will be satisfied
        bids.removeIf(bid -> bid >= 100);

        // If premium rooms left, fill with top economy bids,
        // but left enough to fill all economy rooms
        while (availablePremium > 0
                && bids.size() > availableEconomy) {
            premiumOccupancy.book(bids.pop());
            --availablePremium;
        }

        // Fill economy rooms
        while (bids.size() > 0
                && availableEconomy > 0) {
            economyOccupancy.book(bids.pop());
            --availableEconomy;
        }

        return new EnumMap<>(of(
                PREMIUM, premiumOccupancy,
                ECONOMY, economyOccupancy
        ));
    }
}
