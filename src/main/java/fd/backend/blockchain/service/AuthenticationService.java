package fd.backend.blockchain.service;

import fd.backend.blockchain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }

    public boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

}
