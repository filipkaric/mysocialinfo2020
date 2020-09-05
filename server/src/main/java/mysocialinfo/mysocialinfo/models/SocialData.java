package mysocialinfo.mysocialinfo.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class SocialData extends BaseEntity {

    @OneToOne(mappedBy = "socialData")
    private UserProfile userProfile;

    private String socialNetwork;
    private int numberOfPosts;
    private int numberOfPostsCurrentMonth;
    private int numberOfPostsLastMonth;
    private int numberOfPostsTwoMonthsAgo;
    private int numberOfFollowers;
    private int numberOfFollowersCurrentMoth;
    private int numberOfFollowersLastMonth;
    private int numberOfFollowersTwoMonthsAgo;
    private int numberOfLikes;
    private int numberOfViews;

    public SocialData(String socialNetwork, int numberOfPosts, int numberOfPostsCurrentMonth, int numberOfPostsLastMonth,
                      int numberOfPostsTwoMonthsAgo, int numberOfFollowers, int numberOfFollowersCurrentMoth,
                      int numberOfFollowersLastMonth, int numberOfFollowersTwoMonthsAgo, int numberOfLikes, int numberOfViews) {
        this.socialNetwork = socialNetwork;
        this.numberOfPosts = numberOfPosts;
        this.numberOfPostsCurrentMonth = numberOfPostsCurrentMonth;
        this.numberOfPostsLastMonth = numberOfPostsLastMonth;
        this.numberOfPostsTwoMonthsAgo = numberOfPostsTwoMonthsAgo;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowersCurrentMoth = numberOfFollowersCurrentMoth;
        this.numberOfFollowersLastMonth = numberOfFollowersLastMonth;
        this.numberOfFollowersTwoMonthsAgo = numberOfFollowersTwoMonthsAgo;
        this.numberOfLikes = numberOfLikes;
        this.numberOfViews = numberOfViews;
    }

    public SocialData() {
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public int getNumberOfPostsCurrentMonth() {
        return numberOfPostsCurrentMonth;
    }

    public void setNumberOfPostsCurrentMonth(int numberOfPostsCurrentMonth) {
        this.numberOfPostsCurrentMonth = numberOfPostsCurrentMonth;
    }

    public int getNumberOfPostsLastMonth() {
        return numberOfPostsLastMonth;
    }

    public void setNumberOfPostsLastMonth(int numberOfPostsLastMonth) {
        this.numberOfPostsLastMonth = numberOfPostsLastMonth;
    }

    public int getNumberOfPostsTwoMonthsAgo() {
        return numberOfPostsTwoMonthsAgo;
    }

    public void setNumberOfPostsTwoMonthsAgo(int numberOfPostsTwoMonthsAgo) {
        this.numberOfPostsTwoMonthsAgo = numberOfPostsTwoMonthsAgo;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getNumberOfFollowersCurrentMoth() {
        return numberOfFollowersCurrentMoth;
    }

    public void setNumberOfFollowersCurrentMoth(int numberOfFollowersCurrentMoth) {
        this.numberOfFollowersCurrentMoth = numberOfFollowersCurrentMoth;
    }

    public int getNumberOfFollowersLastMonth() {
        return numberOfFollowersLastMonth;
    }

    public void setNumberOfFollowersLastMonth(int numberOfFollowersLastMonth) {
        this.numberOfFollowersLastMonth = numberOfFollowersLastMonth;
    }

    public int getNumberOfFollowersTwoMonthsAgo() {
        return numberOfFollowersTwoMonthsAgo;
    }

    public void setNumberOfFollowersTwoMonthsAgo(int numberOfFollowersTwoMonthsAgo) {
        this.numberOfFollowersTwoMonthsAgo = numberOfFollowersTwoMonthsAgo;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }
}
