package org.trello.trelloclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.trello.trelloclone.config.EnvLoader;

@SpringBootApplication
public class TrelloCloneApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnv();
		SpringApplication.run(TrelloCloneApplication.class, args);
	}
}
