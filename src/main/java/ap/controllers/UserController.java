package ap.controllers;

import ap.entity.Mail;
import ap.entity.User;
import ap.service.MailService;
import ap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.POST)
    public User createNewUser(@RequestBody @Valid User user, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            user = getUserErrors(user, bindingResult);
            response.setStatus(400);
            return user;
        }
        if (userService.createNewUser(user)) {
            response.setStatus(201);
            response.setHeader("Location", "/api/v.1/user/" + user.getId());
        } else response.setStatus(500);
        return user;
    }

    @RequestMapping(value = "/{id}/mail", method = RequestMethod.GET)
    @Transactional
    public User getUserById(@PathVariable int id, HttpServletResponse response) {
        User user = userService.getUserById(id);
        if (user == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        System.out.println(user);
        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id, HttpServletResponse response) {
        User user = new User();
        user.setId(id);
        if (userService.deleteUser(user)) {
            response.setStatus(200);
        } else response.setStatus(404);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable int id, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            user = getUserErrors(user, bindingResult);
            response.setStatus(400);
            return user;
        }

        user.setId(id);

        if (userService.updateUser(user)) {
            response.setStatus(200);
            return user;
        }
        response.setStatus(404);
        return user;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    public List<User> getAllUsers(HttpServletResponse response) {
        List<User> userList = userService.getAll();
        if (userList.size() < 1) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return userList;
    }

    @RequestMapping(value = "{id}/mail", method = RequestMethod.POST)
    @Transactional
    public Mail createNewUser(@RequestBody @Valid Mail mail, BindingResult bindingResult, @PathVariable int id, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            mail = getMailErrors(mail, bindingResult);
            response.setStatus(400);
            return mail;
        }

        mail.setId(id);
        if (!mailService.createNewMail(mail)) {
            response.setStatus(400);
            return mail;
        }

        response.setStatus(201);
        response.setHeader("Location", "/api/user/" + id + "/mail/" + mail.getId());
        return mail;
    }

    @RequestMapping(value = "/mail/{id}", method = RequestMethod.GET)
    public Mail getMailById(@PathVariable int id, HttpServletResponse response) {
        Mail mailById = mailService.getMailById(id);
        if (mailById == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return mailById;
    }

    @RequestMapping(value = "/mail/{id}", method = RequestMethod.PUT)
    public Mail getUpdateById(@RequestBody @Valid Mail mail, BindingResult bindingResult, @PathVariable int id, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            mail = getMailErrors(mail, bindingResult);
            response.setStatus(400);
            return mail;
        }
        mail.setId(id);

        if (mailService.updateMail(mail)) {
            response.setStatus(200);
            return mail;
        }
        response.setStatus(404);
        return mail;

    }

    @RequestMapping(value = "/mail/{id}", method = RequestMethod.DELETE)
    public void deleteMailById(@PathVariable int id, HttpServletResponse response) {
        Mail mail = new Mail();
        mail.setId(id);
        if (mailService.deleteMail(mail)) {
            response.setStatus(200);
        } else response.setStatus(404);
    }

    private User getUserErrors(User user, BindingResult bindingResult) {
        Map<String, String> mapErrors = new HashMap();
        for (FieldError error : bindingResult.getFieldErrors()) {
            mapErrors.put(error.getField(), error.getDefaultMessage());
        }
        user.setName(mapErrors.get("name"));
        return user;
    }

    private Mail getMailErrors(Mail mail, BindingResult bindingResult) {
        Map<String, String> mapErrors = new HashMap();
        for (FieldError error : bindingResult.getFieldErrors()) {
            mapErrors.put(error.getField(), error.getDefaultMessage());
        }
        mail.setError(mapErrors.get("mail"));
        return mail;
    }
}



