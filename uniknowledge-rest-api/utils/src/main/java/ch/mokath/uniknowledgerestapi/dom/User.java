/**
 *
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.Gson;

/**
 * @author tv0g
 *
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -7683341736850458090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "profile_picture_url")
	private String profilePictureURL;

	@Column(name = "email")
	private String email;

	@Column(name = "password", length = 128)
	private String password;

	@ElementCollection(targetClass = Question.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true)
	private Set<Question> questions;

	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> answers;

	public User() {
	}

	public User(String username, String firstName, String lastName, String profilePictureURL, String email,
			String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePictureURL = profilePictureURL;
		this.email = email;
		this.password = password;
		this.answers = new HashSet<Answer>();
		this.questions = new HashSet<Question>();
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void addQuestion(Question q) {
		this.questions.add(q);
	}
	
	public void addAnswer(Answer a) {
		this.answers.add(a);
	}

	public static class Builder {

		public String username;
		public String firstName;
		public String lastName;
		public String profilePictureURL;
		public String email;
		public String password;

		public User.Builder with(Consumer<User.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public User build() {
			return new User(username, firstName, lastName, profilePictureURL, email, password);
		}
	}
}
