package fd.backend.blockchain.controller;


import fd.backend.blockchain.model.consignment.Consignment;
import fd.backend.blockchain.model.consignment.ConsignmentCreationRequest;
import fd.backend.blockchain.service.AuthenticationService;
import fd.backend.blockchain.service.ConsignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("consignment")
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

    @GetMapping("/")
    public Collection<Consignment> getConsignments(){
        authenticationService.getAuthenticatedUser();
        return Collections.emptyList();
    }


}
