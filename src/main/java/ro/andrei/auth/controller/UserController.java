package ro.andrei.auth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.andrei.auth.DTO.LoginDTO;
import ro.andrei.auth.DTO.UserDTO;
import ro.andrei.auth.DTO.UserInfoDTO;
import ro.andrei.auth.exception.CustomException;
import ro.andrei.auth.model.User;
import ro.andrei.auth.security.JwtProvider;
import ro.andrei.auth.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

   @Autowired
   UserService userService;

   @Autowired
   JwtProvider jwtProvider;

   @Autowired
   ModelMapper modelMapper;

   @GetMapping("/api")
   public String home() {
      return "JWT AUTH DEMO";
   }

   @PostMapping("/auth/register")
   public UserInfoDTO register(@RequestBody UserDTO userDTO) {
      User userToSave = userService.findUserByEmail(userDTO.getEmail());
      if(userToSave != null) {
         throw new CustomException("Email exist", HttpStatus.BAD_REQUEST);
      }

      userToSave = modelMapper.map(userDTO, User.class);
      userToSave.setUserRole("ROLE_USER");
      User userAdded = userService.create(userToSave);

      return modelMapper.map(userAdded,UserInfoDTO.class);

   }

   @PostMapping("/auth/login")
   public Map<String, Object> login(@RequestBody LoginDTO loginDTO) {
      Map<String, Object> response = new HashMap<>();

      User user = userService.findUserByEmail(loginDTO.getEmail());
      if(user == null || !userService.passwordMatches(loginDTO.getPassword(), user.getPassword())) {
         throw new CustomException("Wrong email or password ", HttpStatus.BAD_REQUEST);
      }

      String token = jwtProvider.loginAndGetToken(user.getEmail(),loginDTO.getPassword());
      response.put("token", token);

      return response;
   }

   @GetMapping("/find/{id}")
   public User find(@PathVariable(value = "id") Long id){
      return userService.find(id);
   }

   @PostMapping("/admin/role")
   public String admin() {
      return "Admin";
   }

   @PostMapping("/user/role")
   public String user() {
      return "User";
   }
}

