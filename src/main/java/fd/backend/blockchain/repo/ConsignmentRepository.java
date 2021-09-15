package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.consignment.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ConsignmentRepository extends JpaRepository<Consignment, UUID> {

    Collection<Consignment> findAllBySenderId(UUID senderId);

}
