package fd.backend.blockchain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fd.backend.blockchain.model.company.CompanyDto;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String password;
    private String email; // validate in frontend
    private CompanyDto company;
    private Role role;

}
