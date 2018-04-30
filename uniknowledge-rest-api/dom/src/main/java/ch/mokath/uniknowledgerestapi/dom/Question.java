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
public class Question implements Serializable {

	/*
	 * Fields
	 */

	private static final long serialVersionUID = -5214738664315670513L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	// TODO Change type to User once implemented !!!
	@Column(name = "author")
	private String author;

	@ElementCollection(targetClass = String.class)
	private Set<String> domains;

	@Column(name = "title")
	private String title;

	@Column(name = "text")
	private String text;

	@Column(name = "isClosed")
	private boolean isClosed;

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
	public Question(UUID id, Timestamp timestamp, String author, Set<String> domains, String title, String text) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.author = author;
		this.domains = domains;
		this.title = title;
		this.text = text;
		this.isClosed = false;
	}

	/*
	 * Getters/ Setters
	 */

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
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

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed() {
		this.isClosed = true;
	}

	public void setOpen() {
		this.isClosed = false;
	}

}