package ru.warfaric.redgmapp;

import org.springframework.boot.SpringApplication;

public class TestAdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(AdminServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
