/**
 *
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * @author matteo113
 *
 */
@Entity
public class Question implements Serializable {

	/*
	 * Fields
	 */

	private static final long serialVersionUID = -2547210886311246487L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose(serialize = false, deserialize= true)
	private long id;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    @Expose(serialize = true, deserialize= true)
	private User author;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@Expose(serialize = true, deserialize= true)
	private Set<String> domains;

	@Column(name = "title")
	@Expose(serialize = true, deserialize= true)
	private String title;

	@Column(name = "text")
	@Expose(serialize = true, deserialize= true)
	private String text;

	@ElementCollection(targetClass = Answer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question", orphanRemoval = true, fetch = FetchType.EAGER)
	@Expose(serialize = true, deserialize= true)
	private Set<Answer> answers;
	
	@ManyToMany(mappedBy = "likedQuestions", fetch = FetchType.EAGER)
	@ElementCollection(targetClass = User.class)
	@Expose(serialize = true, deserialize= true)
	private Set<User> upvotes;
	
	@ManyToMany(mappedBy = "followedQuestions", fetch = FetchType.EAGER)
	@ElementCollection(targetClass = User.class)
	@Expose(serialize = true, deserialize= true)
	private Set<User> followers;

	/*
	 * Constructors
	 */

	public Question() {

	}

	/**
	 * Create a new instance of Question. By default a question is open.
	 *
	 * @param id
	 *            Unique ID of the Question
	 * @param timestamp
	 *            Date and time of the Question creation
	 * @param author
	 *            User who created the Question
	 * @param domains
	 *            Domains tags of the Question
	 * @param title
	 *            Title of the Question
	 * @param text
	 *            Text of the Question
	 */
	public Question(HashSet<String> domains, String title, String text) {
		this.domains = domains;
		this.title = title;
		this.text = text;

		//TODO choose between HashSet or SortedSet
		this.answers = new HashSet<Answer>();
		this.upvotes = new HashSet<User>();
		this.followers =  new HashSet<User>();
	}
	
	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return gson.toJson(this);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((domains == null) ? 0 : domains.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Question other = (Question) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (domains == null) {
			if (other.domains != null)
				return false;
		} else if (!domains.equals(other.domains))
			return false;
		if (id != other.id)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/*
	 * Getters/ Setters
	 */
	
	
	public long getId() {
		return id;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void addFollower(User follow) {
		this.followers.add(follow);
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void addAnswer(Answer ans) {
		this.answers.add(ans);
	}

	public Set<User> getLikes() {
		return upvotes;
	}

	public void addLike(User like) {
		this.upvotes.add(like);
	}

	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date date) {
		this.created = date;
	}

	public User getAuthor() {
		return this.author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Set<String> getDomains() {
		return domains;
	}

	public void setDomains(Set<String> domains) {
		this.domains = domains;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public static class Builder{
		public HashSet<String> domains;
		public String title;
		public String text;

		public Question.Builder with(Consumer<Question.Builder> builder){
			builder.accept(this);
			return this;
		}

		public Question build() {
			return new Question( domains, title, text);
		}
	}

}
