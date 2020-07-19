/*
 * Copyright 2020 rizal.
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

import java.util.HashMap;
import java.util.Map;
import com.youtubeplayer.Exception.Exceptions;
import java.io.FileReader;
import java.util.Properties;

/**
 *
 * @author rizal
 */
public class Environment {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private final String APPLICATION_NAME = "YoutubePlayer";
    private static Map<String, String> env;
    public String APP_NAME(){
        return APPLICATION_NAME;
    }
    public Environment(){
        if(env == null) initiateEnvironment();
    }
    private void initiateEnvironment(){
        try {
            env = new HashMap<>();
            FileReader reader=new FileReader("key.properties");  

            Properties p=new Properties();  
            p.load(reader);  
            
            //APP_NAME
            env.put("NAME", p.getProperty("NAME"));
            
            //API KEY
            env.put("KEY", p.getProperty("KEY"));
            //env.put("OAUTH_RESPONSE", p.getProperty("OAUTH_RESPONSE"));
            
            //credential
            env.put("OAUTH", p.getProperty("OAUTH"));
            env.put("SECRET", p.getProperty("SECRET"));
        } catch (Exception e) {
            env = null;
            exceptions.log(e);
        }
        
    }
    /**
     * env.put("KEY", "AIzaSyBBdq9M--jc1nWrxiseJAQ1nawFJ8fT2Lg");
        env.put("OAUTH_RESPONSE", "4/2AG5er-VAo2bHDpammhiJnvPAT64L2Z6VAHcHlXVd32f8hciIPfwApc");
        env.put("OAUTH", "805794483723-n78trb8krf83tcbe0tj91eobseusbg7m.apps.googleusercontent.com");
        env.put("SECRET", "HsY0fqnsgwm-vwSzMM-j3O-A");
     * @param key
     * @return 
     */
    
    public String get(String key){
        return env.get(key);
    }
}
