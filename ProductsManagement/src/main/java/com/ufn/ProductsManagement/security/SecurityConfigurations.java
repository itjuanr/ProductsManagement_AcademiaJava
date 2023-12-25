package com.ufn.ProductsManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/login")
						.permitAll().requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

						.requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/categorias").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/categorias").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/clientes").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/clientes").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/clientes").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/clientes").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/item-pedidos").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/item-pedidos").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/item-pedidos").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/pagamento").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/pagamento").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/pagamento").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/pedido").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/pedido").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/pedido").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/produto").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/produto").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/produto").hasRole("ADMIN").anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
