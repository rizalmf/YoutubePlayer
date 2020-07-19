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
import com.youtubeplayer.controller.child.library.additional.HistoryBuilder;
import com.youtubeplayer.controller.child.library.additional.QueueBuilder;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class QueueController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxChild;
    @FXML
    private ScrollPane spQueue;
    
    private QueueBuilder builder;
    private List<Video> queueList;

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
            queueProperties();
        });
    }    
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new QueueBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
    }
    
    /** 
     * Describe Queue Contents
     *  <br>handle request: 
     *      choose video
     */
    private void queueProperties(){
       //init contentbox
       initQueue();
    }
    private void initQueue(){
        new Thread(() -> {
                Response response = service.queue();
                if(response.isStatus()){
                    queueList = (List<Video>) response.getData();
                    buildQueue();
                }
            }).start();
    }
    private void buildQueue(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spQueue.setContent(builder.buildQueue(queueList));
            });
        }));
        tl.setDelay(Duration.millis(300));
        tl.play();
    }
}
