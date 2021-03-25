package li.tau.beusable.challenge.domain;

import lombok.Value;
import org.apache.commons.lang3.ArrayUtils;

import java.util.EnumMap;
import java.util.Stack;

import static java.util.Arrays.asList;
import static java.util.Comparator.naturalOrder;
import static li.tau.beusable.challenge.domain.RoomType.ECONOMY;
import static li.tau.beusable.challenge.domain.RoomType.PREMIUM;

@Value
public class RoomOccupancyManager {

    int premium;
    int economy;

    public EnumMap<RoomType, Occupancy> bid(int[] guestsBids) {
        Stack<Integer> bids = new Stack<>();
        bids.addAll(asList(ArrayUtils.toObject(guestsBids)));
        bids.sort(naturalOrder());

        int availablePremium = premium;
        int moneyPremium = 0;
        int roomsPremium = 0;

        int availableEconomy = economy;
        int moneyEconomy = 0;
        int roomsEconomy = 0;

        // Fill premium rooms
        while (!bids.isEmpty()
                && bids.peek() >= 100
                && availablePremium > 0) {
            moneyPremium += bids.pop();
            ++roomsPremium;
            --availablePremium;
        }

        // No more premium bids will be satisfied
        bids.removeIf(bid -> bid >= 100);

        // If premium rooms left, fill with top economy bids,
        // but left enough to fill all economy rooms
        while (availablePremium > 0
                && bids.size() > availableEconomy) {
            moneyPremium += bids.pop();
            ++roomsPremium;
            --availablePremium;
        }

        // Fill economy rooms
        while (bids.size() > 0
                && availableEconomy > 0) {
            moneyEconomy += bids.pop();
            ++roomsEconomy;
            --availableEconomy;
        }

        EnumMap<RoomType, Occupancy> result = new EnumMap<>(RoomType.class);
        result.put(PREMIUM, new Occupancy(roomsPremium, moneyPremium));
        result.put(ECONOMY, new Occupancy(roomsEconomy, moneyEconomy));

        return result;
    }
}
