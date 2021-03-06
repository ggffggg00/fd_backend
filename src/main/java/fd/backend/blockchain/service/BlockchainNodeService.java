package fd.backend.blockchain.service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import fd.backend.blockchain.model.BlockChainNode;
import fd.backend.blockchain.model.consignment.ConsignmentBlock;
import fd.backend.blockchain.repo.BlockChainNodeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlockchainNodeService {

    private final BlockChainNodeRepo blockChainNodeRepo;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final OkHttpClient client = new OkHttpClient();

    public BlockChainNode registerNode(BlockChainNode node) {
        return blockChainNodeRepo.save(node);
    }

    public void unregisterNode(BlockChainNode node) {
        blockChainNodeRepo.delete(node);
    }

    public Collection<BlockChainNode> getAllNodes() {
        return blockChainNodeRepo.findAll();
    }

    @Scheduled(fixedRate = 10000)
    public void invalidateInactiveNodes() {
        getAllNodes().forEach(this::checkNode);
    }

    public void notifyBlockchainNodes(ConsignmentBlock block) {
        log.info("Ожидаем разрешение!!!");
        getAllNodes().forEach((node)->{
            service.execute(() -> {
                try {
                    log.info(requestToNode(node, block));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        log.info("Разрешено!!!");
    }

    private String requestToNode(BlockChainNode node, ConsignmentBlock block) {
        return restTemplate.postForEntity("http://" + node.getHost() + "/chain/append",
                block, String.class).getBody();
    }

    private void checkNode(BlockChainNode node) {
        Request request = new Request.Builder()
                .url("http://" + node.getHost() + "/actuator/health")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                unregisterNode(node);
            }
        } catch (IOException e) {
            unregisterNode(node);
        }
    }


}
