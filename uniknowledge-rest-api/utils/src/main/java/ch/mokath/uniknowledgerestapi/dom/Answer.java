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

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author matteo113
 *
 */
@Entity
public class Answer implements Serializable {


	private static final long serialVersionUID = -2043208173651292955L;


	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable= false, nullable = false)
	private UUID id;

//	@Column(name = "question")
	@ManyToOne
	private Question question;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "author")
	private User author;

	@Column(name = "text")
	private String text;

	@ElementCollection(targetClass = User.class)
	private Set<User> likes;

	public Answer() {

	}

	public Answer(User author, String text, Question question) {
		this.timestamp = new Timestamp(new Date().getTime());
		this.author = author;
		this.text = text;
		this.question = question;
		this.likes = new HashSet<User>();
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UUID getId() {
		return id;
	}

	public Question getQuestion() {
		return question;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void addLike(User like) {
		this.likes.add(like);
	}

	public static class Builder{
		public User author;
		public String text;
		public Question question;

		public Answer.Builder with(Consumer<Answer.Builder> builder){
			builder.accept(this);
			return this;
		}

		public Answer build() {
			return new Answer(author, text, question);
		}
	}
}
