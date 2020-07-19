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
package com.youtubeplayer.Exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * handle all exceptions
 * @author rizal
 */
public class Exceptions {
    private Logger log;
    private final Class T;
    
    public Exceptions(Class T) {
        this.T = T;
    }
    
    public void log(Exception ex){
        log = (log == null) ? Logger.getLogger(T.getName()) : log;
        log.log(
                Level.SEVERE 
                , ex.getMessage()
                , ex //uncomment this to read full logs
        );
    }
}
