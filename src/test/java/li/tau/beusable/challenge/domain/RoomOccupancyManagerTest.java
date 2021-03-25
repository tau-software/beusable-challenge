package li.tau.beusable.challenge.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EnumMap;

import static li.tau.beusable.challenge.domain.RoomType.ECONOMY;
import static li.tau.beusable.challenge.domain.RoomType.PREMIUM;
import static org.assertj.core.api.Assertions.assertThat;

class RoomOccupancyManagerTest {

    static final int[] IN_GUESTS_BIDS = new int[]{23, 45, 155, 374, 22, 99, 100, 101, 115, 209};

    static final int[][] IN_FREE_ROOMS = new int[][]{
            new int[]{3, 3},
            new int[]{7, 5},
            new int[]{2, 7},
            new int[]{7, 1}};

    static final int[][] OUT_OCCUPIED_ROOMS = new int[][]{
            new int[]{3, 3},
            new int[]{6, 4},
            new int[]{2, 4},
            new int[]{7, 1}};

    static final int[][] OUT_MONEY = new int[][]{
            new int[]{738, 167},
            new int[]{1054, 189},
            new int[]{583, 189},
            new int[]{1153, 45}};

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void should_pass_base(int testIndex) {
        // given
        int[] freeRooms = IN_FREE_ROOMS[testIndex - 1];
        RoomOccupancyManager testedObject = new RoomOccupancyManager(freeRooms[0], freeRooms[1]);

        // when
        EnumMap<RoomType, Occupancy> result = testedObject.bid(IN_GUESTS_BIDS);

        // then
        int[] correctOccupiedRooms = OUT_OCCUPIED_ROOMS[testIndex - 1];
        int[] correctMoney = OUT_MONEY[testIndex - 1];
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .hasEntrySatisfying(PREMIUM, occupancy -> {
                    assertThat(occupancy)
                            .extracting(Occupancy::getRooms)
                            .isEqualTo(correctOccupiedRooms[0]);
                    assertThat(occupancy)
                            .extracting(Occupancy::getMoney)
                            .isEqualTo(correctMoney[0]);
                })
                .hasEntrySatisfying(ECONOMY, occupancy -> {
                    assertThat(occupancy)
                            .extracting(Occupancy::getRooms)
                            .isEqualTo(correctOccupiedRooms[1]);
                    assertThat(occupancy)
                            .extracting(Occupancy::getMoney)
                            .isEqualTo(correctMoney[1]);
                });
    }

}
