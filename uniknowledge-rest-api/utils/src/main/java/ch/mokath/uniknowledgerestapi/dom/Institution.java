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
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.hibernate.validator.constraints.Email;

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
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose(serialize = true, deserialize= true)
	private Long id;

	@Size(max = 100)
	@Column(name = "institution_name")
	@Expose(serialize = true, deserialize= true)
	private String institutionName;

	@Column(name = "logoPictureURL")
	@Expose(serialize = true, deserialize= true)
	private String logoPictureURL;

	@Column(name = "contactEmail")
	@Email
	@Expose(serialize = true, deserialize= true)
    private String contactEmail;
	
	/* External field-mapping */
	@ElementCollection(targetClass = String.class,fetch=FetchType.EAGER)
	@Expose(serialize = true, deserialize= true)
	private Set<String> domains;
	@OneToMany(mappedBy="institution",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch=FetchType.LAZY)
//	@ElementCollection(targetClass = User.class)
    private Set<User> users;
	
    /* constructors */
	public Institution() {
	}

	public Institution(String institutionName, String logoPictureURL, String contactEmail, HashSet<String> domains){
		this.institutionName = institutionName;
		this.logoPictureURL = logoPictureURL;
		this.contactEmail = contactEmail;
		this.domains = domains;
		this.users = new HashSet<User>();
    }
    
	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return gson.toJson(this);
	}

	@Override
	public int hashCode() { //TODO ? add users to hashcode?
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institutionName == null) ? 0 : institutionName.hashCode());
		result = prime * result + ((logoPictureURL == null) ? 0 : logoPictureURL.hashCode());
		result = prime * result + ((contactEmail == null) ? 0 : contactEmail.hashCode());
		result = prime * result + ((domains == null) ? 0 : domains.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { //TODO ? add users to equals?
		if (this == obj)	return true;
		if (obj == null)	return false;
		if (getClass() != obj.getClass())	return false;
		Institution other = (Institution) obj;
		if (institutionName == null) {
			if (other.institutionName != null)	return false;
		} else if (!institutionName.equals(other.institutionName))    return false;
		if (logoPictureURL == null) {
			if (other.logoPictureURL != null)	return false;
		} else if (!logoPictureURL.equals(other.logoPictureURL))    return false;
		if (contactEmail == null) {
			if (other.contactEmail != null)	return false;
		} else if (!contactEmail.equals(other.contactEmail))    return false;
		if (domains == null) {
			if (other.domains != null)	return false;
		} else if (!domains.equals(other.domains))    return false;
        return true;
   }
        

    /* get-/set- ters */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getLogoPictureURL() {
		return logoPictureURL;
	}
	public void setLogoPictureURL(String logoPictureURL) {
		this.logoPictureURL = logoPictureURL;
	}

	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Set<String> getDomains() {
		return domains;
	}
	public void setDomains(Set<String> domains) {
		this.domains = domains;
	}

	/* user methods */
	public Set<User> getUsers() {
		return users;
	}
	public void addUser(User u) {
		this.users.add(u);
	}
	public void removeUser(User u) {
		this.users.remove(u);
	}


	/* builder */
	public static class Builder {

		public String institutionName;
		public String logoPictureURL;
		public String contactEmail;
		public HashSet<String> domains;

		public Institution.Builder with(Consumer<Institution.Builder> builder) {
			builder.accept(this);
			return this;
		}

		public Institution build() {
			return new Institution(institutionName, logoPictureURL, contactEmail, domains);
		}
	}

}
