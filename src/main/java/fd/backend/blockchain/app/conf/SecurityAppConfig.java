package fd.backend.blockchain.app.conf;

import fd.backend.blockchain.model.user.Role;
import fd.backend.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.ObjectName;

@Configuration
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/secured/**", "/api/*").hasRole("CARRIER")
                    .antMatchers("/actuator/*").permitAll()
                    .antMatchers("/login/").permitAll()
                    .antMatchers("/user/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .permitAll();
    }

    @Bean(name = "bCryptEncode")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method setting up DaoAuthenticationProvider for security
     * @see Bean
     * @return encoder password object
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            @Qualifier("bCryptEncode") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

}
