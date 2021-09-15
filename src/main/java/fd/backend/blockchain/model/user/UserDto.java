package fd.backend.blockchain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fd.backend.blockchain.model.Company;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String password;
    private String companyName;
    private Company company;
    private Role role;

}
