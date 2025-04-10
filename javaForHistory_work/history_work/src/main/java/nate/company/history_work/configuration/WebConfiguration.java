package nate.company.history_work.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
this class
enables to handle security properly
https://stackoverflow.com/questions/44697883/can-you-completely-disable-cors-support-in-spring
 */
@Configuration
//@Bean
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //define the request allowed
                //registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}

