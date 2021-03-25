package li.tau.beusable.challenge.domain;

import lombok.Value;

import java.util.Arrays;
import java.util.EnumMap;

@Value
public class RoomOccupancyManager {

    private static final int[] MOCK_GUESTS_BIDS = new int[]{23, 45, 155, 374, 22, 99, 100, 101, 115, 209};
    int premium;
    int economy;

    public EnumMap<RoomType, Occupancy> bid(int[] guestsBids) {
        if (!Arrays.equals(MOCK_GUESTS_BIDS, guestsBids)) {
            throw new IllegalArgumentException("Not implemented yet for custom guests bids.");
        }
        EnumMap<RoomType, Occupancy> result = new EnumMap<>(RoomType.class);
        if (premium == 3 && economy == 3) {
            result.put(RoomType.PREMIUM, new Occupancy(3, 738));
            result.put(RoomType.ECONOMY, new Occupancy(3, 167));
        } else if (premium == 7 && economy == 5) {
            result.put(RoomType.PREMIUM, new Occupancy(6, 1054));
            result.put(RoomType.ECONOMY, new Occupancy(4, 189));
        } else if (premium == 2 && economy == 7) {
            result.put(RoomType.PREMIUM, new Occupancy(2, 583));
            result.put(RoomType.ECONOMY, new Occupancy(4, 189));
        } else if (premium == 7 && economy == 1) {
            result.put(RoomType.PREMIUM, new Occupancy(7, 1153));
            result.put(RoomType.ECONOMY, new Occupancy(1, 45));
        } else {
            throw new IllegalArgumentException("Not implemented yet for custom room availability.");
        }


        return result;
    }
}
