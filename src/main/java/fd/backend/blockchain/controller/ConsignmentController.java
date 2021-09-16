package fd.backend.blockchain.controller;


import fd.backend.blockchain.model.consignment.Consignment;
import fd.backend.blockchain.model.consignment.ConsignmentCreationRequest;
import fd.backend.blockchain.service.AuthenticationService;
import fd.backend.blockchain.service.ConsignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
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

    @GetMapping("/user")
    public Collection<Consignment> getConsignments() {
        log.info("AAAAAAAAAAAAAAAAAAAAAAAA");
        return consignmentService.getConsignmentInfo(
                authenticationService.getAuthenticatedUserEmail()
        );
    }

//    @GetMapping("/{id}")
//    public Consignment getConsignmentsById(@PathVariable UUID id) {
//        return null;
//    }


}
