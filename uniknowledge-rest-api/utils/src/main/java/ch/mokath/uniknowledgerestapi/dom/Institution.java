/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


//	private UUID id; //20180511: decision to use Long instead of UUID, as for User [zue]

/**
 * @author tv0g
 * @author zue
 */
@Entity
public class Institution implements Serializable {

	private static final long serialVersionUID = 2636792944511323510L;

	/* start fields */
	@Id
//	@GeneratedValue(generator = "UUID")
//	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
//	private UUID id;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose(serialize = false, deserialize= true)
	private Long id;

	@Size(max = 100)
	@Column(name = "institution_name")
	@Expose(serialize = true, deserialize= true)
	private String institutionName;

	@Column(name = "logoPictureURL")
	@Expose(serialize = true, deserialize= true)
	private String logoPictureURL;

	@Column(name = "contactEmail")
	@Expose(serialize = true, deserialize= true)
	private String contactEmail;
	
	/* External field-mapping */
	@ElementCollection(targetClass = String.class,fetch=FetchType.EAGER)
	@Expose(serialize = true, deserialize= true)
	private Set<String> domains;

/*/	@OneToMany(mappedBy="id",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
//	@ElementCollection(targetClass = User.class)
	@ManyToOne
	@JoinColumn(name = "administrator_id")
	@Expose(serialize = true, deserialize= true)
	private Set<User> administrators;

	@ManyToOne
	@JoinColumn(name = "replier_id")
	@Expose(serialize = true, deserialize= true)
	private Set<User> repliers;

	@ManyToOne
	@JoinColumn(name = "asker_id")
	@Expose(serialize = true, deserialize= true)
	private Set<User> askers;
	
    /* constructors */
	public Institution() {
	}

	public Institution(String institutionName, String logoPictureURL, String contactEmail, HashSet<String> domains){
//			,Set<String> administrators, Set<String> repliers, Set<String> askers) {
		this.institutionName = institutionName;
		this.logoPictureURL = logoPictureURL;
		this.contactEmail = contactEmail;
		this.domains = domains;
		
/*		this.administrators = new HashSet<User>();
		this.askers = new HashSet<User>();
		this.repliers = new HashSet<User>();
*/	}

	@Override
	public String toString() {
/*		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return gson.toJson(this);
*/		return new Gson().toJson(this);
	}

	/* getter */
//	public UUID getId() {
	public Long getId() {
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

	public Set<String> getDomains() {
		return domains;
	}

/*	public Set<User> getAdministrators() {
		return administrators;
	}

	public Set<User> getRepliers() {
		return repliers;
	}

	public Set<User> getAskers() {
		return askers;
	}

	/* setter */
	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = Long.parseLong(id);
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public void setLogoPictureURL(String logoPictureURL) {
		this.logoPictureURL = logoPictureURL;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setDomains(Set<String> domains) {
		this.domains = domains;
	}

/*	public void setAdministrators(Set<User> administrators) {
		this.administrators = administrators;
	}

	public void setRepliers(Set<User> repliers) {
		this.repliers = repliers;
	}

	public void setAskers(Set<User> askers) {
		this.askers = askers;
	}

	/* builder */
	public static class Builder {

		public String institutionName;
		public String logoPictureURL;
		public String contactEmail;
		public HashSet<String> domains;
/*		public HashSet<User> administrators;
		public HashSet<User> repliers;
		public HashSet<User> askers;
*/
		public Institution.Builder with(Consumer<Institution.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Institution build() {
			return new Institution(institutionName, logoPictureURL, contactEmail, domains);
		}
	}

}
