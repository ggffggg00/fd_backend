package fd.backend.blockchain.model.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDtoCut {

    private UUID id;

    private String title;

    @JsonProperty(value = "tax_id")
    private String taxIdentifier;

}
