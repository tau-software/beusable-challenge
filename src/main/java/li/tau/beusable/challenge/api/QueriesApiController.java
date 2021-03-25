package li.tau.beusable.challenge.api;

import li.tau.beusable.challenge.domain.Query;
import li.tau.beusable.challenge.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueriesApiController {

    private final QueryRepository queryRepository;

    @GetMapping("/queries")
    public List<Query> getQueries() {
        return queryRepository.findAll();
    }

}
