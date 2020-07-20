/*
 * Copyright 2020 Java Programmer Indonesia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youtubeplayer.model;

/**
 *
 * @author rizal
 */
public class Channel extends Video{
    private String channelId;
    private String totalItemCount;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(String totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public Channel() {
    }

    public Channel(String videoID, String videoURL, String thumbnailURL, 
            String description, String channelTitle, String channelId, 
            String totalItemCount) {
        super.setVideoID(videoID);
        super.setVideoURL(videoURL);
        super.setThumbnailURL(thumbnailURL);
        super.setDescription(description);
        super.setChannelTitle(channelTitle);
        this.channelId = channelId;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public String toString() {
        return "Channel{" + "videoID=" + super.getVideoID() 
                + ", videoURL=" + super.getVideoURL()
                + ", thumbnailURL=" + super.getThumbnailURL()
                + ", description=" + super.getDescription() 
                + ", channelTitle=" + super.getChannelTitle() 
                + ", channelId=" + channelId 
                + ", totalItemCount=" + totalItemCount +"}";
    }
    
}
