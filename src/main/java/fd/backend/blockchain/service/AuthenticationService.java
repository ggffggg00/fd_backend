package fd.backend.blockchain.service;

import fd.backend.blockchain.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService {

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.getPrincipal().toString());
        return null;
    }

    public boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

}
