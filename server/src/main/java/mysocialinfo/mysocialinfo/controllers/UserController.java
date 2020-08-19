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

    @RequestMapping("/save")
    public String Save(){
        User ulogovanKorisnik = new User();
        ulogovanKorisnik.setFirstname("Filip");
        ulogovanKorisnik.setLastname("Karic");
        ulogovanKorisnik.setEmail("kfilip94@gmail.com");
        userRepository.save(ulogovanKorisnik);
        return "facebookAccessToken";
    }
}
