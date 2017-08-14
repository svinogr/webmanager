package ap.service.serviceImpl;

import ap.entity.User;
import ap.dao.UserDao;
import ap.service.UserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    public boolean createNewUser(User user) {
        try {
            User newUser = userDao.save(user);
            return newUser != null;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Transactional
    public User getUserById(int id) {
        try {
            User byID = userDao.getByID(id);
            return byID;
        } catch (HibernateException e) {
            return null;
        }
    }

    public boolean deleteUser(User user) {
        try {
            userDao.delete(user);
        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }


    public boolean updateUser(User user) {
        try {
            userDao.update(user);
        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public List<User> getAll() {
        try {
            List<User> list = userDao.getAll();
            for (User user : list) {
                user.setListMails(null);
            }
            return list;
        } catch (HibernateException e) {
            return null;
        }
    }

    public long getCountRow() {
        try {
            return userDao.getCountRow();
        }catch (HibernateException e){
            return 0;
        }
    }
}
