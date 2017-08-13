package ap.controllers;

import ap.dao.UserDao;
import ap.entity.User;
import ap.dao.BasicDao;
import org.osgi.service.device.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Random;

@Controller
public class StartController {
private final static String[] names={"Иван", "Том","Фредди","Женя","Тимофей"};
private final static String[] subNames={"Иванов", "Сидоров","Петров","Семенов","Курицын"};

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPAge(Model model) {
        long countRow = userDao.getCountRow();
        if(countRow<3){
            for(int i =0; i<3-countRow;i++){
                User user = new User();
                String name= randomName();
                user.setName(name);
                userDao.save(user);
            }
        }
        return "index";
    }

    private String randomName() {
        int name = (int) (Math.random() * names.length);
        int sub = (int) (Math.random() * subNames.length);
        return names[name]+" "+subNames[sub];
    }

}