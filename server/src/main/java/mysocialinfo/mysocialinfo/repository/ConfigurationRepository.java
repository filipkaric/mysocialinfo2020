package mysocialinfo.mysocialinfo.repository;

import mysocialinfo.mysocialinfo.models.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
    Configuration findByNameAndSocialNetworkId(String name, long socialNetworkId);
}
