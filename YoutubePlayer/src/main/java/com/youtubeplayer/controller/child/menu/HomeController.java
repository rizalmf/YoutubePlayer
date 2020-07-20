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

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import static com.youtubeplayer.controller.MainController.service;
import com.youtubeplayer.controller.child.menu.additional.HomeBuilder;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.model.Video;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class HomeController implements Initializable {

    @FXML
    private ScrollPane spRecomend;
    @FXML
    private ScrollPane spPopular;
    @FXML
    private ScrollPane spMixes;
    @FXML
    private VBox recomendBox;
    @FXML
    private HBox bottomBox;
    @FXML
    private VBox channelBox;
    @FXML
    private VBox mixesBox;
    @FXML
    private JFXButton bExpand;
    @FXML
    private VBox box;
    
    private HomeBuilder builder;
    private List<Video> recomendList;
    private List<Channel> subscriptionList;
    private List<Video> videomixList;

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
            recomendBoxProperties();
        });
    }
        
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new HomeBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
    }
    
    /** 
     * Describe Recomended Contents
     *  <br>handle request: 
     *      choose video, expand panel
     */
    private void recomendBoxProperties(){
        //scrollbar listener
        spRecomend.contentProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                spRecomend.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }else
                spRecomend.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        });
        //bExpand properties
        bExpand.setOnAction((e) -> {
            if(box.getChildren().size() > 1){//recomendbox only
                box.getChildren().remove(bottomBox);
                bExpand.setText("Collapse");
                initRecomendation(true);
            }else{
                box.getChildren().add(bottomBox);
                bExpand.setText("Expand");
                initRecomendation(false);
            }
        });
        
        //init contentbox
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                initRecomendation(false);
                return null;
            }
        };
        task.setOnSucceeded((t) -> {
            bottomBoxProperties();
        });
        new Thread(task).start();
    }
    
    private void initRecomendation(boolean expand){
        spRecomend.setContent(null);
        spRecomend.setHvalue(0);
        spRecomend.setVvalue(0);
        if(recomendList == null){
            Response response = service.recomendedVideos();
            if(response.isStatus()){
                recomendList = (List<Video>) response.getData();
                buildRecomendation(expand);
            }
        }else buildRecomendation(expand);
    }
    private void buildRecomendation(boolean expand){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                if(expand){
                    spRecomend.setContent(builder.buildRecommendExpand(recomendList));
                }else spRecomend.setContent(
                        builder.buildRecommendCollapse(
                                recomendList.subList(0, recomendList.size()/4)));
            });
        }));
        //give breath
        tl.setDelay(Duration.millis(500));
        tl.play();
    }
    
    /**
     * bottomBox parent
     *  <br>include:
     *  <br>- popular channel
     *  <br>- video mix
     */
    private void bottomBoxProperties(){
        //init contentbox
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                initSubscriptionChannel();
                return null;
            }
        };
        task.setOnSucceeded((t) -> {
            initVideoMix();
        });
        new Thread(task).start();
    }
    
    private void initSubscriptionChannel(){
        Response response = service.subscriptionChannel();
        if(response.isStatus()){
            subscriptionList = (List<Channel>) response.getData();
            buildPopularChannel();
        }
    }
    private void buildPopularChannel(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spPopular.setContent(builder.buildPopularChannel(subscriptionList));
            });
        }));
        tl.play();
    }
    
    private void initVideoMix(){
        Response response = service.videoMix();
        if(response.isStatus()){
            videomixList = (List<Video>) response.getData();
            buildVideoMix();
        }
    }
    private void buildVideoMix(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spMixes.setContent(builder.buildVideoMix(videomixList));
            });
        }));
        tl.play();
    }
    
}
