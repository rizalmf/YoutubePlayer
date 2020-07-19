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
package com.youtubeplayer.controller.child.library;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import static com.youtubeplayer.controller.MainController.service;
import com.youtubeplayer.controller.child.library.additional.LikedBuilder;
import com.youtubeplayer.controller.child.library.additional.QueueBuilder;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class LikedController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxChild;
    @FXML
    private ScrollPane spLiked;
    
    private LikedBuilder builder;
    private List<Video> likedList;

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
            likedProperties();
        });
    }    
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new LikedBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
    }
    
    /** 
     * Describe Queue Contents
     *  <br>handle request: 
     *      choose video
     */
    private void likedProperties(){
       //init contentbox
       initLikedVideos();
    }
    private void initLikedVideos(){
        new Thread(() -> {
                Response response = service.likedVideos();
                if(response.isStatus()){
                    likedList = (List<Video>) response.getData();
                    buildLikedVideos();
                }
            }).start();
    }
    private void buildLikedVideos(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spLiked.setContent(builder.buildLikedVideos(likedList));
            });
        }));
        tl.setDelay(Duration.millis(300));
        tl.play();
    }
}
