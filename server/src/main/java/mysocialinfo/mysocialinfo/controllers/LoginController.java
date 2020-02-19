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
//        StringBuffer requestURL = ((HttpServletRequest) request).getRequestURL();
//        String queryString = ((HttpServletRequest) request).getQueryString();
//        Map parametri = getQueryMap(queryString);
//        String code = (String)parametri.get("code");
//
//        URL url;
//
//        try {
//            url = new URL("https://graph.facebook.com/v5.0/oauth/access_token?client_id=490841858175046" +
//                    "&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fhome%2F" +
//                    "&client_secret=5065cf17fe5cab49d7a89e7354dc3630" +
//                    "&code=" + code);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            con.setRequestProperty("Content-Type", "application/json");
//
//            con.setUseCaches(false);
//            con.setDoOutput(true);
//            // Send request
//            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.close();
//            // Get Response
//            InputStream is = con.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//            String line;
//            while ((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
//
//            String responseBody = response.toString();
//            JSONObject jsonObject = new JSONObject(responseBody);
//            facebookAccessToken = jsonObject.getString("access_token");
//            System.out.println(facebookAccessToken);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        User user = new User();
//        user.setEmail(requestURL.append('?').append(queryString).toString());
//        return user;
    }

    @RequestMapping("/twitter")
    public User TwitterLogin() {
        String fica = socialDataBL.TwitterUrlToken();
        User user = new User();
        user.setToken(fica);
        return user;
    }

    @RequestMapping("/twitterLogin")
    public User TwitterLoginStvarno(ServletRequest request){
        if (!(request instanceof HttpServletRequest))
            return null;
        String fica = socialDataBL.LoginTwitter(request);
        User user = new User();
        user.setToken(fica);
        return user;
    }
}
