package med.voll.web_application.infra.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Caso necessário desabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/assets/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // O 'true' força o redirecionamento mesmo se vindo diretamente da página de login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("LembrarDeMim") //string sem espaços e segura
                        .alwaysRemember(true)
                        .tokenValiditySeconds(604800) // Opcional: define o tempo de expiração do token (7 dias)
                )
                .csrf(Customizer.withDefaults())
                .build();
    }
    @Bean
    public PasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();

            }
}

