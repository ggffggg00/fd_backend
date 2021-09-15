package fd.backend.blockchain.controller;

import fd.backend.blockchain.model.BlockChainNode;
import fd.backend.blockchain.service.BlockchainNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("registry")
public class BlockchainRegistryController {

    final BlockchainNodeService bcNodeService;

    @PostMapping
    public ResponseEntity<BlockChainNode> registerBlockChainNode
            (@RequestBody BlockChainNode fuckenNode){

        var savedNode = bcNodeService.registerNode(fuckenNode);
        return new ResponseEntity<>(savedNode, HttpStatus.OK);
    }

}
