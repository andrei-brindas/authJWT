package ro.andrei.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.andrei.auth.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

   User findUserByEmail(String email);
   User findUserById(Long id);
   User findUserByEmailAndPassword(String email, String password);

}
