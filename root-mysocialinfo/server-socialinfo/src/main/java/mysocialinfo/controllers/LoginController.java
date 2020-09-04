package mysocialinfo.controllers;

import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.User;
import mysocialinfo.mysocialinfo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User Login(@RequestBody LoginData loginData) {
        try {
            return loginService.loginUser(loginData);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping("/logout")
    public void logout() {
        try {
            loginService.logOutUser();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public boolean test() {
        try {
           return loginService.logOutUser();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }


    // kako izvuci iz sesije usera!!!
//    User user = loginService.loginUser(loginData);
//    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//    HttpSession session = attr.getRequest().getSession();
//            System.out.println("Email: " + session.getAttribute(Constant.EMAIL));
//            System.out.println("userID: " + session.getAttribute(Constant.USER_ID));
//            return user;
}
