package ap.dao.daoImpl;

import ap.dao.MailDao;
import ap.entity.Mail;

public class MailDaoImpl extends BasicDaoImpl<Mail> implements MailDao {
    public MailDaoImpl() {
        super(Mail.class);
    }
}
