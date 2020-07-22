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
package com.youtubeplayer.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.youtubeplayer.util.Environment;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author rizal
 */
public class YoutubeUtil {
    public static YouTube initiateService() throws IOException, GeneralSecurityException{
        return YoutubeAPI.getYoutube(new Environment().APP_NAME());
    }
    
    public static String getKey(){
        return new Environment().get("KEY");
    }
    
    //Query params
    private static final String userChannelParam = "id,snippet";
    private static final String searchParam = "id,snippet";
    private static final String videosParam = "id,snippet,contentDetails,statistics";//takeout status
    private static final String channelsParam = "id,snippet,contentDetails";
    private static final String playlistsParam = "id,snippet";
    private static final String liveStreamParam = "";
    
    public static String getUserChannelParam(){
        return userChannelParam;
    }
    
    public static String getSearchParam(){
        return searchParam;
    }
    
    public static String getVideosParam(){
        return videosParam;
    }
    
    public static String getChannelsParam(){
        return channelsParam;
    }
    
    public static String getLiveStreamparam(){
        return liveStreamParam;
    }
    
    public static String getPlaylistsParam(){
        return playlistsParam;
    }
    
    //Fields
    private static final String userChannelField = 
            "items("
                + "id,"
                + "kind,"
                + "snippet/title,"
                + "snippet/thumbnails"
            + ")";
    private static final String searchField = 
            "items("
                + "id/kind,"
                + "id/videoId,"
                + "snippet/title,"
                + "snippet/channelTitle,"
                + "snippet/publishedAt"
            + ")";
    private static final String videosField = 
            "items("
                + "id,"
                + "kind,"
                + "snippet/title,"
                + "snippet/channelId,"
                + "snippet/channelTitle,"
                + "snippet/publishedAt,"
                + "contentDetails/duration,"
                + "statistics/viewCount,"
                //+ "status/madeForKids"
            + ")";
    private static final String channelsField = 
            "items("
                + "id,"
                + "kind,"
                + "snippet/title,"
                + "snippet/description,"
                + "snippet/channelId,"
                + "snippet/thumbnails,"
                + "contentDetails/totalItemCount"
            + ")";
    private static final String playlistsField = 
            "items("
                + "id,"
                + "kind,"
                + "snippet/title,"
                + "snippet/channelId,"
                + "snippet/thumbnails,"
            + ")";
    private static final String liveStreamField = "";
    
    public static String getUserChannelField(){
        return userChannelField;
    }
    
    public static String getSearchField(){
        return searchField;
    }
    
    public static String getVideosField(){
        return videosField;
    }
    
    public static String getChannelsField(){
        return channelsField;
    }
}
