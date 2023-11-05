package joshua.storageapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import joshua.storageapp.services.UserDetailsLoader;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                /*
                 * Pages that require authentication
                 */
                .requestMatchers("/create-collection", "/user", "create-container/{id}", "/collection/{id}", "create-container", "create-item/{id}", "container/{id}", "item/{id}", "edit-collection/{id}", "edit-container/{id}", "edit-item/{id}", "edit-user").authenticated()
                /*
                 * Pages that do not require authentication
                 * anyone can visit the home page, register, and login
                 */
                .requestMatchers("/", "/registration", "/login", "/user").permitAll()
                // allow loading of static resources
                .requestMatchers("/css/**", "/js/**", "/imgages/**").permitAll())
                /* Login configuration */
                .formLogin((login) -> login.loginPage("/login").defaultSuccessUrl("/user"))
                /* Logout configuration */
                .logout((logout) -> logout.logoutSuccessUrl("/login"))
                .httpBasic(withDefaults());
        return http.build();
    }

}