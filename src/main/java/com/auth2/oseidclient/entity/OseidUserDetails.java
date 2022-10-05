package com.auth2.oseidclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@Entity
@Data
@NoArgsConstructor
public class OseidUserDetails implements UserDetails, OAuth2User, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	private String password;
	private String username;
	private String fullname;
	private String name;
	private Boolean enabled;
	private Boolean locked;
	private String roles;

	private HashMap<String, Object> attributes;

	public OseidUserDetails(String email) {
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (roles != null) {
			authorities.add(new SimpleGrantedAuthority(roles));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public Map<String, Object> getAttributes() {

		Map<String, Object> copy = new HashMap<>();
		if (this.attributes == null) {

			this.attributes = new HashMap<>();
			this.attributes.put("email", this.getEmail());
			copy.put("email", this.getEmail());
			this.attributes.put("name", this.getName());
			copy.put("name", this.getName());
			this.attributes.put("username", this.getUsername());
			copy.put("username", this.getUsername());
		} else {

			for (String s : this.attributes.keySet()) {
				copy.put(s, this.attributes.get(s));
			}
		}

		return copy;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public int HashCode() {
		return Objects.hash(email, name, username);
	}

	public boolean Equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		OseidUserDetails other = (OseidUserDetails) object;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(username, other.username);
	}

	public Object clone() {
		OseidUserDetails copy = null;

		try {
			copy = (OseidUserDetails) super.clone();
		} catch (CloneNotSupportedException c) {
			c.printStackTrace();
		}

		if (attributes != null) {
			copy.attributes.put("email", this.attributes.get("email"));
			copy.attributes.put("name", this.attributes.get("name"));
			copy.attributes.put("username", this.attributes.get("username"));
		}

		return copy;

	}

}
