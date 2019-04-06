package com.purvanovv.user_store.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.purvanovv.user_store.exception.JwtAuthorzieException;
import com.purvanovv.user_store.model.CustomUserDetails;
import com.purvanovv.user_store.model.Role;
import com.purvanovv.user_store.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds; // 1h

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("role", user.getRole());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("username", user.getUsername());
		claims.put("userId", user.getId());
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).setId(user.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public boolean validateToken(String token) throws JwtAuthorzieException {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtAuthorzieException("Expired or invalid JWT token");
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		Role role = new Role((int) ((Map) claims.get("role")).get("id"),
				(String) ((Map) claims.get("role")).get("name"));

		List<Role> roles = new ArrayList<>();
		roles.add(role);

		CustomUserDetails timexisUserDetails = new CustomUserDetails((String) claims.get("username"),
				(String) claims.get("firstName"), (String) claims.get("lastName"), roles);

		return new UsernamePasswordAuthenticationToken(timexisUserDetails, "", timexisUserDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
