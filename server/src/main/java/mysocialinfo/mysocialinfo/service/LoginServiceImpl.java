package mysocialinfo.mysocialinfo.service;

import mysocialinfo.mysocialinfo.config.AuthenticationPrincipal;
import mysocialinfo.mysocialinfo.constants.Constant;
import mysocialinfo.mysocialinfo.controllers.LoginController;
import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.User;
import mysocialinfo.mysocialinfo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private Authentication authentication;

    @Autowired
    private AuthenticationPrincipal authenticationPrincipal;

    @Autowired
    UserRepository userRepository;

    @Override
    public User loginUser(LoginData loginData) {
        User user = userRepository.findByEmailAndPassword(loginData.getUsername(), loginData.getPassword());

        // save user to session
        if (user != null) {
            saveUserToSession(user);
        }

        return user;
    }

    @Override
    public boolean logOutUser() {
        try {
            Authentication loggedInUser = authenticationPrincipal.getAuthentication();
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session = attr.getRequest().getSession();
            HttpSession session = attr.getRequest().getSession(true);
            log.info("Invoking logOutUser() method! "
                    + "\n With username: " + loggedInUser.getPrincipal().toString());
            if (loggedInUser.getPrincipal() != null && loggedInUser.getCredentials() != null) {
                String userId = loggedInUser.getPrincipal().toString();
                String email = loggedInUser.getCredentials().toString();

                session.invalidate();
                log.info("User with user ID: " + userId + ", and with email: " + email + " logged out successfully.");
            }
            return true;
        } catch (Exception e) {
            log.error("User logout failed.");
            return false;
        }
    }

    private void saveUserToSession(User user) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession();
        session.setAttribute(Constant.USER_ID, user.getId());
        session.setAttribute(Constant.EMAIL, user.getEmail());

        authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getEmail());
        authenticationPrincipal.setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
