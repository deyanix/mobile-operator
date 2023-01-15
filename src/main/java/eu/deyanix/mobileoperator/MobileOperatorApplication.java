package eu.deyanix.mobileoperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationContextException.class)
public class MobileOperatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileOperatorApplication.class, args);
    }

}
