package li.tau.beusable.challenge.domain;

import li.tau.beusable.challenge.repository.IntArrayJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Query {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int premium;

    private int economy;

    @Convert(converter = IntArrayJsonConverter.class)
    @Column(columnDefinition = "text")
    private int[] bids;

}
