package com.chenlm.tools;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GitlabRobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(GitlabRobotApplication.class, args);
    }

}
