package com;


import com.nevexis.pyramid.TaxService;
import com.nevexis.pyramid.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@SpringBootApplication

public class App {
    @Autowired
    private TaxService taxService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


}
