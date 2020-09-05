package mysocialinfo.mysocialinfo.repository;

import mysocialinfo.mysocialinfo.models.SocialData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialDataRepository extends CrudRepository<SocialData, Long> {
}
