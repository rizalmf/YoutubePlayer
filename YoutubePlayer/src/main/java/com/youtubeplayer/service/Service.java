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
package com.youtubeplayer.service;

import com.youtubeplayer.model.Response;

/**
 * Quota information update on 2020, 7 July 
 * @author rizal
 */
public interface Service {
        
    //START MENU CONTENT
    /**
     * get specified videos from search content
     * <p> Quota :
     * <br> search : 100,
     * otherSnippet : 5 per videos
     * @param query whatever word requested
     * @return Response
     */
    Response search(String query);
    
    /**
     * get recomended videos
     * <p> Quota : 9
     * <br>- snippet 3, <br>- contentDetails 2, <br>- statistics 2, <br>- status 2
     * @return Response
     */
    Response recomendedVideos();
    
    /**
     * get subscription channel
     * <p> Quota 5: 
     * <br>- snippet 3, <br>- contentDetails 2
     * @return Response
     */
    Response subscriptionChannel();
    
    /**
     * get video mix
     * <p> Quota :
     * <br> ?,
     * @return Response
     */
    Response videoMix();
    //END MENU CONTENT
    
    //START TRENDING CONTENT
    /**
     * get trending videos (chart=mostPopular)
     * <p> Quota : 9
     * <br>- snippet 3, <br>- contentDetails 2, <br>- statistics 2, <br>- status 2
     * @return Response
     */
    Response trending();
    //END TRENDING CONTENT
    
    //START SUBSCRIPTION CONTENT
    /**
     * get latest uploads from subscription.
     * <br>(login needs)
     * <p> Quota : 9
     * <br>- snippet 3, <br>- contentDetails 2, <br>- statistics 2, <br>- status 2
     * @return Response
     */
    Response subscription();
    //END SUBSCRIPTION CONTENT
    
    //START LIVE CONTENT
    /**
     * get live broadcast videos
     * <p> Quota :
     * <br> search : 100,
     * otherSnippet : 5 per videos
     * @return Response
     */
    Response live();
    //END LIVE CONTENT
    
    //START HISTORY CONTENT
    /**
     * get histories
     * <p> Quota : ?
     * <br>
     * @return Response
     */
    Response history();
    //END HISTORY CONTENT
    
    //START QUEUE CONTENT
    /**
     * get queue videos
     * <p> Quota : ?
     * <br>
     * @return Response
     */
    Response queue();
    //END QUEUE CONTENT
    
    //START LIKED CONTENT
    /**
     * get liked videos
     * <br>(login needs)
     * <p> Quota : ?
     * <br>
     * @return Response
     */
    Response likedVideos();
    //END LIKED CONTENT
}
