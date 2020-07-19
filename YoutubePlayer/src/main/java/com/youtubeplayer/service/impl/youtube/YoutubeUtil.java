/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.youtubeplayer.service.impl.youtube;

import com.google.api.services.youtube.YouTube;
import com.youtubeplayer.util.Environment;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author rizal
 */
public class YoutubeUtil {
    private static final String searchParam = "id,snippet";
    private static final String videosParam = "id,snippet,contentDetails,statistics";
    private static final String channelsParam = "id,snippet,contentDetails";
    private static final String liveStreamParam = "";
    private static final String playlistsParam = "";
    
    public static YouTube initiateService() throws IOException, GeneralSecurityException{
        return YoutubeAPI.getYoutube(new Environment().APP_NAME());
    }
    
    public static String getKey(){
        return new Environment().get("KEY");
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
}
