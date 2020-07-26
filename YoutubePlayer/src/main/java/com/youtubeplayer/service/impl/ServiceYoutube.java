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

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.io.IOException;
import java.util.List;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Mixes;
import com.youtubeplayer.model.MyPlaylist;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;
import com.youtubeplayer.service.Service;
import com.youtubeplayer.service.youtube.YoutubeUtil;
import com.youtubeplayer.util.Session;
import com.youtubeplayer.util.formatter.Duration;
import com.youtubeplayer.util.formatter.Viewer;
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
    private YouTube.Channels.List userChannels;
    private YouTube.Search.List search;
    private YouTube.Videos.List videos;
    private YouTube.Subscriptions.List channels;
    private YouTube.Playlists.List mixeslist;
    private YouTube.Playlists.List playlist;
    private YouTube.PlaylistItems.List playlistItems;
    private static ServiceYoutube service;
    
    public static ServiceYoutube getInstance() throws Exception{
        return new ServiceYoutube();
    }
    
    private ServiceYoutube() throws Exception{
        duration = new Duration();
        viewer = new Viewer();
        youtube = YoutubeUtil.initiateService();
        makeSnippet();
        initKey();
        makeFields();
    }
    
    @Override
    public Response user() {
        Channel channel= null;
        try {
            userChannels.setMine(true);
            ChannelListResponse listResponse = userChannels.execute();
            List<com.google.api.services.youtube.model.Channel> resultList = listResponse.getItems();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Channel> iteratorSearchResults = resultList.iterator();
                if (iteratorSearchResults.hasNext()) {
                    com.google.api.services.youtube.model.Channel singleChannel = iteratorSearchResults.next();
                    channel = new Channel();
                    channel.setVideoID(singleChannel.getId());
                    channel.setChannelTitle(singleChannel.getSnippet().getTitle());
                    channel.setThumbnailURL(singleChannel.getSnippet().getThumbnails().getDefault().getUrl());
                }
            }
        } catch (Exception e) {
            exceptions.log(e);
        }
        return (channel != null)? 
                new Response(true, channel, "get data success") 
                : new Response(false, null, "get data failed");
    }
        
    @Override
    public Response search(String query) {
        Response response;
        try {
            Session session = new Session();
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            search.setQ(query);
            search.setRegionCode(session.getRegion());
            search.setEventType("none");
            search.setOrder("relevance");
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (searchResultList != null) {
                Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    SearchResult singleVideo = iteratorSearchResults.next();
                    ResourceId rId = singleVideo.getId();
                    if (rId.getKind().equals("youtube#video")) { 
                        String id = rId.getVideoId();
                        Video video = new Video();
                        video.setVideoID(id);
                        video.setVideoTitle(singleVideo.getSnippet().getTitle());
                        video.setThumbnailURL("https://i.ytimg.com/vi/" + id + "/mqdefault.jpg");
                        video.setDescription(singleVideo.getSnippet().getDescription());
                        video.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                        video.setLiveBroadcastContent(singleVideo.getSnippet().getLiveBroadcastContent());
                        video.setPublishedAt(duration.publishFormat(singleVideo.getSnippet().getPublishedAt().getValue()));
                        video.setVideoURL("https://www.youtube.com/watch?v=" + id);
                        list.add(video);
                    }
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
    public Response recomendedVideos() {
        Response response;
        try {
            Session session = new Session();
            //boolean forKids = session.isYoutubeKids();
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            videos.setChart("mostPopular");
            videos.setMyRating(null);
            videos.setRegionCode(session.getRegion());
            VideoListResponse listResponse = videos.execute();
            List<com.google.api.services.youtube.model.Video> resultList = listResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Video> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    com.google.api.services.youtube.model.Video singleVideo = iteratorSearchResults.next();
                    //if(forKids && !(boolean)singleVideo.getStatus().get("madeForKids")){
                    //    continue;//cant find any.. not yet :D
                    //}
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
                    video.setVideoURL("https://www.youtube.com/watch?v=" + singleVideo.getId());
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
    public Response subscriptionChannel() {
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
        Response response;
        try {
            Session session = new Session();
            //boolean forKids = session.isYoutubeKids();
            //videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            videos.setChart(null);
            videos.setMyRating("like");
            videos.setRegionCode(session.getRegion());
            VideoListResponse videoResponse = videos.execute();
            List<com.google.api.services.youtube.model.Video> resultList = videoResponse.getItems();
            List<Mixes> mixList = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Video> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    com.google.api.services.youtube.model.Video singleVideo = iteratorSearchResults.next();
                    mixeslist.setChannelId(singleVideo.getSnippet().getChannelId());
                    PlaylistListResponse playListResponse = mixeslist.execute();
                    List<com.google.api.services.youtube.model.Playlist> lists = playListResponse.getItems();
                    if (lists != null) {
                        Iterator<com.google.api.services.youtube.model.Playlist> iteratorMixResults = lists.iterator();
                        if (iteratorMixResults.hasNext()) {
                            com.google.api.services.youtube.model.Playlist data = iteratorMixResults.next();
                            Mixes m = new Mixes();
                            m.setPlaylistId(data.getId());
                            m.setVideoTitle(data.getSnippet().getTitle());
                            m.setThumbnailURL(data.getSnippet().getThumbnails().getDefault().getUrl());
                            mixList.add(m);
                        }
                        if (mixList.size() >= 8) break;
                    }
                }
            }
            
            response = new Response(true, mixList, "get data success");
        } catch (Exception e) {
            response = new Response(false, null, "get data failed");
            exceptions.log(e);
        }
        return response;
    } 

    @Override
    public Response trending() {
        Response response;
        try {
            Session session = new Session();
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            //boolean forKids = session.isYoutubeKids();
            videos.setChart("mostPopular");
            videos.setMyRating(null);
            videos.setRegionCode(session.getRegion());
            VideoListResponse listResponse = videos.execute();
            List<com.google.api.services.youtube.model.Video> resultList = listResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Video> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    //if(forKids && !(boolean)singleVideo.getStatus().get("madeForKids")){
                    //    continue;//cant find any.. not yet :D
                    //}
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
                    video.setVideoURL("https://www.youtube.com/watch?v=" + singleVideo.getId());
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
    public Response subscription() {
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
    public Response live() {
        Response response;
        try {
            Session session = new Session();
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            search.setQ("");
            search.setEventType("live");
            search.setRegionCode(session.getRegion());
            search.setOrder("viewCount");
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (searchResultList != null) {
                Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    SearchResult singleVideo = iteratorSearchResults.next();
                    ResourceId rId = singleVideo.getId();
                    if (rId.getKind().equals("youtube#video")) { 
                        String id = rId.getVideoId();
                        Video video = new Video();
                        video.setVideoID(id);
                        video.setVideoTitle(singleVideo.getSnippet().getTitle());
                        video.setThumbnailURL("https://i.ytimg.com/vi/" + id + "/mqdefault.jpg");
                        video.setDescription(singleVideo.getSnippet().getDescription());
                        video.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                        video.setVideoURL("https://www.youtube.com/watch?v=" + id);
                        list.add(video);
                    }
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
    public Response history() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response queue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response likedVideos() {
        Response response;
        try {
            Session session = new Session();
            //boolean forKids = session.isYoutubeKids();
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            videos.setChart(null);
            videos.setMyRating("like");
            videos.setRegionCode(session.getRegion());
            VideoListResponse videoResponse = videos.execute();
            List<com.google.api.services.youtube.model.Video> resultList = videoResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.Video> iteratorSearchResults = resultList.iterator();
                while (iteratorSearchResults.hasNext()) {
                    //if(forKids && !(boolean)singleVideo.getStatus().get("madeForKids")){
                    //    continue;//cant find any.. not yet :D
                    //}
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
                    video.setVideoURL("https://www.youtube.com/watch?v=" + singleVideo.getId());
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
    public Response myList() {
        Response response;
        try {
            List<MyPlaylist> myPlaylists = new ArrayList<>();
            playlist.setMine(true);
            PlaylistListResponse playListResponse = playlist.execute();
            List<com.google.api.services.youtube.model.Playlist> lists = playListResponse.getItems();
            if (lists != null) {
                Iterator<com.google.api.services.youtube.model.Playlist> iteratorMixResults = lists.iterator();
                while (iteratorMixResults.hasNext()) {
                    com.google.api.services.youtube.model.Playlist data = iteratorMixResults.next();
                    MyPlaylist m = new MyPlaylist();
                    m.setPlaylistId(data.getId());
                    m.setPlaylistTiTle(data.getSnippet().getTitle());
                    myPlaylists.add(m);
                }
            }
            
            response = new Response(true, myPlaylists, "get data success");
        } catch (Exception e) {
            response = new Response(false, null, "get data failed");
            exceptions.log(e);
        }
        return response;
    }
    
    @Override
    public Response playlist(String playlistId) {
        Response response;
        try {
            //Session session = new Session();
            //boolean forKids = session.isYoutubeKids();
            playlistItems.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            playlistItems.setPlaylistId(playlistId);
            PlaylistItemListResponse listResponse = playlistItems.execute();
            List<com.google.api.services.youtube.model.PlaylistItem> resultList = listResponse.getItems();
            List<Video> list = new ArrayList<>();
            if (resultList != null) {
                Iterator<com.google.api.services.youtube.model.PlaylistItem> iteratorlistResults = resultList.iterator();
                while (iteratorlistResults.hasNext()) {
                    //if(forKids && !(boolean)singleVideo.getStatus().get("madeForKids")){
                    //    continue;//cant find any.. not yet :D
                    //}
                    com.google.api.services.youtube.model.PlaylistItem singleVideo = iteratorlistResults.next();
                    Video video = new Video();
                    video.setVideoID(singleVideo.getId());
                    video.setVideoURL("https://www.youtube.com/watch?v=" + singleVideo.getId());
                    video.setVideoTitle(singleVideo.getSnippet().getTitle());
                    video.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                    video.setThumbnailURL(singleVideo.getSnippet().getThumbnails().getDefault().getUrl());
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

    private void initKey(){
        search.setKey(YoutubeUtil.getKey());
        videos.setKey(YoutubeUtil.getKey());
        channels.setKey(YoutubeUtil.getKey());
        mixeslist.setKey(YoutubeUtil.getKey());
        playlist.setKey(YoutubeUtil.getKey());
        playlistItems.setKey(YoutubeUtil.getKey());
    }
    private void makeSnippet() throws IOException{
        userChannels = youtube.channels().list(YoutubeUtil.getUserChannelParam());
        search = youtube.search().list(YoutubeUtil.getSearchParam());
        videos = youtube.videos().list(YoutubeUtil.getVideosParam());
        channels = youtube.subscriptions().list(YoutubeUtil.getChannelsParam());
        mixeslist = youtube.playlists().list(YoutubeUtil.getPlaylistsParam());
        playlist = youtube.playlists().list(YoutubeUtil.getPlaylistsParam());
        playlistItems = youtube.playlistItems().list(YoutubeUtil.getPlaylistItemsParam());
    }
    private void makeFields(){
        userChannels.setFields(YoutubeUtil.getUserChannelField());
        search.setType("video");
        search.setFields(YoutubeUtil.getSearchField());
        videos.setFields(YoutubeUtil.getVideosField());
        channels.setFields(YoutubeUtil.getChannelsField());
        mixeslist.setFields(YoutubeUtil.getPlaylistsField());
        playlist.setFields(YoutubeUtil.getPlaylistsField());
        playlistItems.setFields(YoutubeUtil.getPlaylistItemsField());
    }

}
