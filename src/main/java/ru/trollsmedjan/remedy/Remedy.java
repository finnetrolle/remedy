package ru.trollsmedjan.remedy;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by finnetrolle on 27.07.2015.
 */

@SpringBootApplication
public class Remedy {

    private static final Logger log = Logger.getLogger(Remedy.class);

    public static void main(String[] args) {

        log.info("Starting remedy application");
        log.info("---== by Finne Trolle ==---");
        SpringApplication.run(Remedy.class, args);

    }

}
