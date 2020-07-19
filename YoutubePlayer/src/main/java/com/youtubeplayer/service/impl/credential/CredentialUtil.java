/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.youtubeplayer.service.impl.credential;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;

/**
 *
 * @author rizal
 */
public class CredentialUtil {
     public static Credential getCredential(final NetHttpTransport httpTransport) throws IOException {
        return new AuthorizationCodeInstalledApp(
                CredentialAPI.getAuthorization(httpTransport), 
                new LocalServerReceiver()).authorize("user");
    }
}
