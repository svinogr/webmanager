package ap.controllers;

import ap.entity.User;
import ap.dao.BasicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartController {
    @Autowired
    BasicDao basicDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPAge(Model model) {
        User user = new User();
        user.setName("dfzc");
        basicDao.save(user);

        return "index";
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public void getIndexPge(Model model) {


    }


}