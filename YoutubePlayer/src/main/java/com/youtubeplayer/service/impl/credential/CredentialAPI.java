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
    public Credential buildCredential(){
       return  null;
    }
}
