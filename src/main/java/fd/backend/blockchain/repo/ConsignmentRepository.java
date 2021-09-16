package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.consignment.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, UUID> {

    Collection<Consignment> findAllBySenderId(UUID senderId);

}
