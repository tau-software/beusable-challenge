package li.tau.beusable.challenge.api;

import li.tau.beusable.challenge.service.RoomOccupancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static li.tau.beusable.challenge.domain.RoomType.ECONOMY;
import static li.tau.beusable.challenge.domain.RoomType.PREMIUM;

@RestController
@RequiredArgsConstructor
public class RoomOccupancyApiController {

    private final RoomOccupancyService roomOccupancyService;

    @PostMapping("/bid")
    public BidResponse bid(@RequestBody @Valid BidRequest bidRequest) {
        var result = roomOccupancyService.bid(
                bidRequest.getRooms().getPremium(),
                bidRequest.getRooms().getEconomy(),
                bidRequest.getBids());

        return new BidResponse(
                result.get(PREMIUM),
                result.get(ECONOMY));
    }

}
