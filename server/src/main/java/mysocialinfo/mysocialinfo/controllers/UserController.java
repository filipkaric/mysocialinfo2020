package mysocialinfo.mysocialinfo.controllers;

import mysocialinfo.mysocialinfo.businesslogic.SocialDataBL;
import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.SocialData;
import mysocialinfo.mysocialinfo.models.User;
import mysocialinfo.mysocialinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String Save(@RequestBody User user){
        try {
            User userBase = this.userRepository.findByEmail(user.getEmail());
            if (userBase == null) {
                userRepository.save(user);
                return "Успешно креиран корисник";
            } else {
                return "Корисничко име је заузето";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return  "Грешка при креирању корисника";
        }
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public User Login(@RequestBody LoginData loginData){
        if(loginData.getUsername().equals("123") && loginData.getPassword().equals("123")){
            User ulogovanKorisnik = new User();
            ulogovanKorisnik.setFirstname("Filip");
            ulogovanKorisnik.setLastname("Karic");
            return ulogovanKorisnik;
        }
        return null;
    }
}
