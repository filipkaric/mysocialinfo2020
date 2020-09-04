package mysocialinfo.mysocialinfo.service;

import mysocialinfo.mysocialinfo.models.LoginData;
import mysocialinfo.mysocialinfo.models.User;

public interface LoginService {
    User loginUser(LoginData loginData);

    boolean logOutUser();
}
