package com.apiwiz.apiwiz.Controller;

import com.apiwiz.apiwiz.Dtos.LoginDto;
import com.apiwiz.apiwiz.Dtos.UserDto;
import com.apiwiz.apiwiz.Model.Role;
import com.apiwiz.apiwiz.Model.UserEntity;
import com.apiwiz.apiwiz.Repository.RoleRepository;
import com.apiwiz.apiwiz.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/register")
public class RegistrationLoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    public RegistrationLoginController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/create")
    public String createUser(@RequestBody()UserDto userDto){
        UserEntity user= UserEntity.builder().first_name(userDto.getFirst_name())
                .last_name(userDto.getLast_name())
                .email(userDto.getEmail())
                .phone_number(userDto.getPhone_number())
                .password(passwordEncoder.encode(userDto.getPassword())).build();
        Role role=roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user).getId();

    }

    @PostMapping("/login")
    public String login(@RequestParam("email")String email,@RequestParam("password")String password, HttpSession session) throws Exception{
    	if(userRepository.existsByEmail(email)) {
    		UserEntity userEntity= userRepository.findByEmail(email);
    		if(userEntity.getPassword().equals(password)) {
    			Authentication authentication=authenticationManager.authenticate(
    	                new UsernamePasswordAuthenticationToken(email,password));
    	        SecurityContextHolder.getContext().setAuthentication(authentication);
    	        session.setAttribute("security",authentication);
    	        session.setAttribute("email",email);
    	        System.out.println(session.getId());
    	        return "Success";
    		}
    		else {
    			throw new Exception("please check username or password");
    		}
    	}
    	else {
		throw new Exception("please check username or password");
    	}
    }
    
}
