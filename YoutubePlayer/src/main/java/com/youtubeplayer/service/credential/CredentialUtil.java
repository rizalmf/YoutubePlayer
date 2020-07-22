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
package com.youtubeplayer.service.credential;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.youtubeplayer.util.Session;
import java.io.IOException;

/**
 *
 * @author rizal
 */
public class CredentialUtil {
     public static Credential getCredential(final NetHttpTransport httpTransport) throws IOException { 
        Credential credential;
        Session session = new Session();
        if(session.isRefreshLogin()){
            //request token
            credential = new AuthorizationCodeInstalledApp(
                CredentialAPI.getAuthorization(httpTransport), 
                new LocalServerReceiver()).authorize("user");
            //save session
            session.saveCredentialToken(
                    credential.getAccessToken(), 
                    credential.getRefreshToken()
            );
            session.setRefreshLogin(false);
        }else {
            credential = CredentialAPI.getCredentialBuilder()
                .build();
            
            //get session
            String[] token = new Session().getCredentialToken();
            credential.setAccessToken(token[0]);
            credential.setRefreshToken(token[1]);
        }
        
        return credential;
    }
}
