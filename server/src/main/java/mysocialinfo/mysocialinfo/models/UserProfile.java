package mysocialinfo.mysocialinfo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    private long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "socialData_id", referencedColumnName = "id")
    private SocialData socialData;

    private String first_name;
    private String last_name;
    private String email;
    private String location;
    private String birthday;
    private String screen_name;
    private String token;
    private String token_secret;
    private long socialNetworkId;

    public UserProfile() {
    }

    public UserProfile(long id, long userId, String first_name, String last_name, String email, String location, String birthday, String screen_name, String token, long socialNetworkId, String token_secret) {
        this.id = id;
        this.userId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.location = location;
        this.birthday = birthday;
        this.screen_name = screen_name;
        this.token = token;
        this.token_secret = token_secret;
        this.socialNetworkId = socialNetworkId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(long socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    public SocialData getSocialData() {
        return socialData;
    }

    public void setSocialData(SocialData socialData) {
        this.socialData = socialData;
    }
}
