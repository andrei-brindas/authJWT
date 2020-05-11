package ro.andrei.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework
		.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ro.andrei.auth.model.User;
import ro.andrei.auth.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Component
public class SetupConfiguration implements ApplicationRunner {

	@Autowired
	UserService userService;
	@Override
	public void run(ApplicationArguments args) {

		Map<String, String> printToConsole = new HashMap<>();

		User admin = userService.findUserByEmail("admin@admin.com");
		if (admin == null) {
			admin = new User();
			admin.setName("Admin");
			admin.setPassword("admin");
			admin.setEmail("admin@admin.com");
			userService.create(admin);
			printToConsole.put(admin.getEmail(), admin.getName());
		}

		User user = userService.findUserByEmail("user@user.com");
		if (user == null) {
			user = new User();
			user.setName("User");
			user.setPassword("user");
			user.setEmail("user@user.com");
			userService.create(user);
			printToConsole.put(user.getEmail(), user.getName());
		}
		System.out.println("Users: " + printToConsole);
	}

}
