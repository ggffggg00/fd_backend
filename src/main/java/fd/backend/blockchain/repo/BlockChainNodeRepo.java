package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.BlockChainNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockChainNodeRepo extends JpaRepository<BlockChainNode, Long> {

}
