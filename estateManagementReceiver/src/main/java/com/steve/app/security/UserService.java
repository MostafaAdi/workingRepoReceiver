package com.steve.app.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class UserService {

	@Autowired 
	UserRepository userRepository ; 	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public UserService() {

	}
	
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	@Transactional
	public User getUserByID(int id ) {
		Optional<User> optional = this.userRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ; 
		}
	}
	
	@Transactional
	public User getUserByUserName(String userName) {
		for(User user : this.userRepository.findAll()) {
			if(user.getUsername().equalsIgnoreCase(userName)) {
				return user ; 
			}
		}
		return null ;
	}
	
	@Transactional
	public String addUser(User user ) {
		user.flatUserDetailes();
		if(checkUserinforDuplication(user)) {
			return "User already exist in the system";
		}
		else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setActive(false);
			this.userRepository.save(user);
			return "ok";
		}
		
	}
	
	@Transactional
	public String updateUser(User user) {
		String result = "";
		try {
			User dataUser = getUserByID(user.getId());
			if(dataUser != null) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				if(dataUser.isActive())
					user.setActive(true);
				this.userRepository.save(user); 
				return "ok";
			}
		}catch(Exception e ) {
			System.out.println("User Not found ");
		}
		return result ;  
	}
	
	public void deleteUser(User user ) {
		try {
			this.userRepository.deleteById(user.getId());
		}catch(Exception e ){
			System.out.println("User Not found ");
		}
	}
	
	public boolean checkUserinforDuplication(User user ) {
		List<User> usersList = this.userRepository.findAll() ; 
		for(int i = 0 ; i < usersList.size() ; i++ ) {
			User tempUser = usersList.get(i) ;
			if(tempUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				return true ; 
			}
		}
		return false ; 
	}
	
	public List<User> getNonActiveUsers() {
		List<User> allUsers = this.userRepository.findAll() ; 
		List<User> nonActiveUsers = new ArrayList<User>(); 
		for(User user : allUsers) {
			if(!user.isActive()) {
				nonActiveUsers.add(user);
			}
		}
		return nonActiveUsers; 
	}
	
	public List<User> getActiveUsers(){
		List<User> allUsers = this.userRepository.findAll() ; 
		List<User> ActiveUsers = new ArrayList<User>(); 
		for(User user : allUsers) {
			if(user.isActive()) {
				ActiveUsers.add(user);
			}
		}
		return ActiveUsers;
	}
	
	public void activateUser(int userid) {
		User user = this.getUserByID(userid);
		if(user != null ) {
			user.setActive(true);
			this.userRepository.save(user);
		}else {
			System.out.println("user not found ");
		}
	}
	
	public void deActivateUser(int userid) {
		User user = this.getUserByID(userid);
		if(user != null ) {
			user.setActive(false);
			this.userRepository.save(user);
		}else {
			System.out.println("user not found ");
		}	
	}
}
