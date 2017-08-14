package ap.config;

import ap.service.MailService;
import ap.service.UserService;
import ap.service.serviceImpl.MailServiceImpl;
import ap.service.serviceImpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceContext {
    @Bean
    UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    MailService mailService(){
        return new MailServiceImpl();
    }
}
