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
package com.youtubeplayer.controller.child.menu;

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
import com.youtubeplayer.controller.child.menu.additional.LiveBuilder;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class LiveController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxChild;
    @FXML
    private ScrollPane spLive;
    
    private List<Video> liveList;
    private LiveBuilder builder;
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
            liveProperties();
        });
    }    
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new LiveBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
    }
    
    /** 
     * Describe Live Contents
     *  <br>handle request: 
     *      choose video
     */
    private void liveProperties(){
       //init contentbox
       initLive();
    }
    private void initLive(){
        new Thread(() -> {
                Response response = service.live();
                if(response.isStatus()){
                    liveList = (List<Video>) response.getData();
                    Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
                        spLive.setContent(null);
                        buildLive();
                    }));
                    tl.play();
                    
                }
            }).start();
    }
    private void buildLive(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spLive.setContent(builder.buildLive(liveList));
            });
        }));
        tl.setDelay(Duration.millis(600));
        tl.play();
    }
    
}
