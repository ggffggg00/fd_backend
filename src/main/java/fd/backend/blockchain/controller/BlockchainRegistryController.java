package fd.backend.blockchain.controller;

import fd.backend.blockchain.model.BlockChainNode;
import fd.backend.blockchain.service.BlockchainNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/registry")
public class BlockchainRegistryController {

    final BlockchainNodeService bcNodeService;

    @PostMapping
    public ResponseEntity<BlockChainNode> registerBlockChainNode
            (@RequestBody BlockChainNode fuckenNode){
        var savedNode = bcNodeService.registerNode(fuckenNode);
        return new ResponseEntity<>(savedNode, HttpStatus.OK);
    }

    @GetMapping("/nodes")
    public ResponseEntity<?> getAllNodeBlockChain() {
        return ResponseEntity.ok(bcNodeService.getAllNodes());
    }

}
