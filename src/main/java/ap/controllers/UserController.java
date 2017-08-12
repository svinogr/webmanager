package ap.controllers;

import ap.entity.Phone;
import ap.entity.User;
import ap.service.PhoneService;
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
    PhoneService phoneService;

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

    @RequestMapping(value = "/{id}/phone", method = RequestMethod.GET)
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

    @RequestMapping(value = "{id}/phone", method = RequestMethod.POST)
    @Transactional
    public Phone createNewUser(@RequestBody @Valid Phone phone, BindingResult bindingResult, @PathVariable int id, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            phone = getPhoneErrors(phone, bindingResult);
            response.setStatus(400);
            return phone;
        }

        phone.setId(id);
        if (!phoneService.createNewPhone(phone)) {
            response.setStatus(400);
            return phone;
        }

        response.setStatus(201);
        response.setHeader("Location", "/api/v.1/user/" + id + "/phone/" + phone.getId());
        return phone;
    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
    public Phone getPhoneById(@PathVariable int id, HttpServletResponse response) {
        Phone phoneById = phoneService.getPhoneById(id);
        if (phoneById == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return phoneById;
    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.PUT)
    public Phone getUpdateById(@RequestBody @Valid Phone phone, BindingResult bindingResult, @PathVariable int id, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            phone = getPhoneErrors(phone, bindingResult);
            response.setStatus(400);
            return phone;
        }
        phone.setId(id);

        if (phoneService.updatePhone(phone)) {
            response.setStatus(200);
            return phone;
        }
        response.setStatus(404);
        return phone;

    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
    public void deletePhoneById(@PathVariable int id, HttpServletResponse response) {
        Phone phone = new Phone();
        phone.setId(id);
        if (phoneService.deletePhone(phone)) {
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

    private Phone getPhoneErrors(Phone phone, BindingResult bindingResult) {
        Map<String, String> mapErrors = new HashMap();
        for (FieldError error : bindingResult.getFieldErrors()) {
            mapErrors.put(error.getField(), error.getDefaultMessage());
        }
        phone.setError(mapErrors.get("number"));
        return phone;
    }
}



