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
public class SocialController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SocialDataBL socialDataBL;

    String facebookAccessToken;

    @RequestMapping("/test")
    public String Test(){
        return facebookAccessToken;
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
        return socialDataBL.LoginTwitter(request);
    }

    @RequestMapping("/youtube")
    public SocialData LoginToYoutube(ServletRequest request){
        if (!(request instanceof HttpServletRequest))
            return null;
        return socialDataBL.YoutubeLogin(request);
    }
}
