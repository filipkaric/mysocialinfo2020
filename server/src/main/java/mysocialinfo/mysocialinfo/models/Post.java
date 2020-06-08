package mysocialinfo.mysocialinfo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Post {
    @JsonProperty("created_time")
    private Date createdTime;
    @JsonProperty("message")
    private String message;

    public Post(Date createdTime, String message) {
        this.createdTime = createdTime;
        this.message = message;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
