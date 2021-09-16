package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
