package com.devmountain.photocollect.services;

import com.devmountain.photocollect.dtos.UserDto;
import com.devmountain.photocollect.entities.User;
import com.devmountain.photocollect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

///Service Layer is where the business logic goes///

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("http://localhost:8090/login.html");
        return response;
    }

    @Override
    public List<String> userLogin(UserDto userDto){
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isPresent()){
            if(passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())){
                response.add("http://localhost:8090/home.html");
                response.add(String.valueOf(userOptional.get().getId()));
                System.out.println("hello world");
            } else {
                System.out.println("password incorrect");
                response.add("Username or password incorrect");
            }
        } else {
            System.out.println("User does not exist");
            response.add("Username or password incorrect");
        }
        return response;
    }

}
