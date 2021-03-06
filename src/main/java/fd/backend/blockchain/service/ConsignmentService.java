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
import fd.backend.blockchain.repo.UserRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsignmentService {

    private final ConsignmentRepository consignmentRepository;
    private final CompanyRepository companyRepository;
    private final PortRepository portRepository;
    private final BlockchainNodeService blockchain;
    private final UserRepository userRepository;

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
                .findById(UUID.fromString(request.getReceiverId()))
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        var consignment = consignmentRepository.save(
                Consignment.builder()
                        .port(port)
                        .departurePort(departurePort)
                        .sender(sender)
                        .receiver(receiver)
                        .flagTransfer(false)
                        .cargoData(encryptCargoData(request.getCargoData(), sender))
                        .build());
        changeConsignmentOwner(consignment,null, sender);

        return consignment;
    }

    public void transferConsignment(UUID consignmentId) throws Exception {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new Exception("Not found consignment by Id"));
        Company companyFrom = consignment.getSender(); //TODO: ???????????????? from - ??????????????????????
        Company companyTo = consignment.getReceiver(); //TODO: ???????????????? to - ????????????????????
//        if true ???? ???????????? ?????????? ?????????????????? ????????????????
        // ?????? ?? ????????
        if (consignment.getFlagTransfer() == true) return;
        log.info(consignment + " INFO");
        changeConsignmentOwner(consignment, companyFrom, companyTo);
    }

    private void changeConsignmentOwner(Consignment consignment, Company from, Company to) {
        consignment.setFlagTransfer(from != null);
        consignmentRepository.save(consignment);
        var block = new ConsignmentBlock(consignment, to, from)
                .signBlock(SAME_SECRET);
        blockchain.notifyBlockchainNodes(block);
    }

    /**
     * ?????????? ?????????????????? consignment ???? email user-?? (???? ??????????)
     * @param email
     * @return
     */
    public Collection<Consignment> getConsignmentInfo(String email) {
        User user = userRepository.findByEmail(email);
        return getUserConsignments(user);
    }

    public Consignment getConsignmentsById(UUID consignmentId) throws Exception {
        return consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new Exception("Consignments not found"));
    }

    /**
     * ?????????????????????????????? ?????????? ???????????? Consignments ???? User
     * @param user
     * @return
     */
    private Collection<Consignment> getUserConsignments(User user){
        Collection<Consignment> col1 = consignmentRepository.findAllBySenderIdAndFlagTransfer(user.getCompany().getId(), false);
        Collection<Consignment> col2 = consignmentRepository.findAllByReceiverIdAndFlagTransfer(user.getCompany().getId(), true);
        col1.addAll(col2);
        return col1;
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
