package com.example.webdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.boot.builder.SpringApplicationBuilder;*/

@SpringBootApplication
public class WebDevSummerApplication {

	public static void main(String[] args) {
		/*SpringApplicationBuilder builder = new SpringApplicationBuilder(WebDevSummerApplication.class);
        builder.headless(false).run(args);*/
		SpringApplication.run(WebDevSummerApplication.class, args);
	}
}
