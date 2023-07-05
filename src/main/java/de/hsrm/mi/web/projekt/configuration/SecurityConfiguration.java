package de.hsrm.mi.web.projekt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * UserBuilder userBuilder = User.builder().passwordEncoder(password ->
     * passwordEncoder().encode(password));
     * 
     * UserDetails user1 = userBuilder
     * .username("Joendhard")
     * .password("Losung")
     * .roles("USER")
     * .build();
     * 
     * UserDetails user2 = userBuilder
     * .username("Joghurta")
     * .password("Losung")
     * .roles("CHEF")
     * .build();
     * 
     * return new InMemoryUserDetailsManager(user1, user2);
     * }
     */

    @Order(1)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(HttpMethod.GET, "/kategorie", "/frage", "/quiz", "/registrieren",
                        "/fragebearbeiten.css", "/api/**", "/stompbroker")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/registrieren", "/api/quiz/check").permitAll()
                .requestMatchers("/frage/0", "/kategorie/0", "/quiz/0").hasRole("CHEF")
                .requestMatchers(HttpMethod.POST, "/frage/*", "/kategorie/*", "/quiz/*").hasRole("CHEF")
                .anyRequest()
                .authenticated())
                .formLogin(login -> login
                        .defaultSuccessUrl("/quiz"))
                .logout(out -> out.logoutSuccessUrl("/login"))
                .csrf(csrf -> csrf.disable());// .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()));
        http.headers(headers -> headers.frameOptions().sameOrigin());

        return http.build();
    }

    @Order(2)
    @Bean
    SecurityFilterChain filterChainAPI(HttpSecurity http) throws Exception {
        http
                // Diese SecurityFilterchain nur für bestimmte Pfade
                .securityMatchers(
                        s -> s.requestMatchers("/api/**", "/stompbroker"))
                // nur authentifizierte Requests zulassen
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                // CSRF Protection bei zustandslosen REST-APIs unnoetig
                .csrf(csrf -> csrf.disable())
                // Basic Authentication aktivieren
                .httpBasic(withDefaults())
                // Basic Auth ist stateless (Zugangsinfos kommen immer mit), keine Session
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
