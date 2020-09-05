package mysocialinfo.mysocialinfo.repository;

import mysocialinfo.mysocialinfo.models.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findByUserIdAndSocialNetworkId(long userId, long socialNetworkId);
}
