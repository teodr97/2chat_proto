package org.coblt.twochat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
/*
 Needed in order to detect the "Message" entity from within
 the "commons" package for the DB. Otherwise, Spring doesn't see it.
 */
@EntityScan("commons")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}