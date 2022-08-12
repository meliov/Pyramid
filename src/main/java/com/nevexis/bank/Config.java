package com.nevexis.bank;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/bank.properties"})
public class Config {

}
