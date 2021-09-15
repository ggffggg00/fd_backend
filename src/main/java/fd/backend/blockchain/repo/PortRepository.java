package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.Port;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository extends JpaRepository<Port, Long> {

}
