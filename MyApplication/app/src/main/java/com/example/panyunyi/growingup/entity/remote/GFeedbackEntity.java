package com.example.panyunyi.growingup.entity.remote;

/**
 * Created by panyunyi on 2017/8/8.
 * CUFE cs14
 */
public class GFeedbackEntity {
    private String feedbackContent;
    private String feedbackTitle;
    private String userId;
    private String feedbackTime;


    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}