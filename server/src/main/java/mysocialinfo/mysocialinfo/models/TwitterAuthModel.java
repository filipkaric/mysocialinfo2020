package mysocialinfo.mysocialinfo.models;

public class TwitterAuthModel {

    private String token;

    private String tokenSecret;

    public TwitterAuthModel() {
    }

    public TwitterAuthModel(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
