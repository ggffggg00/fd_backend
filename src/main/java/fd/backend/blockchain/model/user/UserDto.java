package fd.backend.blockchain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.company.CompanyDto;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String password;
    private CompanyDto company;
    private Role role;

}
