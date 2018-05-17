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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import ch.mokath.uniknowledgerestapi.dom.Institution;

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
	
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ElementCollection(targetClass = Question.class)
	private Set<Question> questions;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> answers;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_upvote_question",
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
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_upvote_answer",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "answer_id"))
	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> likedAnswers;

	/* field relationship mapping for Institution - Only 1 institution/user */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "institution_id")
	@ElementCollection(targetClass = Institution.class)
	@Expose(serialize = true, deserialize= true)
	private Institution institution;

	/* constructors and methods */
	public User() {
	}

	public User(String username, String firstName, String lastName, String profilePictureURL, String email,
			String password) {
//			String password, String institution) {
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
		this.institution = new Institution();
/*		this.institution = getInstitutionFromJsonString(institution);
	}
    public Institution getInstitutionFromJsonString(String jsonString) {
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
    
        return gson.fromJson(jsonString, Institution.class);       
*/    }


	@Override
	public String toString() {
		
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return gson.toJson(this);
	}
	
	

	@Override
	public int hashCode() { //TODO ? add institution to hashcode?
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
//		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
/*		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
*/		return true;
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
	
	public void removeQuestion(Question q) {
		this.questions.remove(q);
	}
	
	public void addAnswer(Answer a) {
		this.answers.add(a);
	}
	
	public void removeAnswer(Answer a) {
		this.answers.remove(a);
	}
	
	public void addLikedQuestion(Question q) {
		this.likedQuestions.add(q);
	}
	
	public void removeLikedQuestion(Question q) {
		this.likedQuestions.remove(q);
	}
	
	public Set<Question> getLikedQuestions(){
		return this.likedQuestions;
	}
	
	public void addFollowedQuestion(Question q) {
		this.followedQuestions.add(q);
	}
	
	public void removeFollowedQuestion(Question q) {
		this.followedQuestions.remove(q);
	}
	
	public Set<Question> getFollowedQuestions() {
		return this.followedQuestions;
	}
	
	public void addLikedAnswer(Answer a) {
		this.likedAnswers.add(a);
	}
	
	public void removeLikedAnswer(Answer a) {
		this.likedAnswers.remove(a);
	}
	
	public Set<Answer> getLikedAnswers() {
		return this.getLikedAnswers();
	}
	
	
	public void setInstitution(Institution i) {
        this.institution = i;
	}
	public Institution getInstitution() {
        return this.institution;
	}	
	public void removeInstitution() {
        this.institution = null;
	}
	
	
	public static class Builder {

		public String username;
		public String firstName;
		public String lastName;
		public String profilePictureURL;
		public String email;
		public String password;
//		public String institution;

		public User.Builder with(Consumer<User.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public User build() {
			return new User(username, firstName, lastName, profilePictureURL, email, password);
//			return new User(username, firstName, lastName, profilePictureURL, email, password, institution);
		}
	}
}
