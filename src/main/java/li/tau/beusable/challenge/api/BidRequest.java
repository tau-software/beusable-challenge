package li.tau.beusable.challenge.api;

import lombok.Value;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Value
@ScriptAssert(
        lang = "javascript",
        script = "_this.bids != null " +
                "&& (function () {" +
                "  for (bidIdx in _this.bids) { " +
                "    if (_this.bids[bidIdx] < 0) return false;" +
                "  }" +
                "  return true;" +
                "})()",
        message = "All bids should be greater than or equal to zero.")
public class BidRequest {

    @NotNull
    @Valid
    Rooms rooms;
    @NotNull
    int[] bids;

    @Value
    public static class Rooms {
        @PositiveOrZero
        int premium;
        @PositiveOrZero
        int economy;
    }

}
