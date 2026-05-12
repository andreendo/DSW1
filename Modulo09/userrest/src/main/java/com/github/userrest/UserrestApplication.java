package com.github.userrest;

import com.github.userrest.domain.entities.Item;
import com.github.userrest.domain.entities.User;
import com.github.userrest.repositories.ItemRepository;
import com.github.userrest.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserrestApplication {

    private final static Logger logger = LoggerFactory.getLogger(UserrestApplication.class);

	public static void main(String[] args) {
        var context = SpringApplication.run(UserrestApplication.class, args);

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
