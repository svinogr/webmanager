package ap.dao.daoImpl;

import ap.dao.PhoneDao;
import ap.entity.Phone;

public class PhoneDaoImpl extends BasicDaoImpl<Phone> implements PhoneDao {
    public PhoneDaoImpl() {
        super(Phone.class);
    }
}
