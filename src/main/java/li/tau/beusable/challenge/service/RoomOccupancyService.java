package li.tau.beusable.challenge.service;

import li.tau.beusable.challenge.domain.Occupancy;
import li.tau.beusable.challenge.domain.RoomOccupancyManager;
import li.tau.beusable.challenge.domain.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
@RequiredArgsConstructor
public class RoomOccupancyService {

    private final TracingService tracingService;

    public EnumMap<RoomType, Occupancy> bid(int premium, int economy, int[] bids) {
        tracingService.trace(premium, economy, bids);

        var manager = new RoomOccupancyManager(premium, economy);

        return manager.bid(bids);
    }

}
