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
package com.youtubeplayer.util;

import static com.youtubeplayer.controller.additional.RoleMenu.VERSION;
import java.util.prefs.Preferences;

/**
 *
 * @author rizal
 */
public class Session {
    private Preferences p;
    
    //START CREDENTIAL SESSION 
    public void saveCredentialToken(String accessToken, String refreshToken){
        p = Preferences.userRoot().node("youtubeplayer");
        p.put("accessToken", accessToken);
        p.put("refreshToken", refreshToken);
    }
    public String[] getCredentialToken(){
        p = Preferences.userRoot().node("youtubeplayer");
        String[] token = new String[2];
        token[0] = p.get("accessToken", "");
        token[1] = p.get("refreshToken", "");
        return token;
    }
    //END CREDENTIAL SESSION START
    
    //START LOGIN SESSION
    public boolean isLogin(){
        p = Preferences.userRoot().node("youtubeplayer");
        return p.getBoolean("is_login", false);
    }
    public void setLogin(boolean is_login){
        p = Preferences.userRoot().node("youtubeplayer");
        p.putBoolean("is_login", is_login);
    }
    public boolean isRefreshLogin(){
        p = Preferences.userRoot().node("youtubeplayer");
        return p.getBoolean("refresh_login", false);
    }
    public void setRefreshLogin(boolean refresh_login){
        p = Preferences.userRoot().node("youtubeplayer");
        p.putBoolean("refresh_login", refresh_login);
    }
    //END LOGIN SESSION
    
    //START KIDS SESSION
    public void setYoutubeKids(boolean kids_safe){
        p = Preferences.userRoot().node("youtubeplayer");
        p.putBoolean("kids_safe", kids_safe);
    }
    public Boolean isYoutubeKids(){
        p = Preferences.userRoot().node("youtubeplayer");
        return p.getBoolean("kids_safe", false);
    }
    //END KIDS SESSION
    
    //START REGION SESSION
    public void setRegion(String region){
        p = Preferences.userRoot().node("youtubeplayer");
        p.put("region", region);
    }
    public String getRegion(){
        p = Preferences.userRoot().node("youtubeplayer");
        return p.get("region", "ID");
    }
    //END REGION SESSION
    
    //START VERSION SESSION
    public void setInstalledVersion(String version){
        p = Preferences.userRoot().node("youtubeplayer");
        p.put("version", version);
    }
    public String getInstalledVersion(){
        p = Preferences.userRoot().node("youtubeplayer");
        return p.get("version", VERSION);
    }
    //END VERSION SESSION
        
}
