/**
 *
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
	private long id;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

    @ManyToOne()
    @JoinColumn(name = "author_id")
	private User author;

	@ElementCollection(targetClass = String.class)
	private Set<String> domains;

	@Column(name = "title")
	private String title;

	@Column(name = "text")
	private String text;

	@ElementCollection(targetClass = Answer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question", orphanRemoval = true)
	private Set<Answer> answers;

	@ElementCollection(targetClass = User.class)
	private Set<User> likes;

	@ElementCollection(targetClass = User.class)
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
	public Question(Set<String> domains, String title, String text) {
		this.domains = domains;
		this.title = title;
		this.text = text;

		//TODO choose between HashSet or SortedSet
		this.answers = new HashSet<Answer>();
		this.likes = new HashSet<User>();
		this.followers =  new HashSet<User>();
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
		return likes;
	}

	public void addLike(User like) {
		this.likes.add(like);
	}

	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date date) {
		this.created = date;
	}

	public User getAuthorId() {
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
		public Set<String> domains;
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
