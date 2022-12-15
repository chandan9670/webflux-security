package com.tech.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.tech.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class ContextRepository implements ServerSecurityContextRepository {
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
	throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		 return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
		            .filter(authHeader -> authHeader.startsWith("Bearer "))
		            .flatMap(authHeader -> {
		                String authToken = authHeader.substring(7);
		                try {
		                	Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
		                	SecurityContext context = SecurityContextHolder.getContext();
							context.setAuthentication(new Authentication() {	
								@Override
								public String getName() {
									return claims.getSubject();
								}
								
								@Override
								public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
									
								}
								
								@Override
								public boolean isAuthenticated() {
									return true;
								}
								
								@Override
								public Object getPrincipal() {
									return claims.getSubject();
								}
								
								@Override
								public Object getDetails() {
									return claims;
								}
								
								@Override
								public Object getCredentials() {
									return claims;
								}
								
								@Override
								public Collection<? extends GrantedAuthority> getAuthorities() {
									return Collections.emptyList();
								}
							});
							return Mono.just(context);
		                }catch(Exception exception) {
		                	exception.printStackTrace();
		                	return Mono.empty();
		                }
		            });
	}

}
