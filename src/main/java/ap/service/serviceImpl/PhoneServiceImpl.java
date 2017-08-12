package ap.service.serviceImpl;

import ap.dao.PhoneDao;
import ap.dao.UserDao;
import ap.entity.Phone;
import ap.entity.User;
import ap.service.PhoneService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    PhoneDao phoneDao;

    @Autowired
    UserDao userDao;

    @Transactional
    public boolean createNewPhone(Phone phone) {
        User byID = userDao.getByID(phone.getId());
        if (byID == null) {
            phone.setError("user not found");
            return false;
        }

        try {
            phone.setParentId(byID);
            phoneDao.save(phone);
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    public Phone getPhoneById(int id) {
        try {
            Phone byID = phoneDao.getByID(id);
            return byID;
        } catch (HibernateException e) {
            return null;
        }
    }

    public boolean deletePhone(Phone phone) {
        try {
            phoneDao.delete(phone);
        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean updatePhone(Phone phone) {
        try {
            Phone byID = phoneDao.getByID(phone.getId());
            if(byID==null){
                phone.setError("phone is absent");
                return false;
            }
            byID.setNumber(phone.getNumber());
            phoneDao.update(byID);
            phone = byID;

        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }

    public List<Phone> getAll() {
        return null;
    }
}
