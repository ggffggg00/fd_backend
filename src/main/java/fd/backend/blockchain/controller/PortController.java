package fd.backend.blockchain.controller;

import fd.backend.blockchain.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/port")
@RestController
public class PortController {

    @Autowired
    private PortService portService;

    /**
     * Получить список портов системы
     */
    @GetMapping
    public ResponseEntity<?> getPortSystem() {
        return ResponseEntity.ok(portService.getAllPort());
    }
}
