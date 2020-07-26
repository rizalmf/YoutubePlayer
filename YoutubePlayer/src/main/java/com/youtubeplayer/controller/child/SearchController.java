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
package com.youtubeplayer.controller.child;

import static com.youtubeplayer.controller.MainController.service;
import com.youtubeplayer.controller.additional.SearchBuilder;
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
public class SearchController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxChild;
    @FXML
    private ScrollPane spSearch;
    @FXML
    private Label lResultInfo;

    private SearchBuilder builder;
    private List<Video> searchList;
    private String searchText;
    public void setSearchText(String searchText){
        this.searchText = searchText;
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
            liveProperties();
        });
    }  
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new SearchBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
        lResultInfo.setText("Search Result (keyword : "+searchText+")");
    }
    
    /** 
     * Describe Search Contents
     *  <br>handle request: 
     *      choose video
     */
    private void liveProperties(){
       //init contentbox
       initLive();
    }
    private void initLive(){
        new Thread(() -> {
                Response response = service.search(searchText);
                if(response.isStatus()){
                    searchList = (List<Video>) response.getData();
                    buildLive();
                }
            }).start();
    }
    private void buildLive(){
        Timeline tl = new Timeline(new KeyFrame(Duration.ONE, (e) -> {
            Platform.runLater(() -> {
                spSearch.setContent(builder.buildSearch(searchList));
            });
        }));
        tl.setDelay(Duration.millis(300));
        tl.play();
    }
}
