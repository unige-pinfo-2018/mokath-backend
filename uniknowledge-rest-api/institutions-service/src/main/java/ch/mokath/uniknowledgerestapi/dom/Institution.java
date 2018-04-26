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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author tv0g
 *
 */
@Entity
@Table(name = "INSTITUTIONS")
public class Institution implements Serializable {
	
	private static final long serialVersionUID = 2636792944511323510L;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> administrators;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> repliers;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> askers;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> domains;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID UUID;

	@Size(max = 100)
	@Column(name = "name")
	private String name;

	@Column(name = "logoPictureURL")
	private String logoPictureURL;

	@Column(name = "contactEmail")
	private String contactEmail;

	public Institution() {
		// TODO Auto-generated constructor stub
	}
	public Institution(String name, String logoPictureURL, String contactEmail, Set<String> domains,
			Set<String> administrators, Set<String> repliers, Set<String> askers) {
		this.name = name;
		this.logoPictureURL = logoPictureURL;
		this.contactEmail = contactEmail;
		this.domains = domains;
		this.administrators = administrators;
		this.askers = askers;
		this.repliers = repliers;
	}

	@Override
	public String toString() {
		return "Institution Name: '" + this.name + "', Logo URL: '" + this.logoPictureURL + "', Contact Email: '" + this.contactEmail+ "'";
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

	public UUID getUUID() {
		return UUID;
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
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
		public String name;
		public String logoPictureURL;
		public String contactEmail;

		public Institution.Builder with(Consumer<Institution.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Institution build() {
			return new Institution(name, logoPictureURL, contactEmail, domains, administrators, repliers, askers);
		}
	}

}