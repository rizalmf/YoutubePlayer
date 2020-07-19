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
package com.youtubeplayer.service.impl;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.IOException;
import java.util.List;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;
import com.youtubeplayer.service.Service;
import com.youtubeplayer.service.impl.youtube.YoutubeUtil;
import com.youtubeplayer.util.Duration;
import com.youtubeplayer.util.Viewer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author rizal
 */
public class ServiceYoutube implements Service{
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private final Duration duration;
    private final Viewer viewer;

    private final long NUMBER_OF_VIDEOS_RETURNED = 54;
    private final YouTube youtube;
    private YouTube.Search.List searchs;
    private YouTube.Videos.List videos;
    private YouTube.Subscriptions.List channels;
    private YouTube.LiveStreams.List liveStreams;
    private YouTube.Playlists.List playlists;
    
    public ServiceYoutube() throws Exception{
        System.out.println("initiating youtube service");
        duration = new Duration();
        viewer = new Viewer();
        youtube = YoutubeUtil.initiateService();
        makeSnippet();
        initKey();
        makeFields();
    }
    
    private void initKey(){
        searchs.setKey(YoutubeUtil.getKey());
        videos.setKey(YoutubeUtil.getKey());
        channels.setKey(YoutubeUtil.getKey());
        liveStreams.setKey(YoutubeUtil.getKey());
        playlists.setKey(YoutubeUtil.getKey());
    }
    
    private void makeSnippet() throws IOException{
        searchs = youtube.search().list(YoutubeUtil.getSearchParam());
        videos = youtube.videos().list(YoutubeUtil.getVideosParam());
        channels = youtube.subscriptions().list(YoutubeUtil.getChannelsParam());
        liveStreams = youtube.liveStreams().list(YoutubeUtil.getLiveStreamparam());
        playlists = youtube.playlists().list(YoutubeUtil.getPlaylistsParam());
    }
    private void makeFields(){
        searchs.setType("video");
        searchs.setFields(
                "items(id/kind,id/videoId,"
                + "snippet/title,"
                + "snippet/channelTitle,"
                + "snippet/publishedAt)"
        );
        videos.setFields(
                "items("
                    + "id,"
                    + "kind,"
                    + "snippet/title,"
                    + "snippet/channelTitle,"
                    + "snippet/publishedAt,"
                    + "contentDetails/duration,"
                    + "statistics/viewCount"
                + ")"
        );
        channels.setFields(
                "items("
                    + "id,"
                    + "kind,"
                    + "snippet/title,"
                    + "snippet/description,"
                    + "snippet/channelId,"
                    + "snippet/thumbnails,"
                    + "contentDetails/totalItemCount"
                + ")"
        );
    }
    @Override
    public Response search(String query) {
        return new Response(true, null, "Data found!");
    }
    
    @Override
    public Response recomendedVideos() {
        Response response;
        try {
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            videos.setChart("mostPopular");//chartUnspecified
            videos.setRegionCode("ID");
            VideoListResponse listResponse = videos.execute();
            List<com.google.api.services.youtube.model.Video> resultList = listResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Video> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    com.google.api.services.youtube.model.Video singleVideo = iteratorSearchResults.next();
                    Video video = new Video();
                    video.setVideoID(singleVideo.getId());
                    video.setVideoTitle(singleVideo.getSnippet().getTitle());
                    video.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                    video.setPublishedAt(
                            duration.publishFormat(
                                singleVideo.getSnippet().getPublishedAt().getValue()
                            )
                    );
                    video.setDuration(
                            duration.format(
                                singleVideo.getContentDetails().getDuration())
                    );
                    video.setViews(
                            viewer.format(
                                singleVideo.getStatistics().getViewCount().longValue()
                            )
                    );
                    String thumbnailURL = "https://i.ytimg.com/vi/" + singleVideo.getId() + "/mqdefault.jpg";
                    video.setThumbnailURL(thumbnailURL);
                    list.add(video);
                }
            }
            response = new Response(true, list, "get data success");
        } catch (Exception e) {
            response = new Response(false, null, "get data failed");
            exceptions.log(e);
        }
        return response;
    }

    @Override
    public Response popularChannel() {
        Response response;
        try {
            channels.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            channels.setMine(true);
            SubscriptionListResponse listResponse = channels.execute();
            List<com.google.api.services.youtube.model.Subscription> resultList = listResponse.getItems();
            List<Channel> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Subscription> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    com.google.api.services.youtube.model.Subscription singleChannel = iteratorSearchResults.next();
                    Channel channel = new Channel();
                    channel.setVideoID(singleChannel.getId());
                    channel.setChannelTitle(singleChannel.getSnippet().getTitle());
                    channel.setDescription(singleChannel.getSnippet().getDescription());
                    channel.setChannelId(singleChannel.getSnippet().getChannelId());
                    channel.setTotalItemCount(singleChannel.getContentDetails().getTotalItemCount()+"");
                    channel.setThumbnailURL(singleChannel.getSnippet().getThumbnails().getDefault().getUrl());
                    list.add(channel);
                }
            }
            response = new Response(true, list, "get data success");
        } catch (Exception e) {
            response = new Response(false, null, "get data failed");
            exceptions.log(e);
        }
        return response;
    }

    @Override
    public Response videoMix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 

    @Override
    public Response trending() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Response subscription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Response live() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response history() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response queue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response likedVideos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
