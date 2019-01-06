package me.christ9979.stackdriverapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.debug("Application Started - {}", "debug");
        log.info("Application Started - {}", "info");
        log.error("Application Started - {}", "error");
    }
}
