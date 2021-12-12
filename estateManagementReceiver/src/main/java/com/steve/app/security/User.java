package com.steve.app.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(nullable = false)
	private String password = " ";
	private String username ;
	private String UserPermissions = "none" ;
	private String UserRoles ="none";
	
	private LocalDateTime createdAt ; 
	private boolean Active = false ;
	
	public User() {
		this.createdAt = LocalDateTime.now();
		this.UserRoles = "none"; 
		this.UserPermissions = "none";
	}
	
	public User(String password, String username, String userPermissions,
			String userRoles, boolean isActive) {
		this.password = password;
		this.username = username;
		if(userPermissions.equalsIgnoreCase("") || userPermissions.equalsIgnoreCase(" "))
			this.UserPermissions = "none";
		else 
			this.UserPermissions = userPermissions ; 
		if(userRoles.equalsIgnoreCase("") || userRoles.equalsIgnoreCase(" "))
			this.UserRoles = "none";
		else 
			this.UserRoles = userRoles; 
		this.Active = isActive;
		this.createdAt = LocalDateTime.now(); 
	}
	
	public void flatUserDetailes() {
		System.out.println("user ID : "+this.id+" username :"+this.username+" role "+this.UserRoles +" permissions : "+this.UserPermissions);
	}
	
	public boolean hasRole(String role) {
		if(this.UserRoles.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertRolesToList().contains(role)) {
			return false ;
		}
		return true ; 
	}
	
	public boolean hasPermission(String permission) {
		if(this.UserPermissions.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertPermissionsToList().contains(permission)) {
			return false  ; 
		}
		return true ; 
	}

	public void addRole(String role ) {
		if(this.UserRoles.equalsIgnoreCase(" ") || this.UserRoles.equalsIgnoreCase("")) {
			this.UserRoles=role;
		}else
		this.UserRoles+=","+role;
	}
	
	public void addPermission(String permission ) {
		if(this.UserPermissions.equalsIgnoreCase(" ") || this.UserPermissions.equalsIgnoreCase("")) {
			this.UserPermissions=permission;
		}else
		this.UserPermissions+=","+permission;
	}

	public List<String> convertPermissionsToList(){
		if(this.UserPermissions.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userPermissions = new ArrayList<String>() ;
			String[] permissions = this.UserPermissions.split(",");
			for(int i = 0 ; i<permissions.length ; i++) {
				userPermissions.add(permissions[i]);
			}
			return userPermissions ; 
		}
	}
	
	public List<String> convertRolesToList(){
		if(this.UserRoles.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userRoles = new ArrayList<String>() ;
			String[] roles = this.UserRoles.split(",");
			for(int i =0 ; i < roles.length ; i++) {
				userRoles.add(roles[i]); 
			}
			return userRoles ; 
			
		}
	}
	
	public void revokeRoleFromUser(String role ) {
		List<String> userRoles = this.convertRolesToList() ; 
		if(userRoles.size() != 0 ) {
		if(userRoles.contains(role)) {
			userRoles.remove(userRoles.indexOf(role));
			this.UserRoles = "";
			for(String tempRole : userRoles) {
				this.addRole(tempRole);
				}
			}
		}
	}
	
	public void revokePermissionFromUser(String permission) {
		List<String> userPermissions = this.convertPermissionsToList();
		if(userPermissions.size() != 0 ) {
			if(userPermissions.contains(permission)) {
				userPermissions.remove(permission);
				this.UserPermissions = "";
				for(String tempPermission : userPermissions) {
					this.addPermission(tempPermission);
				}
			}
		}
	}

}
