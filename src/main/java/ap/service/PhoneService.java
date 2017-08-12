package ap.service;

import ap.entity.Phone;
import ap.entity.User;

import java.util.List;

public interface PhoneService {
    boolean createNewPhone(Phone phone);
    Phone getPhoneById(int id);
    boolean deletePhone(Phone phone);
    boolean updatePhone(Phone phone);
    List<Phone> getAll();
}
