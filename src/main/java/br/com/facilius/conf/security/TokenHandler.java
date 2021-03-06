package br.com.facilius.conf.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import br.com.facilius.model.User;

public class TokenHandler {
	
	public static final String APP_WEB_ID = "facilius_web";
	public static final String SECRET_KEY = "FACILIUS_SECRET";

	public String create(String id, User user, long ttlMin) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(user.getEmail()).signWith(signatureAlgorithm, signingKey).claim("password", user.getPassword());

		// if it has been specified, let's add the expiration
		if (ttlMin >= 0) {
			long expMillis = nowMillis + (ttlMin * 60000);
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

}
