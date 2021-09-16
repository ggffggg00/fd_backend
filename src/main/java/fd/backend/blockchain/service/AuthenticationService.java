package fd.backend.blockchain.service;

import fd.backend.blockchain.model.Port;
import fd.backend.blockchain.model.consignment.Consignment;
import fd.backend.blockchain.model.user.User;
import fd.backend.blockchain.model.user.UserDto;
import fd.backend.blockchain.repo.CompanyRepository;
import fd.backend.blockchain.repo.PortRepository;
import fd.backend.blockchain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService {

    public String getAuthenticatedUserEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object userAuth = (auth != null && isAuthenticated())
                ? auth.getPrincipal()
                : null;
        UserDetails userDetails = null;
        if (userAuth == null) {
            throw new RuntimeException("Not auth user");
        }
        if (userAuth instanceof UserDetails) {
            userDetails = (UserDetails) userAuth;
        }
        log.info(userDetails.getUsername());
        return userDetails.getUsername();//Email returned
    }

    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

}
