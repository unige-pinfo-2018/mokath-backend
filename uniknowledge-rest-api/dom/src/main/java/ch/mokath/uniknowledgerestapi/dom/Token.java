/**
 *
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Consumer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author tv0g
 *
 */
@Entity
public class Token implements Serializable {

	// ================================================================================
	// Properties
	// ================================================================================

	@Id
	@OneToOne
    @JoinColumn(name = "user_id")
    private User user;

	@Column(name = "token")
	private String token;

	@Column(name = "signingKey")
	private String signingKey;

	// ================================================================================
	// Constructor(s)
	// ================================================================================

	public Token(String token, String signingKey, User user) {
		this.token = token;
		this.signingKey = signingKey;
		this.user = user;
	}

	// ================================================================================
	// Getter(s) & Setter(s)
	// ================================================================================

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public void setSigningKey(String signingKey) {
		this.signingKey = signingKey;
	}


	// ================================================================================
	// Builder
	// ================================================================================

	public static class Builder {

		public String token;
		public String signingKey;
		public User user;

		public Token.Builder with(Consumer<Token.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Token build() {
			return new Token(token, signingKey, user);
		}
	}

}
