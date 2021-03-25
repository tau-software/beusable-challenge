package li.tau.beusable.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Occupancy {

    private int rooms = 0;

    private int money = 0;

    public void book(int price) {
        this.money += price;
        this.rooms++;
    }
}
