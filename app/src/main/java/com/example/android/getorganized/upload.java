package com.example.android.getorganized;

/**
 * Created by Nisarg on 3/15/2018.
 */

public class upload {

    String username,image_url;
    public  upload(){
    }
    public upload(String username, String image_url) {
        this.username = username;
        this.image_url = image_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
