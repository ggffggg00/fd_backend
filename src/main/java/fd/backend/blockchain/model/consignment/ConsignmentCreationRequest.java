package fd.backend.blockchain.model.consignment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConsignmentCreationRequest {

    private String senderId;
    private String receiverId;
    private long departurePortId;
    private long portId;
    private String cargoData;

}
