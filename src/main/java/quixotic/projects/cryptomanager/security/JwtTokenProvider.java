package quixotic.projects.cryptomanager.security;

import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import quixotic.projects.cryptomanager.exception.forbiddenRequestExceptions.InvalidJwtException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider{
	private final int expirationInMs = 86400000;
	private final String SECRET_KEY = "3F6A8D1B2C4E9F7A5D2B0E4C1F6A2D3B5E8C9F0A5D2B3E8A9D6C4F0A2A2D3B5E";
	private final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

	public String generateToken(Authentication authentication){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder()
			.setSubject(authentication.getName())
			.setIssuedAt(new Date(nowMillis))
			.setExpiration(new Date(nowMillis + expirationInMs))
			.claim("authorities", authentication.getAuthorities())
			.signWith(signingKey, signatureAlgorithm);
		return builder.compact();
	}

	public void validateToken(String token){
		try{
			Jwts.parserBuilder().setSigningKey(apiKeySecretBytes).build().parseClaimsJws(token);
		}catch(SecurityException ex){
			throw new InvalidJwtException("Invalid JWT signature");
		}catch(MalformedJwtException ex){
			throw new InvalidJwtException("Invalid JWT token");
		}catch(ExpiredJwtException ex){
			throw new InvalidJwtException("Expired JWT token");
		}catch(UnsupportedJwtException ex){
			throw new InvalidJwtException("Unsupported JWT token");
		}catch(IllegalArgumentException ex){
			throw new InvalidJwtException("JWT claims string is empty");
		}
	}

	public String getUsernameFromJWT(String token) {
		if (token == null) {
			return null;
		} else if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		return Jwts.parserBuilder()
				.setSigningKey(apiKeySecretBytes)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}}
