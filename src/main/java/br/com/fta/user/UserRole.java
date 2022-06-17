package br.com.fta.user;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

	private static final long serialVersionUID = -5624745031630081413L;
	
	private String role;
	
	public UserRole(String role) {
		this.role = role;
	}
	
	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		return Objects.equals(role, other.role);
	}
	
}
