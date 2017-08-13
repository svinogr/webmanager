package ap.service.serviceImpl;

import ap.dao.MailDao;
import ap.dao.UserDao;
import ap.entity.Mail;
import ap.entity.User;
import ap.service.MailService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    MailDao mailDao;

    @Autowired
    UserDao userDao;

    @Transactional
    public boolean createNewMail(Mail mail) {
        User byID = userDao.getByID(mail.getId());
        if (byID == null) {
            mail.setError("user not found");
            return false;
        }

        try {
            mail.setParentId(byID);
            mailDao.save(mail);
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    public Mail getMailById(int id) {
        try {
            Mail byID = mailDao.getByID(id);
            return byID;
        } catch (HibernateException e) {
            return null;
        }
    }

    public boolean deleteMail(Mail mail) {
        try {
            mailDao.delete(mail);
        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean updateMail(Mail mail) {
        try {
            Mail byID = mailDao.getByID(mail.getId());
            if(byID==null){
                mail.setError("mail is absent");
                return false;
            }
            byID.setMail(mail.getMail());
            mailDao.update(byID);
            mail = byID;

        } catch (HibernateOptimisticLockingFailureException | HibernateException e) {
            return false;
        }
        return true;
    }

    public List<Mail> getAll() {
        return null;
    }
}
