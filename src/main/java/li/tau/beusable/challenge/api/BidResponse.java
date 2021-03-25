package li.tau.beusable.challenge.api;

import li.tau.beusable.challenge.domain.Occupancy;
import lombok.Value;

@Value
public class BidResponse {

    Occupancy premium;

    Occupancy economy;

}
