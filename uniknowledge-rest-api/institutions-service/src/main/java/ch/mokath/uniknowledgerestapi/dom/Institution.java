/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.registry.infomodel.User;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author tv0g
 *
 */
@Entity
@Table(name = "INSTITUTIONS")
public class Institution implements Serializable {

	private Set<User> administrators;
	private Set<User> repliers;
	private Set<User> askers;
	private Set<String> domains;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID UUID;

	@Size(max = 100)
	@Column(name = "name")
	private String name;

	@Column(name = "logoPictureURL")
	private String logoPictureURL;

	@Column(name = "contactEmail")
	private String contactEmail;

	public Institution(String name, String logoPictureURL, String contactEmail, Set<String> domains,
			Set<User> administrators, Set<User> repliers, Set<User> askers) {
		this.name = name;
		this.logoPictureURL = logoPictureURL;
		this.contactEmail = contactEmail;
		this.domains = domains;
		this.administrators = administrators;
		this.askers = askers;
		this.repliers = repliers;
	}

	public String toString() {
		return "Institution Name: '" + this.name + "', Logo URL: '" + this.logoPictureURL + "', Contact Email: '" + this.contactEmail+ "'";
	}

	public Set<User> getAdministrators() {
		return administrators;
	}

	public Set<User> getRepliers() {
		return repliers;
	}

	public Set<User> getAskers() {
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

	public void setAdministrators(Set<User> administrators) {
		this.administrators = administrators;
	}

	public void setRepliers(Set<User> repliers) {
		this.repliers = repliers;
	}

	public void setAskers(Set<User> askers) {
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

		public Set<User> administrators;
		public Set<User> repliers;
		public Set<User> askers;
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