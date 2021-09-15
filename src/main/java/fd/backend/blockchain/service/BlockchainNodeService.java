package fd.backend.blockchain.service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import fd.backend.blockchain.model.BlockChainNode;
import fd.backend.blockchain.repo.BlockChainNodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class BlockchainNodeService {

    final BlockChainNodeRepo repo;
    OkHttpClient client = new OkHttpClient();

    public BlockChainNode registerNode(BlockChainNode node) {
        return repo.save(node);
    }

    public void unregisterNode(BlockChainNode node) {
        repo.delete(node);
    }

    public Collection<BlockChainNode> getAllNodes() {
        return repo.findAll();
    }

    @Scheduled(fixedRate = 10000)
    public void invalidateInactiveNodes() {
        getAllNodes().forEach(this::checkNode);
    }

    private void checkNode(BlockChainNode node) {
        Request request = new Request.Builder()
                .url("http://" + node.getHost() + "/actuator/health")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200)
                unregisterNode(node);
        } catch (IOException e) {
            unregisterNode(node);
        }
    }

}
