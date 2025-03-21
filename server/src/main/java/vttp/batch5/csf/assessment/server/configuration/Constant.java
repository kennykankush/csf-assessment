package vttp.batch5.csf.assessment.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {

    @Value("${TOP.SECRET.CONFIDENTIAL}")
    public String secretSauce;
    
}
