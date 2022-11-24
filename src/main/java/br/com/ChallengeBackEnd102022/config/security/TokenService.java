package br.com.ChallengeBackEnd102022.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.ChallengeBackEnd102022.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${backendChallenge.jwt.expiration}")
	private String expiration;
	
	@Value("${backendChallenge.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		User loggedUser = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("Back End Challenge Auth Service")
				.setSubject(loggedUser.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
