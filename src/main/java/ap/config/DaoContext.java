package ap.config;

import ap.dao.BasicDao;
import ap.dao.MailDao;
import ap.dao.UserDao;
import ap.dao.daoImpl.BasicDaoImpl;
import ap.dao.daoImpl.MailDaoImpl;
import ap.dao.daoImpl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoContext {
    @Bean
    BasicDao basicDao(){
        return new BasicDaoImpl();
    }

    @Bean
    UserDao userDao(){
        return new UserDaoImpl();
    }

    @Bean
    MailDao phoneDao(){
        return new MailDaoImpl();
    }

}
