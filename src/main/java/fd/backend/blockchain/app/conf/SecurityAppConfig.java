package fd.backend.blockchain.app.conf;

import fd.backend.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
@CrossOrigin
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
//                    .antMatchers("/**").permitAll()
                    .antMatchers("/secured/*", "/api/*").hasRole("CARRIER")
                    .antMatchers("/actuator/*").permitAll()
                    .antMatchers("/registry/**").permitAll()
                    .antMatchers("/static/**").permitAll()
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
                    .permitAll().and()
                .rememberMe()
                    .key("SameFuckenSecret")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(86400000);

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
