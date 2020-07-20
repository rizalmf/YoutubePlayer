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

import java.util.prefs.Preferences;

/**
 *
 * @author rizal
 */
public class Session {
    private Preferences p;
    
    private void saveCredentialToken(String accessToken, String refreshToken){
        p = Preferences.userRoot().node("youtubeplayer");
        p.put("accessToken", accessToken);
        p.put("refreshToken", refreshToken);
    }
    
    private String[] getCredentialToken(){
        p = Preferences.userRoot().node("youtubeplayer");
        String[] token = new String[2];
        token[0] = p.get("accessToken", "");
        token[1] = p.get("refreshToken", "");
        return token;
    }
}
