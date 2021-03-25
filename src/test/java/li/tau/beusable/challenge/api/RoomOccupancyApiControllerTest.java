package li.tau.beusable.challenge.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import li.tau.beusable.challenge.CodingChallengeApplicationTests;
import li.tau.beusable.challenge.api.BidRequest.Rooms;
import li.tau.beusable.challenge.domain.Occupancy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static li.tau.beusable.challenge.domain.RoomOccupancyManagerTest.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class RoomOccupancyApiControllerTest extends CodingChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void should_pass_base(int testIndex) throws Exception {
        // given
        int[] freeRooms = IN_FREE_ROOMS[testIndex - 1];
        var request = new BidRequest(new Rooms(freeRooms[0], freeRooms[1]), IN_GUESTS_BIDS);

        // when
        var result = mockMvc.perform(
                post("/bid")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        int[] correctOccupiedRooms = OUT_OCCUPIED_ROOMS[testIndex - 1];
        int[] correctMoney = OUT_MONEY[testIndex - 1];
        var correctResponse = new BidResponse(
                new Occupancy(correctOccupiedRooms[0], correctMoney[0]),
                new Occupancy(correctOccupiedRooms[1], correctMoney[1])
        );
        result
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(correctResponse)));
    }

    @Test
    void should_400_when_no_rooms() throws Exception {
        // given
        var request = new BidRequest(null, IN_GUESTS_BIDS);

        // then
        var result = mockMvc.perform(
                post("/bid")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result
                .andExpect(status().is(400));
    }

    @Test
        // TODO can be parameterized with should_400_when_no_rooms
        // and extended with more inproper request data
    void should_400_when_negative_bid() throws Exception {
        // given
        int[] guestsBidsWithNegative = IN_GUESTS_BIDS.clone();
        guestsBidsWithNegative[3] = -12;
        var request = new BidRequest(new Rooms(3, 3), guestsBidsWithNegative);

        // then
        var result = mockMvc.perform(
                post("/bid")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result
                .andExpect(status().is(400));
    }

}