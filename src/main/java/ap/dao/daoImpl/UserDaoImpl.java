package ap.dao.daoImpl;

import ap.entity.User;
import ap.dao.UserDao;
import org.osgi.service.component.annotations.Component;

@Component
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {
    public UserDaoImpl() {
        super(User.class);
    }
}
