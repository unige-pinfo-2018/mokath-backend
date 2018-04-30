package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.hibernate.annotations.common.util.impl.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthInfos implements Serializable {

	private Logger log = LoggerFactory.getLogger(AuthInfos.class);

	// ================================================================================
	// Constants
	// ================================================================================

	// 128-bits salt, 512-bits Hash, 10'000 iterations
	public static final int SALT_BYTES_SIZE = 16;
	public static final int PRF_BYTES_SIZE = 64;
	public static final int ITERATIONS_NUMBER = 10000;

	// Properties Declaration
	private static final long serialVersionUID = -6723694555578759181L;
	private String email;
	private String password;

	// ================================================================================
	// Constructor(s)
	// ================================================================================

	public AuthInfos(String email, String password) {
		this.email = email;
		this.password = password;
	}

	// ================================================================================
	// Getter(s) & Setter(s)
	// ================================================================================

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================

	/**
	 * Create and return a full password hash entry to store in the database
	 * 
	 * @param password
	 *            Password to hash
	 * @return Full password hash entry to store in database
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public String createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

		// Generate random crypto secure salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTES_SIZE];
		random.nextBytes(salt);

		// Hash password
		byte[] hash = pbkdf2(password.toCharArray(), salt, ITERATIONS_NUMBER, PRF_BYTES_SIZE * 8);

		// Return string to store in db
		return ITERATIONS_NUMBER + ":" + toBase64(salt) + ":" + toBase64(hash);
	}

	/**
	 * Provide a timing attack proof validation of a provided password against a
	 * given hash.
	 * 
	 * @param password
	 *            Password to validate
	 * @param expectedHash
	 *            Full password hash entry extracted from db
	 * @return true iff given password matches with the hash from db
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public boolean validatePassword(String expectedHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		// Split hash from DB into iterations, salt and hash parameters
		String[] p = expectedHash.split(":");
		int iterations = Integer.parseInt(p[0]);
		byte[] salt = fromBase64(p[1]);
		byte[] correctHash = fromBase64(p[2]);
		
		// Compute hash of provided password
		byte[] providedPasswordHash = pbkdf2(this.password.toCharArray(), salt, iterations, correctHash.length * 8);
		
		// MessageDigest.isEqual is timing attack proof since
		// http://www.oracle.com/technetwork/java/javase/6u17-141447.html
		return MessageDigest.isEqual(correctHash, providedPasswordHash);
	}

	// ================================================================================
	// Utilities Private Functions
	// ================================================================================

	private String toBase64(byte[] s) {
		return new String(Base64.getEncoder().encode(s));
	}

	private byte[] fromBase64(String s) {
		return Base64.getDecoder().decode(s);
	}

	private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes);

		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

		return skf.generateSecret(spec).getEncoded();
	}
}
