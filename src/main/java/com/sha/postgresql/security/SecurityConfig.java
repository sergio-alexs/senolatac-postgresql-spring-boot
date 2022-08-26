package com.sha.postgresql.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Cross-origin-resource-sharing: Serve it from -> localhost:8080, Request from
		// -> localhost:4200 (permit/block with cors)
		http.cors().and().authorizeRequests()
				// There are public paths so reachable paths by everybody
				.antMatchers("/error", "/api/user/**").permitAll()
				// These can be reachable for just admin role. In here, ADMIN means: ADMIN or
				// ROLE_ADMIN;
				.antMatchers("/api/admin/**").hasRole("ADMIN")
				// TodoItem operations will need fully authentication.
				// OR described like below for each role.
				// .antMatchers("api/todo/**").hasAnyRole("ADMIN", "USER")
				// All remaining paths should need authentication.
				.anyRequest().fullyAuthenticated().and()
				// logout will log the user out by invalidated session.
				.logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST")).and()
				.formLogin()
				// login form and path
				.loginPage("/api/user/login").and()
				// Enable basic authentication. So our Authorization type is BasicAuthorization.
				// bto(username:password)
				.httpBasic().and()
				// Default SessionPolicy is IF_REQUIRED: a session will be created only if
				// required.
				// STATELESS - no session will be created or used.
				// Example: changeUserRole from USER to ADMIN with endpoint.
				// IF_REQUIRED keeps session-data(USER), STATELESS keeps new-data(ADMIN).
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// Cross side request forgery.
				.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
