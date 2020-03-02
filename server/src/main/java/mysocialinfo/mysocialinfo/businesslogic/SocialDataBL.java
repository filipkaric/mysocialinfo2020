package mysocialinfo.mysocialinfo.businesslogic;

import mysocialinfo.mysocialinfo.helpers.OAuth1AuthorizationHeaderBuilder;
import mysocialinfo.mysocialinfo.models.SocialData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static mysocialinfo.mysocialinfo.helpers.ParameterStringBuilder.getQueryMap;

@Repository
public class SocialDataBL {

    String facebookAccessToken;
    String twitterToken;

    public SocialData FacebookLogin(ServletRequest request){
        if (! (request instanceof HttpServletRequest))
            return null;

        String code = getValueFromRequestParameter(request, "code");

        String token = getFacebookToken(code);

        SocialData data = getFacebookData(token);

        return data;
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
            String token = loginTwitter(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socialData;
    }

    public SocialData YoutubeLogin(ServletRequest request){
        if (! (request instanceof HttpServletRequest))
            return null;

        String code = getValueFromRequestParameter(request, "code");

        String token = getYoutubeToken(code);

        String chanelId = getYoutubeChannelId(token);

        SocialData socialData = getVideosList(chanelId);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return socialData;
    }

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

    private SocialData calculateData(JSONArray posts){
        SocialData socialData = new SocialData();
        try
        {
            socialData.setNumberOfPosts(posts.length());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return socialData;
    }

    //region Facebook

    private String getFacebookToken(String code){
        try {
            URL url = new URL("https://graph.facebook.com/v5.0/oauth/access_token?client_id=490841858175046" +
                    "&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fhome%2Ffacebook%2F" +
                    "&client_secret=5065cf17fe5cab49d7a89e7354dc3630" +
                    "&code=" + code);
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
            socialData = calculateData(posts);
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

    private String loginTwitter(ServletRequest request) {
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
            return oauthToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
            url = new URL("https://www.googleapis.com/youtube/v3/channels?access_token="+token+"&part=id&mine=true&key=AIzaSyBoJZcIoxh16GxFFJqIBqpFNTb6WCbNdFk");
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
            url = new URL("https://www.googleapis.com/youtube/v3/search?key=AIzaSyBoJZcIoxh16GxFFJqIBqpFNTb6WCbNdFk&channelId=" + channelId +
                    "&part=snippet,id&order=date&maxResults=50");
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
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            socialData = calculateData(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return socialData;
    }

    //endregion Youtube

    //endregion PrivateMethods
}
