package br.com.ChallengeBackEnd102022.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.com.ChallengeBackEnd102022.model.User;
import br.com.ChallengeBackEnd102022.repository.UserRepository;


public class AuthFilter extends OncePerRequestFilter{
	
	
	private TokenService tokenService;
	private UserRepository repository;
	private final HandlerExceptionResolver handlerExceptionResolver;

	public AuthFilter(TokenService tokenService, UserRepository repository, HandlerExceptionResolver handlerExceptionResolver) {
		this.tokenService = tokenService;
		this.repository = repository;
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		boolean valido = tokenService.isValid(token);

		try{

			authenticateClient(token);
			
			
			filterChain.doFilter(request, response);
			
		}
		catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
			
		}
		
	}

	private void authenticateClient(String token) {
		Long userId = tokenService.getUserId(token);
		User user = repository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
