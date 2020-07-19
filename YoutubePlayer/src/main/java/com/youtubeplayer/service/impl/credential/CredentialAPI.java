/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.youtubeplayer.service.impl.credential;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.youtubeplayer.util.Environment;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author rizal
 */
public class CredentialAPI {
    private static final Collection<String> SCOPES =
        Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    protected static GoogleAuthorizationCodeFlow getAuthorization(final NetHttpTransport httpTransport) throws IOException {
        Environment env = new Environment();
        return new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, 
                    JSON_FACTORY, 
                    env.get("OAUTH"), 
                    env.get("SECRET"),
                    SCOPES
            )
            .build();
    }
}
