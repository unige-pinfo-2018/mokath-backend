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

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * @author matteo113
 * @author zue
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

	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
//	@ElementCollection(targetClass = User.class)
    @Expose(serialize = true, deserialize= true)
	private User author;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@Expose(serialize = true, deserialize= true)
	private Set<String> domains;

	@Column(name = "title")
	@Expose(serialize = true, deserialize= true)
	private String title;

	@Column(name = "text", columnDefinition="text")
	@Expose(serialize = true, deserialize= true)
	private String text;

	@OneToMany(mappedBy = "question",cascade = CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
//	@ElementCollection(targetClass = Answer.class)
	private Set<Answer> answers;
	
	@ManyToMany(mappedBy = "likedQuestions",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
	private Set<User> upvotes;
	
	@ManyToMany(mappedBy = "followedQuestions",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
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
	public void removeFollower(User follow) {
		this.followers.remove(follow);
	}

	public Set<Answer> getAnswers() {
		return answers;
	}
	public void addAnswer(Answer a) {
		this.answers.add(a);
	}
	public void removeAnswer(Answer a) {
		this.answers.remove(a);
	}

	public Set<User> getUpvotes() {
		return upvotes;
	}
	public void addUpvote(User like) {
		this.upvotes.add(like);
	}
	public void removeUpvote(User like) {
		this.upvotes.remove(like);
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
