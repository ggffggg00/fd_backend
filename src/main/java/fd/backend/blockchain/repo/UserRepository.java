package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByCompanyName(String username);

}
