package ru.sberbank.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSberbankAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSberbankAppApplication.class, args);
        PrintService printService = new PrintService();
        printService.createPdfReport();
    }

}
