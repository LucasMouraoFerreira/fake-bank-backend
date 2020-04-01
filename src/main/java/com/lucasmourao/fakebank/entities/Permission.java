package com.lucasmourao.fakebank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_permission")
public class Permission implements Serializable, GrantedAuthority{

	private static final long serialVersionUID = -6128423061616362067L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userPermission;
	
	@Override
	public String getAuthority() {
		return this.userPermission;
	}
	
	public Permission() {}

	public Permission(Long id, String userPermission) {
		super();
		this.id = id;
		this.userPermission = userPermission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userPermission == null) ? 0 : userPermission.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userPermission == null) {
			if (other.userPermission != null)
				return false;
		} else if (!userPermission.equals(other.userPermission))
			return false;
		return true;
	}
	
	

}
