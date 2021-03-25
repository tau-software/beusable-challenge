package li.tau.beusable.challenge.repository;

import li.tau.beusable.challenge.domain.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {
}
