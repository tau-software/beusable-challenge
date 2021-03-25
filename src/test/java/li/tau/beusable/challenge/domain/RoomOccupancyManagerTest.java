package li.tau.beusable.challenge.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static li.tau.beusable.challenge.domain.RoomType.ECONOMY;
import static li.tau.beusable.challenge.domain.RoomType.PREMIUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoomOccupancyManagerTest {

    public static final int[] IN_GUESTS_BIDS = new int[]{23, 45, 155, 374, 22, 99, 100, 101, 115, 209};

    public static final int[][] IN_FREE_ROOMS = new int[][]{
            new int[]{3, 3},
            new int[]{7, 5},
            new int[]{2, 7},
            new int[]{7, 1}};

    public static final int[][] OUT_OCCUPIED_ROOMS = new int[][]{
            new int[]{3, 3},
            new int[]{6, 4},
            new int[]{2, 4},
            new int[]{7, 1}};

    public static final int[][] OUT_MONEY = new int[][]{
            new int[]{738, 167},
            new int[]{1054, 189},
            new int[]{583, 189},
            new int[]{1153, 45}};

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void should_pass_base(int testIndex) {
        // given
        int[] freeRooms = IN_FREE_ROOMS[testIndex - 1];
        var testedObject = new RoomOccupancyManager(freeRooms[0], freeRooms[1]);

        // when
        var result = testedObject.bid(IN_GUESTS_BIDS);

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

    @ParameterizedTest
    @CsvSource({"-3, 2", "4,-3", "-8,-9"})
    void should_throwException_when_negative_rooms(int premium, int economy) {
        // when
        var assertion = assertThatThrownBy(() -> {
            new RoomOccupancyManager(premium, economy);
        });

        // then
        assertion.isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_handle_when_empty_bids() {
        // given
        var testedObject = new RoomOccupancyManager(3, 3);
        var emptyBids = new int[0];

        // when
        var result = testedObject.bid(emptyBids);

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .hasEntrySatisfying(PREMIUM, occupancy -> {
                    assertThat(occupancy)
                            .extracting(Occupancy::getRooms)
                            .isEqualTo(0);
                    assertThat(occupancy)
                            .extracting(Occupancy::getMoney)
                            .isEqualTo(0);
                })
                .hasEntrySatisfying(ECONOMY, occupancy -> {
                    assertThat(occupancy)
                            .extracting(Occupancy::getRooms)
                            .isEqualTo(0);
                    assertThat(occupancy)
                            .extracting(Occupancy::getMoney)
                            .isEqualTo(0);
                });
    }

}
