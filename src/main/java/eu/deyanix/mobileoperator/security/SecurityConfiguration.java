package eu.deyanix.mobileoperator.security;

import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private AppAuthenticationProvider authenticationProvider;
    private UserRepository userRepository;
    private DataSource dataSource;

    public SecurityConfiguration(AppAuthenticationProvider authenticationProvider, UserRepository userRepository, DataSource dataSource) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .build();
    }
}
