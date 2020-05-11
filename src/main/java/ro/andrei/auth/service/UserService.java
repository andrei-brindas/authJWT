package ro.andrei.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.andrei.auth.model.User;
import ro.andrei.auth.repository.UserRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserService implements CRUDService<User>, UserDetailsService {

   @Autowired
   UserRepository userRepository;

   @Autowired
   PasswordEncoder bCryptPasswordEncoder;


   @Override
   public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
      User user = userRepository.findUserByEmail(email);
      if(user != null){
         return buildUserForAuthentication(user);
      } else {
         throw new UsernameNotFoundException("Username not found.");
      }
   }

   @Override
   public User create (User user) {
      User newUser = new User();
      newUser.setId(0L);
      newUser.setEmail(user.getEmail());
      newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      newUser.setName(user.getName());
      newUser.setUserRole(user.getUserRole());
      return userRepository.save(newUser);

   }

   @Override
   public User find (Long i) {
      return userRepository.findUserById(i);
   }

   @Override
   public User update (User user) {
      User userToUpdate = userRepository.findUserById(user.getId());
      if(userToUpdate == null){
         userToUpdate = new User();
      }

      userToUpdate.setEmail(user.getEmail());
      userToUpdate.setPassword(user.getPassword());
      userToUpdate.setName(user.getName());
      return userRepository.save(userToUpdate);
   }

   @Override
   public void delete (User user) {
      userRepository.delete(user);
   }

   private static Collection<? extends GrantedAuthority> getAuthorities(User user){
      return AuthorityUtils.createAuthorityList(user.getUserRole());
   }

   private UserDetails buildUserForAuthentication(User user){
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
   }

   public Boolean passwordMatches(CharSequence  rawPassword, String encodedPassword){
      return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
   }

   public User findEmailAndPassword(String email, String password){
      return userRepository.findUserByEmailAndPassword(email, bCryptPasswordEncoder.encode(password));
   }

   public User findUserByEmail(String email){
      return userRepository.findUserByEmail(email);
   }
}
