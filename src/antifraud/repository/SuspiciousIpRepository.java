package antifraud.repository;

import antifraud.model.SusIp.SusIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousIpRepository extends CrudRepository<SusIp, Long> {

    boolean existsByIp(String ip);
    int deleteByIp(String ip);
}
