package mysocialinfo.mysocialinfo.controllers;

import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {

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
}
