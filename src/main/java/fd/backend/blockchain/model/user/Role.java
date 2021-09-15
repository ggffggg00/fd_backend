package fd.backend.blockchain.model.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли Spring Security
 */
public enum Role implements GrantedAuthority {
    ADMIN,
    CARRIER,
    IMPORTER,
    EXPORTER;

    @Override
    public String getAuthority() {
        return name();
    }
}
