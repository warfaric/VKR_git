package ru.warfaric.redgmapp;

import org.springframework.boot.SpringApplication;

public class TestRedgmAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(RedgmAppApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
