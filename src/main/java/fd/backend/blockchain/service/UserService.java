package fd.backend.blockchain.service;

import fd.backend.blockchain.model.user.Role;
import fd.backend.blockchain.model.user.UserDto;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByCompanyName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new User(user.getCompanyName(), user.getPassword(), listAuthority(user.getRoles()));
    }

    /**
     * Пул ролей
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> listAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    private fd.backend.blockchain.model.user.User convertUserDtoToEntity(UserDto user) {
        return new fd.backend.blockchain.model.user.User(
                null,
                user.getCompanyName(),
                passwordEncoder.encode(user.getPassword()),
                Collections.singleton(user.getRole())
        );
    }
}
