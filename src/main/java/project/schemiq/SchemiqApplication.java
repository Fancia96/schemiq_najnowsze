package project.schemiq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchemiqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchemiqApplication.class, args);
	}

	// to mozna pobrac skądś
	public static String[] badWords = {"milk", "league", "hai", "java"};

	public static String userNotFound = "~~User not found~~";
	public static String elementNotFound = "~~Element not found~~";
	public static String boardNotFound = "~~Board not found~~";

	public static String messageNotFound = "~~Message not found~~";
	public static String friendshipNotFound = "~~Friendship not found~~";
}
