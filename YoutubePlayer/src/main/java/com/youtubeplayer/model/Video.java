
package com.youtubeplayer.model;


import java.io.Serializable;
/**
 * follow this github. He is java swing expert!
 * @author https://github.com/DJ-Raven
 */
public class Video implements Serializable {

    public static final long serialVersionUID = 2020L;

    private String videoID;
    private String videoURL;
    private String videoTitle;
    private String thumbnailURL;
    private String description;
    private String duration;
    private String channelTitle;
    private String views;
    private String publishedAt;
    private String liveBroadcastContent;
    private boolean madeForKids;

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public boolean isMadeForKids() {
        return madeForKids;
    }

    public void setMadeForKids(boolean madeForKids) {
        this.madeForKids = madeForKids;
    }
    
    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
    
    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channalTitle) {
        this.channelTitle = channalTitle;
    }

    public Video() {
    }

    public Video(String videoID, String videoURL, String videoTitle, 
            String thumbnailURL, String description, String duration, 
            String channelTitle, String views, String publishedAt, 
            boolean madeForKids) {
        this.videoID = videoID;
        this.videoURL = videoURL;
        this.videoTitle = videoTitle;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.duration = duration;
        this.channelTitle = channelTitle;
        this.views = views;
        this.publishedAt = publishedAt;
        this.madeForKids = madeForKids;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    
    public Video copy() {
        return new Video(videoID, videoURL, videoTitle, 
                thumbnailURL, description, duration, channelTitle, 
                views, publishedAt, madeForKids);
    }

    @Override
    public String toString() {
        return "Video{" + "videoID=" + videoID + ", videoURL=" + videoURL 
                + ", videoTitle=" + videoTitle + ", thumbnailURL=" + thumbnailURL 
                + ", description=" + description + ", duration=" + duration 
                + ", channelTitle=" + channelTitle +", views=" + views 
                + ", publishedAt=" + publishedAt+", madeForKids=" + madeForKids +"}";
    }

}
