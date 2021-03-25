package li.tau.beusable.challenge.service;

import li.tau.beusable.challenge.domain.Query;
import li.tau.beusable.challenge.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TracingService {

    private final QueryRepository queryRepository;

    @Async
    public void trace(int premium, int economy, int[] bids) {
        Query query = Query.builder()
                .premium(premium)
                .economy(economy)
                .bids(bids)
                .build();

        queryRepository.save(query);
    }

}
