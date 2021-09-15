package fd.backend.blockchain.model.consignment;

import fd.backend.blockchain.model.company.Company;
import lombok.Data;

import java.util.Calendar;

import static fd.backend.blockchain.crypto.Hashing.SHA256;

@Data
public class ConsignmentBlock {
    private String id;
    private String from;
    private String to;
    private String signature;
    private String cargoDetails;
    private long timestamp;

    public ConsignmentBlock(Consignment consignment, Company to, Company from){
        this.id = consignment.getId().toString();
        this.to = to.getId().toString();
        this.from = from == null ? "CREATED" : from.getId().toString();
        this.cargoDetails = consignment.getCargoData();
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public ConsignmentBlock signBlock(String secret){
        this.signature = SHA256(id+to+from+cargoDetails+timestamp+secret);
        return this;
    }

}
