package com.example.guest.discussionforum;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/5/16.
 */
@Parcel
public class Topic {
    String name;
//    String userComment;
    String userMessage;

    public Topic() {}

    public Topic(String name, String userMessage) {
        this.name = name;
//        this.userComment = userComment;
        this.userMessage = userMessage;
    }

    public String getName() {
        return name;
    }

//    public String getUserComment() {
//        return userComment;
//    }

    public String getUserMessage() {
        return userMessage;
    }
}
