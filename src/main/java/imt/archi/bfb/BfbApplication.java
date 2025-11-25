package imt.archi.bfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class BfbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfbApplication.class, args);
	}

}
