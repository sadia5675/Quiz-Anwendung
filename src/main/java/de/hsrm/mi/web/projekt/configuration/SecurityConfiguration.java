package de.hsrm.mi.web.projekt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    /* 
    @Bean
    public UserDetailsService userDetailsService() {
        UserBuilder userBuilder = User.builder().passwordEncoder(password -> passwordEncoder().encode(password));

        UserDetails user1 = userBuilder
                .username("Joendhard")
                .password("Losung")
                .roles("USER")
                .build();

        UserDetails user2 = userBuilder
                .username("Joghurta")
                .password("Losung")
                .roles("CHEF")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(HttpMethod.GET, "/kategorie", "/frage", "/quiz", "/registrieren" , "/fragebearbeiten.css" ).permitAll()
                .requestMatchers(HttpMethod.POST, "/registrieren").permitAll()
                .requestMatchers("/frage/0", "/kategorie/0", "/quiz/0")
                .hasRole("CHEF")
                .anyRequest()
                .authenticated())
                .formLogin()
                .defaultSuccessUrl("/quiz")
                .and()
                .logout(out -> out.logoutSuccessUrl("/login"))
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()));
                http.headers().frameOptions().sameOrigin();

        return http.build();
    }


    
}
