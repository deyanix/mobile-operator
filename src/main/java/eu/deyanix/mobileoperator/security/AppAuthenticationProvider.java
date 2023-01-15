package eu.deyanix.mobileoperator.security;

import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider {
    public static void reloadToken(User newUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof User user) {
            if (user.getId() != newUser.getId()) {
                return;
            }
        }

        SecurityContextHolder.getContext()
                .setAuthentication(AppAuthenticationProvider.createToken(newUser));
    }

    public static Authentication createToken(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
        if (user.getCustomer() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        };

        if (user.hasAuthority("ROLE_ADMINISTRATOR")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
    }

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AppAuthenticationProvider(UserRepository userRepository) {
        this.passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        this.userRepository = userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication
                .getCredentials()
                .toString();
        User user = userRepository
                .findByUsername(name)
                .orElseThrow(() -> new BadCredentialsException("Niepoprawne dane logowania"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Niepoprawne dane logowania");
        }
        return createToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
