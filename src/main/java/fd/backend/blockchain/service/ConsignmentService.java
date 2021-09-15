package fd.backend.blockchain.service;

import fd.backend.blockchain.crypto.aes.AESCipher;
import fd.backend.blockchain.crypto.aes.AESKeyGenerator;
import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.consignment.Consignment;
import fd.backend.blockchain.model.consignment.ConsignmentBlock;
import fd.backend.blockchain.model.consignment.ConsignmentCreationRequest;
import fd.backend.blockchain.model.user.User;
import fd.backend.blockchain.repo.CompanyRepository;
import fd.backend.blockchain.repo.ConsignmentRepository;
import fd.backend.blockchain.repo.PortRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsignmentService {

    private final ConsignmentRepository consignmentRepository;
    private final CompanyRepository companyRepository;
    private final PortRepository portRepository;
    private final BlockchainNodeService blockchain;
    private static final String SAME_SECRET = "387rgOH7*O&GxpuON:";

    public Consignment createConsignment(ConsignmentCreationRequest request) {

        var port = portRepository.findById(request.getPortId())
                .orElseThrow(() -> new IllegalArgumentException("Arrival port not found"));
        var departurePort = portRepository.findById(request.getDeparturePortId())
                .orElseThrow(() -> new IllegalArgumentException("Departure port not found"));

        var sender = companyRepository
                .findById(UUID.fromString(request.getSenderId()))
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        var receiver = companyRepository
                .findById(UUID.fromString(request.getSenderId()))
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        var consignment = consignmentRepository.save(
                Consignment.builder()
                        .port(port)
                        .departurePort(departurePort)
                        .sender(sender)
                        .receiver(receiver)
                        .cargoData(encryptCargoData(request.getCargoData(), sender))
                        .build());
        changeConsignmentOwner(consignment,null, sender);

        return consignment;
    }

    public Collection<Consignment> getUserConsignments(User user){
        return consignmentRepository.findAllBySenderId(user.getCompany().getId());
    }

    public void changeConsignmentOwner(Consignment consignment, Company from, Company to){
        var block = new ConsignmentBlock(consignment, to, from)
                .signBlock(SAME_SECRET);
        blockchain.notifyBlockchainNodes(block);
    }

    private String encryptCargoData(String cargoData, Company senderCompany) {
        var secretKey = (StringUtils.isEmpty(senderCompany.getAesKey())) ?
                AESKeyGenerator.getInstance().generateKey() :
                AESKeyGenerator.fromString(senderCompany.getAesKey());

        companyRepository.save(
                senderCompany.setAesKey(
                        AESKeyGenerator.keyToString(secretKey)));

        try {
            return AESCipher.encrypt(cargoData, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Cargo data encryption error");
        }

    }


}
