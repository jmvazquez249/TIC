package um.edu.uy;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.ui.JavaFXApplication;

@SpringBootApplication
public class Main {


    private static ConfigurableApplicationContext context;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    public static void main(String[] args) {
        Main.context = SpringApplication.run(Main.class);

        Application.launch(JavaFXApplication.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

}

