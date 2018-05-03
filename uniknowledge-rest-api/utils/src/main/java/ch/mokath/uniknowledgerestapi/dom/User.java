/**
 *
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author tv0g
 * @author matteo113
 *
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -7683341736850458090L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose(serialize = false, deserialize= true)
	private Long id;

	@Column(name = "username")
	@Expose(serialize = true, deserialize= true)
	private String username;

	@Column(name = "first_name")
	@Expose(serialize = true, deserialize= true)
	private String firstName;

	@Column(name = "last_name")
	@Expose(serialize = true, deserialize= true)
	private String lastName;

	@Column(name = "profile_picture_url")
	@Expose(serialize = true, deserialize= true)
	private String profilePictureURL;

	@Column(name = "email")
	@Expose(serialize = true, deserialize= true)
	private String email;


	@Column(name = "password", length=128, nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	@ElementCollection(targetClass = Question.class)
	private Set<Question> questions;

	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> answers;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_like_question",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "question_id"))
	@ElementCollection(targetClass = Question.class)
	private Set<Question> likedQuestions;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_follow_question",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "question_id"))
	@ElementCollection(targetClass = Question.class)
	private Set<Question> followedQuestions;
	
	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> likedAnswers;

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
		this.likedQuestions = new HashSet<Question>();
		this.followedQuestions = new HashSet<Question>();
		this.likedAnswers = new HashSet<Answer>();
	}

	@Override
	public String toString() {
		
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return gson.toJson(this);
	}

	public void setID(Long id) {
		this.id = id;
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
	
	public void addLikedQuestions(Question q) {
		this.likedQuestions.add(q);
	}
	
	public void removeLikedQuestion(Question q) {
		this.likedQuestions.remove(q);
	}
	
	public Set<Question> getLikedQuestions(){
		return this.likedQuestions;
	}
	
	public void addFollowedQuestion(Question q) {
		this.likedQuestions.add(q);
	}
	
	public void removeFollowedQuestion(Question q) {
		this.likedQuestions.remove(q);
	}
	
	public Set<Question> getFollowedQuestion() {
		return this.followedQuestions;
	}
	
	//TODO Implements add, remove, get for LikedAnswers

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
