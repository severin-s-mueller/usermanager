package com.usermanager;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

/**
 * @author Severin Müller
 * 
 * Die Klasse User repräsentiert einen Benutzer und ist als JPA-Entity-Klasse implementiert.  
 */
@Entity
public class User {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private  Integer userId;
	@Column(name = "email",unique=true,nullable=false)
	private String email;
	@Column(nullable=false)
	private String lastName;
	@Column(nullable=false)
	private String firstName;
	private enum UserRole{USER,ADMIN};
	@Column(nullable=false)
	private UserRole role;
	@Column(nullable=false)
	private Date created;

	/**
	 * @param userId	Numerische ID und Primärschlüssel des Users
	 * @param email		E-Mail-Adresse (muss eindeutig sein)
	 * @param lastName	Nachname
	 * @param firstName	Vorname
	 * @param role		Rolle des Users, entweder regulärer User oder Admin
	 * @param created	Erstelldatum des Benutzers
	 */
	
	public User() {
		
	}
	public User(Integer userId, String email, String lastName, String firstName, UserRole role, Date created) {
		super();
		this.userId = userId;
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.role = role;
		this.created = created;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}

}
