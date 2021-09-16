package fd.backend.blockchain.service;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.company.CompanyDto;
import fd.backend.blockchain.model.user.Role;
import fd.backend.blockchain.model.user.UserDto;
import fd.backend.blockchain.repo.BlockChainNodeRepo;
import fd.backend.blockchain.repo.CompanyRepository;
import fd.backend.blockchain.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    @Qualifier("bCryptEncode")
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Byte save(UserDto userDto) {

        // TODO: В идеале проверять username и другие поля на повторения

        fd.backend.blockchain.model.user.User userEntity = convertUserDtoToEntity(userDto);
        userRepository.save(userEntity);
        log.info("Success save new user");
        return 0;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String companyTitle) throws UsernameNotFoundException {
        var user = userRepository.findByCompany(
                companyRepository.findByTitle(companyTitle)
        );
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", companyTitle));
        }
        return new User(user.getCompany().getTitle(), user.getPassword(), listAuthority(user.getRoles()));
    }

    /**
     * Пул ролей
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> listAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    /**
     * Конвертер красивый!
     * @param user
     * @return
     */
    private fd.backend.blockchain.model.user.User convertUserDtoToEntity(UserDto user) {
        var userEntity = new fd.backend.blockchain.model.user.User();
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRoles(Collections.singleton(user.getRole()));
        userEntity.setCompany(convertCompanyDtotoCompany(user.getCompany()));
        return userEntity;
    }
    
    private Company convertCompanyDtotoCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setId(company.getId());
        company.setLegalAddress(companyDto.getLegalAddress());
        company.setOgrn(companyDto.getOgrn());
        company.setTaxIdentifier(companyDto.getTaxIdentifier());
        company.setTitle(companyDto.getTitle());
        company.setContactPhoneNumber(companyDto.getContactPhoneNumber());
        return company;
    }
}
