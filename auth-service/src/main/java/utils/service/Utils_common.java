package utils.service;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import config.PropertyConfig;
import constants.enums.Auth_role;
import exceptions.exception.InvalidSaltForHashFunctionGenerationException;
import exceptions.exception.PasswordHashKeyGenerationException;
import model.Jwt;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;

public class Utils_common {

	public static String generateSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16]; // 128-bit salt
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String hashWithPBKDF2(String password, String salt)
			throws PasswordHashKeyGenerationException, InvalidSaltForHashFunctionGenerationException {
		int iterations = 65536;
		int keyLength = 128;
		char[] passwordChars = password.toCharArray();
		byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);

		try {
			PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hash);

		} catch (NoSuchAlgorithmException | IllegalArgumentException | InvalidKeySpecException e) {
			throw new PasswordHashKeyGenerationException(
					"Exception occured during HashFunction generation for password", e);

		} catch (NullPointerException e) {
			throw new InvalidSaltForHashFunctionGenerationException("Generated Salt key is invalid", e);
		}
	}

	public static Jwt generateJwtToken(int emp_id, String email, Auth_role role) {

		String secret = PropertyConfig.get("JWT_SECRET");

		Algorithm algorithm = Algorithm.HMAC256(secret);

		long nowMillis = System.currentTimeMillis();
		long expiryMillis = nowMillis + Long.parseLong(PropertyConfig.get("JWT_EXPIRY_MILLIS"));

		Date issued_at = new Date(nowMillis);
		Date expires_at = new Date(expiryMillis);

		String token = JWT.create().withClaim("emp_id", emp_id).withClaim("email", email)
				.withClaim("role", role.toString()).withIssuedAt(issued_at).withExpiresAt(expires_at).sign(algorithm);
		return new Jwt(token, new Timestamp(nowMillis), new Timestamp(expiryMillis));

	}
}
