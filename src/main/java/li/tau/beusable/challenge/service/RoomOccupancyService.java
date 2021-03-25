package li.tau.beusable.challenge.service;

import li.tau.beusable.challenge.domain.Occupancy;
import li.tau.beusable.challenge.domain.RoomOccupancyManager;
import li.tau.beusable.challenge.domain.RoomType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
public class RoomOccupancyService {

    public EnumMap<RoomType, Occupancy> bid(int premium, int economy, int[] bids) {
        var manager = new RoomOccupancyManager(premium, economy);

        return manager.bid(bids);
    }

}
