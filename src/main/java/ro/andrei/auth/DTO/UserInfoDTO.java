package ro.andrei.auth.DTO;

import java.util.UUID;

public class UserInfoDTO {

   private UUID id;
   private String email;
   private String password;
   private String userRole;

   public UUID getId () {
      return id;
   }

   public void setId (UUID id) {
      this.id = id;
   }

   public String getEmail () {
      return email;
   }

   public void setEmail (String email) {
      this.email = email;
   }

   public String getPassword () {
      return password;
   }

   public void setPassword (String password) {
      this.password = password;
   }

   public String getUserRole () {
      return userRole;
   }

   public void setUserRole (String userRole) {
      this.userRole = userRole;
   }
}
