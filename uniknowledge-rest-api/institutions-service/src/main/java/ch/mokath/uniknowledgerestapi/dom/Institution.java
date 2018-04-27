/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;

/**
 * @author tv0g
 *
 */
@Entity
public class Institution implements Serializable {

	private static final long serialVersionUID = 2636792944511323510L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Size(max = 100)
	@Column(name = "institution_name")
	private String institutionName;

	@Column(name = "logoPictureURL")
	private String logoPictureURL;

	@Column(name = "contactEmail")
	private String contactEmail;

	@ElementCollection(targetClass = String.class)
	private Set<String> domains;

	@ElementCollection(targetClass = String.class)
	private Set<String> administrators;

	@ElementCollection(targetClass = String.class)
	private Set<String> repliers;

	@ElementCollection(targetClass = String.class)
	private Set<String> askers;

	public Institution() {
	}

	public Institution(String institutionName, String logoPictureURL, String contactEmail, Set<String> domains,
			Set<String> administrators, Set<String> repliers, Set<String> askers) {
		this.institutionName = institutionName;
		this.logoPictureURL = logoPictureURL;
		this.contactEmail = contactEmail;
		this.domains = domains;
		this.administrators = administrators;
		this.askers = askers;
		this.repliers = repliers;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public Set<String> getAdministrators() {
		return administrators;
	}

	public Set<String> getRepliers() {
		return repliers;
	}

	public Set<String> getAskers() {
		return askers;
	}

	public Set<String> getDomains() {
		return domains;
	}

	public UUID getId() {
		return id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public String getLogoPictureURL() {
		return logoPictureURL;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setAdministrators(Set<String> administrators) {
		this.administrators = administrators;
	}

	public void setRepliers(Set<String> repliers) {
		this.repliers = repliers;
	}

	public void setAskers(Set<String> askers) {
		this.askers = askers;
	}

	public void setDomains(Set<String> domains) {
		this.domains = domains;
	}

	public void setName(String institutionName) {
		this.institutionName = institutionName;
	}

	public void setLogoPictureURL(String logoPictureURL) {
		this.logoPictureURL = logoPictureURL;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public static class Builder {

		public Set<String> administrators;
		public Set<String> repliers;
		public Set<String> askers;
		public Set<String> domains;
		public String institutionName;
		public String logoPictureURL;
		public String contactEmail;

		public Institution.Builder with(Consumer<Institution.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Institution build() {
			return new Institution(institutionName, logoPictureURL, contactEmail, domains, administrators, repliers,
					askers);
		}
	}

}