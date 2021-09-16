package fd.backend.blockchain.service;

import fd.backend.blockchain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    @Autowired
    @Qualifier("securityContext")
    private Authentication authentication;

    public User getAuthenticatedUser(){
        var authUser = authentication.getPrincipal();
        return null;
    }

    public boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

}
