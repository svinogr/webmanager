package ap.service;

import ap.entity.Mail;

import java.util.List;

public interface MailService {
    boolean createNewMail(Mail mail);
    Mail getMailById(int id);
    boolean deleteMail(Mail mail);
    boolean updateMail(Mail mail);
    List<Mail> getAll();
}
