package mysocialinfo.mysocialinfo.businesslogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import mysocialinfo.mysocialinfo.config.AuthenticationPrincipal;
import mysocialinfo.mysocialinfo.helpers.OAuth1AuthorizationHeaderBuilder;
import mysocialinfo.mysocialinfo.models.*;
import mysocialinfo.mysocialinfo.repository.UserProfileRepository;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.Param;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;

import static mysocialinfo.mysocialinfo.helpers.ParameterStringBuilder.getQueryMap;

@Repository
public class SocialDataBL {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    private AuthenticationPrincipal authenticationPrincipal;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${facebook.accestoken}")
    private String accessTokenFacebookUrl;

    public SocialData FacebookLogin(ServletRequest request){
        try {
            if (!(request instanceof HttpServletRequest))
                return null;

            String code = getValueFromRequestParameter(request, "code");

            String token = getFacebookToken(code);

            this.saveTokenAndUserProfile(token, null, SocialNetwork.FACEBOOK);

            this.getProfileData(SocialNetwork.FACEBOOK);

            SocialData data = getFacebookData(token);

            return data;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String TwitterUrlToken() {
        try {
            return requestTwitterToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public SocialData LoginTwitter(ServletRequest request) {
        SocialData socialData = new SocialData();
        try {
            TwitterAuthModel twitterAuthModel = loginTwitter(request);

            this.saveTokenAndUserProfile(twitterAuthModel.getToken(), twitterAuthModel.getTokenSecret(), SocialNetwork.FACEBOOK);

            socialData = getTwitterData(twitterAuthModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        socialData.setSocialNetwork("Twitter");
        return socialData;
    }

    public SocialData YoutubeLogin(ServletRequest request){
        if (! (request instanceof HttpServletRequest))
            return null;

        String code = getValueFromRequestParameter(request, "code");

        String token = getYoutubeToken(code);

        this.saveTokenAndUserProfile(token, null, SocialNetwork.FACEBOOK);

        String chanelId = getYoutubeChannelId(token);

        SocialData socialData = getVideosList(chanelId);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        socialData.setSocialNetwork("Youtube");
        return socialData;
    }



    //region Facebook

    private String getFacebookToken(String code){
        try {
            URL url = new URL(accessTokenFacebookUrl + code);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");

            con.setUseCaches(false);
            con.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.close();
            // Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseBody = response.toString();
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SocialData getFacebookData(String token) {
        URL url;
        SocialData socialData = null;
        try {
            url = new URL("https://graph.facebook.com/v5.0/me?fields=posts");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + token);

            con.setUseCaches(false);
            con.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.close();
            // Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String data = response.toString();
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray posts = jsonObject.getJSONObject("posts").getJSONArray("data");
            System.out.println(posts);
            socialData = calculateData(posts, SocialNetwork.FACEBOOK);
            socialData.setSocialNetwork("Facebook");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return socialData;
    }

    //endregion

    //region Twitter

    private String requestTwitterToken() {
        String token = "";
        URL url;
        try
        {
            String consumer_key = "cAZvSJcPSJoJFBCylBgCcO3H4";
            String consumer_secret = "VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO";

            String authorization = new OAuth1AuthorizationHeaderBuilder()
                    .withMethod("POST")
                    .withURL("https://api.twitter.com/oauth/request_token")
                    .withConsumerSecret("VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO")
                    .withParameter("oauth_consumer_key", "cAZvSJcPSJoJFBCylBgCcO3H4")
                    .withParameter("oauth_nonce", "tp9pdk9frXwLOwt3")
                    .build();

            url = new URL("https://api.twitter.com/oauth/request_token");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty ("oauth_callback", "http%3A%2F%2Flocalhost%3A4200%2Fhome%2Ftwitter%2F");
            con.setRequestProperty ("Authorization", authorization);

            con.setUseCaches(false);
            con.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.close();
            // Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseBody = "{" + response.toString() + "}";
            responseBody = responseBody.replace('=', ':').replace('&',',');
            JSONObject jsonObject = new JSONObject(responseBody);

            System.out.println(responseBody);
            String oauthToken = jsonObject.getString("oauth_token");
            return oauthToken;
        }
        catch(Exception e)
        {

        }
        return token;
    }

    private TwitterAuthModel loginTwitter(ServletRequest request) {
        StringBuffer requestURL = ((HttpServletRequest) request).getRequestURL();
        String queryString = ((HttpServletRequest) request).getQueryString();
        Map parametri = getQueryMap(queryString);
        String code = (String)parametri.get("verifier");
        String token = (String)parametri.get("token");
        String basicAuth = "OAuth oauth_consumer_key=\"cAZvSJcPSJoJFBCylBgCcO3H4\",oauth_token=" + token + ",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1580930206\",oauth_nonce=\"JRckMrFATih\",oauth_version=\"1.0\",oauth_signature=\"zqXAccQY4BSW0EZnF29k9wS9Pcc%3D\"";
        URL url;

        try {
            url = new URL("https://api.twitter.com/oauth/access_token?oauth_verifier=" + code);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("oauth_verifier", code);
            con.setRequestProperty("Authorization", basicAuth);

            con.setUseCaches(false);
            con.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.close();
            // Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseBody = "{" + response.toString() + "}";
            responseBody = responseBody.replace('=', ':').replace('&', ',');
            JSONObject jsonObject = new JSONObject(responseBody);

            System.out.println(responseBody);
            String oauthToken = jsonObject.getString("oauth_token");
            String tokenSecret = jsonObject.getString("oauth_token_secret");
            TwitterAuthModel authModel = new TwitterAuthModel(oauthToken, tokenSecret);
            return authModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SocialData getTwitterData(TwitterAuthModel twitterAuthModel){
        SocialData socialData = new SocialData();
        URL url;
        String consumer_key = "cAZvSJcPSJoJFBCylBgCcO3H4";
        String consumer_secret = "VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO";

        String authorization = new OAuth1AuthorizationHeaderBuilder()
                .withMethod("GET")
                .withURL("https://api.twitter.com/1.1/statuses/user_timeline.json")
                .withConsumerSecret("VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO")
                .withParameter("oauth_consumer_key", "cAZvSJcPSJoJFBCylBgCcO3H4")
                .withParameter("oauth_nonce", "WmWVcny05Nb")
                .withParameter("oauth_token", twitterAuthModel.getToken())
                .withTokenSecret(twitterAuthModel.getTokenSecret())
                .build();
        try {
            HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json");
            request.addHeader("Authorization", authorization);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                // Get HttpResponse Status
                System.out.println(response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                Header headers = entity.getContentType();
                System.out.println(headers);

                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);

                    String data = response.toString();
                    JSONArray posts = new JSONArray(result);
                    System.out.println(posts);
                    socialData = calculateData(posts, SocialNetwork.TWITTER);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socialData;
    }

    //endregion

    //region Youtube

    private String getYoutubeToken(String code){
        try {
            URL url = new URL("https://oauth2.googleapis.com/token?client_id=911449969420-i76nfkg8ik3v0lf9i20vpvjikpvsoidm.apps.googleusercontent.com" +
                    "&redirect_uri=http://localhost:4200/home/youtube/" +
                    "&client_secret=dFEnQWPJMknyxNGv9ykWM5SE" +
                    "&grant_type=authorization_code" +
                    "&code=" + code);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setUseCaches(false);
            con.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.close();
            // Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseBody = response.toString();
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getYoutubeChannelId(String token){
        URL url;
        try {
            url = new URL("https://www.googleapis.com/youtube/v3/channels?access_token="+token+"&part=id&mine=true");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.22.0");
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Host", "www.googleapis.com");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Postman-Token", "7e67689b-5579-4d12-9de9-e09c0b170f08");

            con.setUseCaches(false);
            con.setDoOutput(true);

            // Send request
            con.connect();
            // Get Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String responseBody = response.toString();

            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getJSONArray("items").getJSONObject(0).getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SocialData getVideosList(String channelId){
        URL url;
        SocialData socialData = new SocialData();
        try {
            url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="+ channelId +"&order=date&type=video&key=AIzaSyBoJZcIoxh16GxFFJqIBqpFNTb6WCbNdFk");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.22.0");
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Host", "www.googleapis.com");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Postman-Token", "7e67689b-5579-4d12-9de9-e09c0b170f08");

            con.setUseCaches(false);
            con.setDoOutput(true);

            // Send request
            con.connect();
            // Get Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseBody = response.toString();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject pageInfo = jsonObject.getJSONObject("pageInfo");
            socialData = calculateData(jsonObject.getJSONArray("items"), SocialNetwork.YOUTUBE);
            int numberOfPosts = pageInfo.getInt("totalResults");
            socialData.setNumberOfPosts(numberOfPosts);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return socialData;
    }

    private void saveTokenAndUserProfile(String token, String tokenSecret, SocialNetwork socialNetwork){
        long userId = Long.valueOf(authenticationPrincipal.getAuthentication().getPrincipal().toString()).longValue();
        UserProfile profile = userProfileRepository.findByUserIdAndSocialNetworkId(userId, socialNetwork.ordinal());
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(userId);
            profile.setSocialNetworkId(socialNetwork.ordinal());
        }
        profile.setToken(token);
        if(tokenSecret != null){
            profile.setToken_secret(tokenSecret);
        }
        userProfileRepository.save(profile);
    }

    private UserProfile getProfileData(SocialNetwork socialNetwork) {
        URL url;
        UserProfile userProfile = new UserProfile();

        //Test
        Configuration config = new Configuration();
        config.setId(1);
        Parameter urlParameter = new Parameter();
        Type type = new Type(1, "url");
        urlParameter.setType(type);
        urlParameter.setValue("https://graph.facebook.com/v5.0/10218367531053241?fields=birthday,email,first_name,last_name");
        Type typeAttr = new Type (2, "attribute");
        Parameter birthdayParameter = new Parameter(2, typeAttr, "birthday", config.getId());
        Parameter emailParameter = new Parameter(2, typeAttr, "email", config.getId());
        Parameter first_name = new Parameter(2, typeAttr, "first_name", config.getId());
        Parameter last_name = new Parameter(2, typeAttr, "last_name", config.getId());
        LinkedList<Parameter> listaParams = new LinkedList<Parameter>();
        listaParams.add(urlParameter);
        listaParams.add(birthdayParameter);
        listaParams.add(emailParameter);
        listaParams.add(first_name);
        listaParams.add(last_name);
        config.setParameters(listaParams);

        try {
            String json = null;
            for (Parameter param : config.getParameters()) {
                if (param.getType().getName().equals("url")) {
                    json = userProfile(param.getValue(), socialNetwork);
                    break;
                }
            }
            if(json != null) {
                userProfile = new ObjectMapper().readValue(json, UserProfile.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userProfile;
    }

    private String userProfile(String configUrl, SocialNetwork socialNetwork) {
        try {
            long userId = Long.valueOf(authenticationPrincipal.getAuthentication().getPrincipal().toString()).longValue();
            UserProfile profile = userProfileRepository.findByUserIdAndSocialNetworkId(userId, socialNetwork.ordinal());

            URL url = new URL(configUrl);
            switch (socialNetwork){
                case FACEBOOK:
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    con.setRequestProperty("Content-Type", "application/json");

                    con.setRequestProperty("Authorization", "Bearer " + profile.getToken());

                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    // Send request
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.close();
                    // Get Response
                    InputStream is = con.getInputStream();
                    BufferedReader rd1 = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                    String line;
                    while ((line = rd1.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd1.close();
                    return response.toString();
                    //return new JSONObject(response.toString());
                case YOUTUBE:
                    HttpURLConnection con1 = (HttpURLConnection) url.openConnection();
                    con1.setRequestMethod("GET");

                    con1.setRequestProperty("Content-Type", "application/json");
                    con1.setRequestProperty("User-Agent", "PostmanRuntime/7.22.0");
                    con1.setRequestProperty("Accept", "*/*");
                    con1.setRequestProperty("Host", "www.googleapis.com");
                    con1.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
                    con1.setRequestProperty("Connection", "keep-alive");
                    con1.setRequestProperty("Cache-Control", "no-cache");
                    con1.setRequestProperty("Postman-Token", "7e67689b-5579-4d12-9de9-e09c0b170f08");

                    con1.setUseCaches(false);
                    con1.setDoOutput(true);

                    // Send request
                    con1.connect();
                    // Get Response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder responseYoutube = new StringBuilder(); // or StringBuffer if Java version 5+
                    String lineYoutube;
                    while ((lineYoutube = rd.readLine()) != null) {
                        responseYoutube.append(lineYoutube);
                        responseYoutube.append('\r');
                    }
                    rd.close();
                    String responseBody = responseYoutube.toString();
                    return responseBody;
                    //JSONObject jsonObject = new JSONObject(responseBody);
                    //return jsonObject;
                case TWITTER:
                    String consumer_key = "cAZvSJcPSJoJFBCylBgCcO3H4";
                    String consumer_secret = "VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO";

                    String authorization = new OAuth1AuthorizationHeaderBuilder()
                            .withMethod("GET")
                            .withURL("https://api.twitter.com/1.1/statuses/user_timeline.json")
                            .withConsumerSecret("VbHpDOo7L7qMdU5NaaK5LvyxCXcEGPrAzVrpUNtMaFNzfWZUHO")
                            .withParameter("oauth_consumer_key", "cAZvSJcPSJoJFBCylBgCcO3H4")
                            .withParameter("oauth_nonce", "WmWVcny05Nb")
                            .withParameter("oauth_token", profile.getToken())
                            .withTokenSecret(profile.getToken_secret())
                            .build();
                        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json");
                        request.addHeader("Authorization", authorization);
                        try (CloseableHttpResponse response2 = httpClient.execute(request)) {

                            // Get HttpResponse Status
                            System.out.println(response2.getStatusLine().toString());

                            HttpEntity entity = response2.getEntity();
                            Header headers = entity.getContentType();
                            System.out.println(headers);

                            if (entity != null) {
                                // return it as a String
                                String result = EntityUtils.toString(entity);
                                System.out.println(result);

                                String data = response2.toString();
                                return result;
                                //return new JSONObject(result);
                            }

                        }
                        catch (Exception e) {
                            throw e;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion Youtube

    //region PrivateMethods

    private String getValueFromRequestParameter(ServletRequest request, String parameter){
        String value = "";
        try
        {
            String queryString = ((HttpServletRequest) request).getQueryString();
            Map parameters = getQueryMap(queryString);
            value = (String)parameters.get(parameter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    private SocialData calculateData(JSONArray posts, SocialNetwork socialNetwork) {
        SocialData socialData = new SocialData();
        int numberOfPostsCurrentMonth = 0;
        int numberOfPostsLastMonth = 0;
        int numberOfPostsTwoMonthsAgo = 0;
        YearMonth currentMonth    = YearMonth.now();
        YearMonth lastMonth    = currentMonth.minusMonths(1);
        YearMonth twoMonthsAgo = currentMonth.minusMonths(2);
        String key = "";

        try {
            switch (socialNetwork){
                case FACEBOOK:
                    key = "created_time";
                    break;
                case TWITTER:
                    key = "created_at";
                    break;
                case YOUTUBE:
                    key = "publish_time";
                    break;
            }
            socialData.setNumberOfPosts(posts.length());
            LinkedList<String> list = new LinkedList<String>();
            for (int i=0; i<posts.length(); i++) {
                JSONObject object = posts.getJSONObject(i);
                LocalDate inputDate = LocalDate.parse(object.getString(key).substring(0, 10));
                if(inputDate.getMonth() == currentMonth.getMonth()){
                    ++numberOfPostsCurrentMonth;
                } else if(inputDate.getMonth() == lastMonth.getMonth()){
                    ++numberOfPostsLastMonth;
                } else if(inputDate.getMonth() == twoMonthsAgo.getMonth()){
                    ++numberOfPostsTwoMonthsAgo;
                };
            }
            socialData.setNumberOfPostsCurrentMonth(numberOfPostsCurrentMonth);
            socialData.setNumberOfPostsLastMonth(numberOfPostsLastMonth);
            socialData.setNumberOfPostsTwoMonthsAgo(numberOfPostsTwoMonthsAgo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return socialData;
    }

    //endregion PrivateMethods
}
