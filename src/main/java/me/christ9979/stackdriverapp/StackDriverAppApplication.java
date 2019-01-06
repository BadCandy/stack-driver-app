package me.christ9979.stackdriverapp;

import com.google.cloud.logging.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class StackDriverAppApplication {

	private Logger logger = LoggerFactory.getLogger(StackDriverAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StackDriverAppApplication.class, args);
	}

	@GetMapping("/call/info")
	public String callInfo() {
		logger.info("Api called - info");
		return "info";
	}

	@GetMapping("/call/error")
	public String callError() {
		logger.error("Api called - error");
		return "error";
	}
}

