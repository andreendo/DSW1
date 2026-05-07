package com.github.users;

import com.github.users.domain.entities.Item;
import com.github.users.domain.entities.User;
import com.github.users.repositories.ItemRepository;
import com.github.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersApplication {

	private static final Logger logger = LoggerFactory.getLogger(UsersApplication.class);

	public static void main(String[] args) {
		var context = SpringApplication.run(UsersApplication.class, args);

		var userService = context.getBean(UserService.class);
		var itemRepository = context.getBean(ItemRepository.class);
		if (userService.count() == 0) {
			logger.info("No users found, adding some");
			var adm1 = userService.registerUser(new User("admin1", "admin1", "ADMIN"));
			itemRepository.save(new Item("a1", "a1", adm1));
			itemRepository.save(new Item("a1", "a1", adm1));
			itemRepository.save(new Item("a1", "a1", adm1));

			var adm2 = userService.registerUser(new User("admin2", "admin2", "ADMIN"));
			itemRepository.save(new Item("a2", "a2", adm2));
			itemRepository.save(new Item("a2", "a2", adm2));
			itemRepository.save(new Item("a2", "a2", adm2));
			itemRepository.save(new Item("a2", "a2", adm2));

			var usr1 = userService.registerUser(new User("user1", "user1", "USER"));
			itemRepository.save(new Item("u1", "u1", usr1));

			var usr2 = userService.registerUser(new User("user2", "user2", "USER"));
			itemRepository.save(new Item("u2", "u2", usr2));
			itemRepository.save(new Item("u2", "u2", usr2));
			itemRepository.save(new Item("u2", "u2", usr2));
			itemRepository.save(new Item("u2", "u2", usr2));
			itemRepository.save(new Item("u2", "u2", usr2));
		}
	}
}
