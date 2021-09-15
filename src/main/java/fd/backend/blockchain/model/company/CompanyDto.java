package fd.backend.blockchain.model.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    @NotNull
    private String title;

    @JsonProperty(value = "tax_id")
    @NotNull
    private String taxIdentifier;

    @NotNull
    private String ogrn;

    @NotNull
    @JsonProperty(value = "legal_address")
    private String legalAddress;

    @NotNull
    @JsonProperty(value = "phone")
    private String contactPhoneNumber;
}
