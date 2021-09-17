package fd.backend.blockchain.controller;


import fd.backend.blockchain.model.consignment.Consignment;
import fd.backend.blockchain.model.consignment.ConsignmentCreationRequest;
import fd.backend.blockchain.service.AuthenticationService;
import fd.backend.blockchain.service.ConsignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/consignment")
public class ConsignmentController {

    final ConsignmentService consignmentService;
    final AuthenticationService authenticationService;

    /**
     * {
     *     "senderId": "9d852ec9-3bda-4861-8fa7-fba802c76476",
     *     "receiverId": "732440e8-e5f2-4c03-98a7-a566afb95b81",
     *     "departurePortId": 1,
     *     "portId": 2,
     *     "cargoData": "hehe-hoho"
     * }
     *
     */
    @PostMapping("/create")
    public Consignment createConsignment(
            @RequestBody ConsignmentCreationRequest request){

        return consignmentService.createConsignment(request);
    }

    /**
     * Коносаменты по текущему юзеру - инфо
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<Collection<Consignment>> getConsignments() {
        Collection<Consignment> consignmentCollection;
        try {
            consignmentCollection = consignmentService.getConsignmentInfo(
                    authenticationService.getAuthenticatedUserEmail()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(consignmentCollection);
    }

    /**
     * Информация о Коносаменте по его UUID
     */
    @GetMapping("/info/{consignmentId}")
    public ResponseEntity<Consignment> getConsignmentById(@PathVariable UUID consignmentId) {
        Consignment consignment = null;
        try {
            consignment = consignmentService.getConsignmentsById(consignmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(consignment);
    }

    /**
     * Передача консамента
     * @param consignmentId
     * @return
     */
    @GetMapping("/transfer/{consignmentId}")
    public ResponseEntity<?> transferConsignment(@PathVariable UUID consignmentId) {
        try {
            consignmentService.transferConsignment(consignmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }




}
