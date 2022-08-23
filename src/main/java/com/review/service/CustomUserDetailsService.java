package com.review.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.review.model.Role;
import com.review.model.User;
import com.review.repository.UserRepository;
import com.review.repository.RoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);


		 /*Set<Role> set = new HashSet<Role>(); set.add(new Role(1, "ADMIN"));
		 System.out.println(encoder.encode("password"));*/


		if(user != null) {
			//user.setRoles(set);
			List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
			return buildUserForAuthentication(user, authorities);
		} else {
			throw new UsernameNotFoundException("Username not found.");
		}
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach(role -> {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});
		List<GrantedAuthority> authorities = new ArrayList<>(roles);
		return authorities;
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Role userRoles = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRoles)));
		userRepository.save(user);
	}
}
