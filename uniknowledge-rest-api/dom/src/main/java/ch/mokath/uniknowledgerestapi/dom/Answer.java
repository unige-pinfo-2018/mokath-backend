/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "author")
	private User author;
	
	@Column(name = "text")
	private String text;
	
	@ElementCollection(targetClass = User.class)
	private Set<User> likes;

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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public Set<User> getLikes() {
		return likes;
	}
	
	public void addLike(User like) {
		this.likes.add(like);
	}
	
	
}
