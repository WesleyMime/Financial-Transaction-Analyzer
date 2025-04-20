package br.com.fta.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**")
						.permitAll()
						.requestMatchers("/actuator/health")
						.permitAll()
						.anyRequest()
						.authenticated()
				)
				.formLogin((formLogin) -> formLogin
						.loginPage("/login")
						.permitAll()
				)
				.httpBasic(Customizer.withDefaults())
				.logout(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		return new AuthUserDetailService(inMemoryUserDetailsManager());
	}

	private InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails userDetails = User
				.withUsername("admin@email.com.br")
				.password(passwordEncoder().encode("123999"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}