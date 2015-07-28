package ru.trollsmedjan.remedy.configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;

import com.mangofactory.swagger.plugin.*;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;


@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig  {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(new ApiInfo("remedy", "Fozzie-sov campaigns automata", null, "maxsyachin@gmail.com", null, null))
                .includePatterns("/beacons.*"
                        ,"/entosers.*"
                        ,"/campaign.*"
                        ,"/monitoring.*"
//                        ,"/constellations.*"
//                        ,"/solarsystems.*"
                );
//        .includePatterns("/.*");
//                .includePatterns("/beacons.*");
    }


}
