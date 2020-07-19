/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.youtubeplayer.service.impl.youtube;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.youtubeplayer.service.impl.credential.CredentialUtil;
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
                CredentialUtil.getCredential(httpTransport))
                    .setApplicationName(AppName)
                    .build();
    }
}
