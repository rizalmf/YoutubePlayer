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
package com.youtubeplayer.controller.child.playlist;

import static com.youtubeplayer.controller.MainController.service;
import com.youtubeplayer.controller.child.playlist.additional.PlaylistBuilder;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class PlaylistController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxChild;
    @FXML
    private ScrollPane spPlaylists;
    @FXML
    private Label lPlaylistInfo;
    
    private PlaylistBuilder builder;
    private List<Video> videoList;
    private String playlistId;
    public void setPlaylistId(String playlistId){
        this.playlistId =playlistId;
    }
    private String playlistName;
    public void setPlaylistName(String playlistName){
        this.playlistName =playlistName;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            //initiation
            nodeInitiation();   
            //properties
            playlistProperties();
        });
    }    
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new PlaylistBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
        lPlaylistInfo.setText("Playlist "+playlistName+"");
    }
    
    /** 
     * Describe Playlist Contents
     *  <br>handle request: 
     *      choose video
     */
    private void playlistProperties(){
       //init contentbox
       initPlaylist();
    }
    private void initPlaylist(){
        new Thread(() -> {
                Response response = service.playlist(playlistId);
                if(response.isStatus()){
                    videoList = (List<Video>) response.getData();
                    buildPlaylist();
                }
            }).start();
    }
    private void buildPlaylist(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spPlaylists.setContent(builder.buildPlaylist(videoList));
            });
        }));
        tl.setDelay(Duration.millis(300));
        tl.play();
    }
    
}
