package fd.backend.blockchain.service;

import fd.backend.blockchain.model.Port;
import fd.backend.blockchain.repo.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PortService {

    @Autowired
    private PortRepository portRepository;

    /**
     * Получить все порты
     * @return
     */
    public Collection<Port> getAllPort() {
        return portRepository.findAll();
    }

}
