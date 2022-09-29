package antifraud.repository;

import antifraud.model.StolenCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StolenCardRepository extends CrudRepository<StolenCard, Long> {
    boolean existsByNumber(String number);

    int deleteStolenCardByNumber(String number);
}
