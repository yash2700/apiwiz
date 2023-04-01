package com.apiwiz.apiwiz.Controller;

import com.apiwiz.apiwiz.Dtos.LoginDto;
import com.apiwiz.apiwiz.Dtos.SubscribeDto;
import com.apiwiz.apiwiz.Dtos.UserDto;
import com.apiwiz.apiwiz.Model.UserEntity;
import com.apiwiz.apiwiz.Repository.RoleRepository;
import com.apiwiz.apiwiz.Repository.UserRepository;
import com.apiwiz.apiwiz.Service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/getByEmail")
    public UserEntity getByEmail(@RequestParam()String emailId){
        return userService.getByEmailId(emailId);
    }

    @GetMapping("/getBySymbol")
    public HashMap<String, HashMap<String, Object>> getBySymbol(@RequestParam("symbol")String symbol,@RequestParam("start_ddate")String start_date,@RequestParam("end_date")String end_date,HttpSession session) throws JsonProcessingException {
        SecurityContextHolder.getContext().setAuthentication((Authentication) session.getAttribute("security"));
        System.out.println(session.getAttribute("email"));
        System.out.println(session.getId());
        return userService.getBySymbol(symbol,start_date,end_date);
    }



    @PostMapping("/subscribe")
    public String subscribe(@RequestBody()SubscribeDto subscribeDto,HttpSession session){
        SecurityContextHolder.getContext().setAuthentication((Authentication) session.getAttribute("security"));
        String email= (String) session.getAttribute("email");
        System.out.println(email);
        System.out.println(session.getId());
        return userService.subscribe(subscribeDto,email);
    }
//    @Scheduled(cron = "* * * * * *")
//    public void schedule(){
//        String currTime=new SimpleDateFormat("YYYY-MM-dd").format(new Date());
//        System.out.println(currTime);
//    }

}
