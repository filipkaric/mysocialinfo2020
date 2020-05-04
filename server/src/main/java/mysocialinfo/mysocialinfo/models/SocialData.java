package mysocialinfo.mysocialinfo.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SocialData {

    private int numberOfPosts;

    private String socialNetwork;

    public SocialData(int numberOfPosts, String socialNetwork) {
        this.numberOfPosts = numberOfPosts;
        this.socialNetwork = socialNetwork;
    }

    public SocialData() {
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getSocialNetwork() { return socialNetwork; }

    public void setSocialNetwork(String socialNetwork) { this.socialNetwork = socialNetwork; }
}
