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

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.youtubeplayer.service.credential.CredentialUtil;
import com.youtubeplayer.util.Session;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author rizal
 */
public class YoutubeAPI {
    protected static YouTube getYoutube(String AppName) throws IOException, GeneralSecurityException{
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(
                Auth.HTTP_TRANSPORT, 
                Auth.JSON_FACTORY, 
                (new Session().isLogin())? CredentialUtil.getCredential(httpTransport) : (HttpRequest) -> {})
                    .setApplicationName(AppName)
                    .build();
    }
}
