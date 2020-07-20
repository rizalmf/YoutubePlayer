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
package com.youtubeplayer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.youtubeplayer.Exception.Exceptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.youtubeplayer.controller.additional.NodeBuilder;
import static com.youtubeplayer.controller.additional.RoleMenu.HISTORY_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.HOME_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.LIKED_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.LIVE_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.QUEUE_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.SUBSCRIPTION_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.TRENDING_PATH;
import com.youtubeplayer.service.Service;
import com.youtubeplayer.service.impl.ServiceTemp;
import com.youtubeplayer.service.impl.ServiceYoutube;
import javafx.concurrent.Task;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class MainController implements Initializable {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    
    @FXML
    private AnchorPane parent;
    @FXML
    private Button bLogo;
    @FXML
    private Button bHome;
    @FXML
    private Button bTrending;
    @FXML
    private Button bSubscription;
    @FXML
    private Button bLive;
    @FXML
    private Button bHistory;
    @FXML
    private Button bQueue;
    @FXML
    private Button bLiked;
    @FXML
    private Button bUpload;
    @FXML
    private Label lVersion;
    @FXML
    private HBox contentBox;
    @FXML
    private HBox windowBox;
    @FXML
    private JFXButton bMinimize;
    @FXML
    private JFXButton bClose;
    @FXML
    private VBox sideBar;
    @FXML
    private HBox mainBox;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private VBox contentParent;
    @FXML
    private JFXButton bGithub;
    
    private NodeBuilder builder;
    public static Service service;
    private boolean graphicOnly;
    private String closeTooltip;
    private String minimizeTooltip;
    private String githubTooltip;
    
    @FXML
    private HBox windowBoxChild;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            dragableProperties();
            windowProperties();
            
            //initiation
            initiation();
            //check available update
        });
    }   
    private void initiation(){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                serviceInitiation();
                return null;
            }
        };
        task.setOnSucceeded((t) -> { 
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.requestFocus();
            //node
            nodeInitiation(); 
            //properties
            sideBarProperties();
        });        
        task.setOnFailed((t) -> {
            //failed
        });
        new Thread(task).start();
    }
    /**
     * Initiate necessary service on start stage
     */
    private void serviceInitiation(){
        try {
            //service = ServiceTemp.getInstance();
            service = ServiceYoutube.getInstance();
        } catch (Exception e) {
            exceptions.log(e);
            //failed
        }
        
    }
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        parent.setBackground(Background.EMPTY);
        windowBoxChild.setVisible(false);
        builder = new NodeBuilder();
        graphicOnly = false;
        //open content home on first initiation
        builder.openContent(HOME_PATH, contentBox);
        
        //tooltip
        closeTooltip = "Exit";
        minimizeTooltip = "Minimize";
        githubTooltip = "Fork me on github";
    }
    
    /**
     * Sidebar parent
     */
    private void sideBarProperties(){
        parent.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            if(width < 800 && !graphicOnly){
                mainBox.getChildren().remove(sideBar);
                contentParent.setId("mainPane-alt");
                graphicOnly = true;
            }else if(width > 900 && graphicOnly){
                mainBox.getChildren().add(0, sideBar);
                contentParent.setId("mainPane");
                graphicOnly = false;
            }
        });    
        
        menuProperties();
        libraryProperties();
        playListProperties();  
        
        bUpload.setOnAction((e) -> {
            //login needs
        });
        
        //versioning
        Platform.runLater(() -> {
            lVersion.setText("Youtube App ");
        });
    }
    
    /** 
     * Describe Menu Sidebar 
     *  <br>handle request: 
     *      home, trending, subscription, live
     */
    private void menuProperties(){
        bHome.setOnAction((e) -> {
            builder.openContent(HOME_PATH, contentBox);
        });
        bTrending.setOnAction((e) -> {
            builder.openContent(TRENDING_PATH, contentBox);
        });
        bSubscription.setOnAction((e) -> {
            //login needs
            builder.openContent(SUBSCRIPTION_PATH, contentBox);
        });
        bLive.setOnAction((e) -> {
            builder.openContent(LIVE_PATH, contentBox);
        });
    }
    
    /** 
     * Describe Library Sidebar 
     *  <br>handle request: 
     *      history, queue, liked videos
     */
    private void libraryProperties(){
        bHistory.setOnAction((e) -> {
            builder.openContent(HISTORY_PATH, contentBox);
        });
        bQueue.setOnAction((e) -> {
            builder.openContent(QUEUE_PATH, contentBox);
        });
        bLiked.setOnAction((e) -> {
            //login needs
            builder.openContent(LIKED_PATH, contentBox);
        });
    }
    
    /** 
     * Describe Playlist Sidebar 
     *  <br>handle request: 
     *      any saved playlists
     */
    private void playListProperties(){
        
    }
    
    /**
     * Draggable properties
     *  <br>handle : movable app, maximize request
     */
    private void dragableProperties(){
        final Delta dragDelta = new Delta();
        Stage stage = (Stage) parent.getScene().getWindow();
        parent.setOnMousePressed((e) -> {
            switch(e.getClickCount()){
                case 2: stage.setMaximized(!stage.isMaximized());
                    break;
                default: 
                    stage.setOpacity(0.8);
                    dragDelta.x = stage.getX() - e.getScreenX();
                    dragDelta.y = stage.getY() - e.getScreenY();
                    break;
            }
            
        });
        parent.setOnMouseReleased((e) -> { stage.setOpacity(1); });
        parent.setOnMouseDragged((e) -> {
            if(stage.isMaximized()) stage.setMaximized(false);
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
    }
    private static class Delta {
        double x, y;
    }
    
    /**
     * Window properties
     *  <br>handle : tooltips, minimize & exit request
     */
    private void windowProperties(){
        //set tooltip nodes
        bClose.setTooltip(new Tooltip(closeTooltip));
        bMinimize.setTooltip(new Tooltip(minimizeTooltip));
        bGithub.setTooltip(new Tooltip(githubTooltip));
        
        Stage stage = (Stage) parent.getScene().getWindow();
        bClose.setOnAction((e) -> { System.exit(0); });
        bMinimize.setOnAction((e) -> { stage.setIconified(true); });
        windowBox.setOnMouseEntered((e) -> {
            windowBoxChild.setVisible(true);
        });
        windowBox.setOnMouseExited((e) -> {
            windowBoxChild.setVisible(false);
        });
        bGithub.setOnAction((e) -> { builder.github(); });
    }
}
