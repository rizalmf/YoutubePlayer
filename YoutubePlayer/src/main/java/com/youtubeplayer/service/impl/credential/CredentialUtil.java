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
package com.youtubeplayer.service.impl.credential;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;

/**
 *
 * @author rizal
 */
public class CredentialUtil {
     public static Credential getCredential(final NetHttpTransport httpTransport) throws IOException { 
        Credential credential;
        
        credential = new GoogleCredential.Builder()
                .setTransport(new NetHttpTransport())
                .setJsonFactory(new JacksonFactory())
                .setClientSecrets(
                        "805794483723-n78trb8krf83tcbe0tj91eobseusbg7m.apps.googleusercontent.com", 
                        "HsY0fqnsgwm-vwSzMM-j3O-A")
                .build();
        
        //not used?
        credential.setAccessToken("ya29.a0AfH6SMCLvO5XQFhJeyWGB5nqPbWvw2seikkyO_EQTePeLRvGuJVyAHkyIY-ZWhBd4X3nohoNhKFmG3XRNsGsiJgxmXrGjJ1srCOgv9kwV55YF8NxTTKJEpqF5dFw9waDY2ABR2je0PeQBln5XFIAmsIRGrQm2TrB6jE");
        credential.setRefreshToken("1//0gxDsorT-O0_BCgYIARAAGBASNwF-L9IrKvOHzRnZOdfojIQZaTtpQ3ByLY9GyN7cePhrb_b7YNkAEhtk4y5K9ZsHL09FLLPwA10");
        if(false){
            //request token
            credential = new AuthorizationCodeInstalledApp(
                CredentialAPI.getAuthorization(httpTransport), 
                new LocalServerReceiver()).authorize("user");
        }
        
        return credential;
    }
}
