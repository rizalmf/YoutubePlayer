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
package com.youtubeplayer.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;
import com.youtubeplayer.service.Service;
import com.youtubeplayer.util.formatter.Duration;

/**
 * Temporary data test
 * 
 * @author rizal
 */
public class ServiceTemp implements Service{

    private final Duration duration;
    private static ServiceTemp service;
    
    public static ServiceTemp getInstance(){
        if (service == null) {
            service = new ServiceTemp();
        }
        return service;
    }
    private ServiceTemp(){
        System.out.println("initiating temporary service");
        duration = new Duration();
    }
    private List<Video> createVideoList(int many){
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < many; i++) {
            list.add(new Video(
                    ""+i, 
                    ""+i, 
                    "title-"+i, 
                    "", 
                    "description-"+i, 
                    duration.format((i*360)+""), 
                    "channel-"+i, 
                    i+"M", 
                    i+" day ago",
                    true
            ));
        }
        return list;
    }

    @Override
    public Response user() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Response search(String query) {
        return new Response(true, createVideoList(52), "Data found!");
    }

    @Override
    public Response recomendedVideos() {
        return new Response(true, createVideoList(52), "Data found!");
    }
    
    @Override
    public Response subscriptionChannel() {
        List<Channel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Channel(
                    ""+i, 
                    ""+i, 
                    "", 
                    "description-"+i, 
                    "channel-"+i, 
                    "channelId"+i, 
                    duration.format((i*360)+"")
            ));
        }
        return new Response(true, list, "Data found!");
    }

    @Override
    public Response videoMix() {
        return new Response(true, createVideoList(8), "Data found!");
    }

    @Override
    public Response trending() {
        return new Response(true, createVideoList(32), "Data found!");
    }
    
    @Override
    public Response subscription() {
        return new Response(true, createVideoList(32), "Data found!");
    }

    @Override
    public Response live() {
        return new Response(true, createVideoList(32), "Data found!");
    }
    
    @Override
    public Response history() {
        return new Response(true, createVideoList(32), "Data found!");
    }

    @Override
    public Response queue() {
        return new Response(true, createVideoList(32), "Data found!");
    }

    @Override
    public Response likedVideos() {
        return new Response(true, createVideoList(52), "Data found!");
    }

}
