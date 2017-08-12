package ap.config;

import ap.entity.Phone;
import ap.service.PhoneService;
import ap.service.UserService;
import ap.service.serviceImpl.PhoneServiceImpl;
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
    PhoneService phoneService(){
        return new PhoneServiceImpl();
    }
}
