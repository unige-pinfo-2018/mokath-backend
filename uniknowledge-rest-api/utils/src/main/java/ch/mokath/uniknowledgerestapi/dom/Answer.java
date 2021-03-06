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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author matteo113
 * @author zue
 */
@Entity
public class Answer implements Serializable {

	private static final long serialVersionUID = 6765067732764896403L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose(serialize = true, deserialize= true)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id")
	@Expose(serialize = true, deserialize= true)
	private Question question;

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	@Expose(serialize = true, deserialize= true)
	private User author;

	@Column(name = "text", columnDefinition="text")
	@Expose(serialize = true, deserialize= true)
	private String text;

	@ManyToMany(mappedBy = "likedAnswers",fetch = FetchType.LAZY)
    private Set<User> upvoters;

	@Column(name = "validated")
	@Expose(serialize = true, deserialize= true)
	private boolean validated;

	public Answer() {
	}

	public Answer(String text, Question question) {
		this.text = text;
		this.question = question;
		this.upvoters = new HashSet<User>();
		this.validated = false;
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
		Answer other = (Answer) obj;
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
		if (id != other.id)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (validated != other.validated)
			return false;
		return true;
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

	public long getId() {
		return id;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	public Question getQuestion() {
		return question;
	}

	public Date getCreated() {
		return this.created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public Set<User> getUpvoters() {
		return upvoters;
	}
	public void addUpvote(User u) {
		this.upvoters.add(u);
	}	
	public void removeUpvote(User u) {
		this.upvoters.remove(u);
	}

	public void validate() {
		this.validated = true;
	}
	public void unvalidate() {
		this.validated = false;
	}
	public boolean isValidated() {
		return this.validated;
	}

	public static class Builder {
		public User author;
		public String text;
		public Question question;

		public Answer.Builder with(Consumer<Answer.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Answer build() {
			return new Answer(text, question);
		}
	}
}
