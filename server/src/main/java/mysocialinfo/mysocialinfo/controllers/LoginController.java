package mysocialinfo.mysocialinfo.controllers;

import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.User;
import mysocialinfo.mysocialinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/test")
    public String Test(){
        return "Test";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User Login(@RequestBody LoginData loginData){
        if(loginData.getUsername().equals("123") && loginData.getPassword().equals("123")){
            User ulogovanKorisnik = new User();
            ulogovanKorisnik.setFirstname("Filip");
            ulogovanKorisnik.setLastname("Karic");
            return ulogovanKorisnik;
        }
        return null;
    }

    @RequestMapping("/save")
    public String Save(){
        User ulogovanKorisnik = new User();
        ulogovanKorisnik.setFirstname("Filip");
        ulogovanKorisnik.setLastname("Karic");
        ulogovanKorisnik.setEmail("kfilip94@gmail.com");
        userRepository.save(ulogovanKorisnik);
        return "Test";
    }
}
