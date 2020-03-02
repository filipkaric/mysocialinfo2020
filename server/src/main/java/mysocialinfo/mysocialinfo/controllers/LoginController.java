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
public class LoginController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SocialDataBL socialDataBL;

    String facebookAccessToken;

    @RequestMapping("/test")
    public String Test(){
        return facebookAccessToken;
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
        return "facebookAccessToken";
    }

    @RequestMapping("/facebook")
    public SocialData LoginToFacebook(ServletRequest request){
        if (!(request instanceof HttpServletRequest))
            return null;
        return socialDataBL.FacebookLogin(request);
    }

    @RequestMapping("/twitter")
    public User TwitterGetUrl() {
        String urlToken = socialDataBL.TwitterUrlToken();
        User user = new User();
        user.setToken(urlToken);
        return user;
    }

    @RequestMapping("/twitterLogin")
    public SocialData TwitterLogin(ServletRequest request){
        if (!(request instanceof HttpServletRequest))
            return null;
        SocialData fica = socialDataBL.LoginTwitter(request);
        return fica;
    }

    @RequestMapping("/youtube")
    public SocialData LoginToYoutube(ServletRequest request){
        if (!(request instanceof HttpServletRequest))
            return null;
        return socialDataBL.YoutubeLogin(request);
    }
}
