package fd.backend.blockchain.app.conf;

import fd.backend.blockchain.model.user.Role;
import fd.backend.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.ObjectName;
import java.util.UUID;

@Configuration
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/secured/*", "/api/*").hasRole("CARRIER")
                    .antMatchers("/actuator/*").permitAll()
                    .antMatchers("/login/").permitAll()
                    .antMatchers("/static/*").permitAll()
                    .antMatchers("/user/*").permitAll() //Удалить?
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .defaultSuccessUrl("/company/info/all") //TODO: редирект на страницу после авторизации - ИЗМЕНИТЬ
                    .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .permitAll();
    }

    @Bean(name = "bCryptEncode")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "securityContext")
    public Authentication getSecurityAuthConext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            @Qualifier("bCryptEncode") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

}
