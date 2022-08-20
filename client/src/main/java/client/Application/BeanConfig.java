package client.Application;

import client.Application.UI.Controller.Chat;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "client")
@EnableAspectJAutoProxy
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public Chat chat() {
        return new Chat();
    }
}