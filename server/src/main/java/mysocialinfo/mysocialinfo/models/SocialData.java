package mysocialinfo.mysocialinfo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocialData<ctor> {
    @JsonProperty("numberOfPosts")
    private int numberOfPosts;

    public SocialData(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public SocialData() {
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
}
