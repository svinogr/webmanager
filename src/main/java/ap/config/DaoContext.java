package ap.config;

import ap.dao.BasicDao;
import ap.dao.PhoneDao;
import ap.dao.UserDao;
import ap.dao.daoImpl.BasicDaoImpl;
import ap.dao.daoImpl.PhoneDaoImpl;
import ap.dao.daoImpl.UserDaoImpl;
import ap.entity.Phone;
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
    PhoneDao phoneDao(){
        return new PhoneDaoImpl();
    }

}
