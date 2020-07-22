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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import com.youtubeplayer.controller.additional.NodeBuilder;
import static com.youtubeplayer.controller.additional.RoleMenu.HOME_PATH;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.util.Session;
import com.youtubeplayer.util.formatter.RegionCode;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class SettingController implements Initializable {

    private String closeTooltip;
    private NodeBuilder builder;
    private Channel userChannel;
    @FXML
    private Label lKids;
    @FXML
    private Label lRegion;
    public void setUserChannel(Channel userChannel){
        this.userChannel= userChannel;
    }
    
    @FXML
    private AnchorPane parent;
    @FXML
    private JFXButton bClose;
    @FXML
    private VBox userBox;
    @FXML
    private HBox buttonBox;
    @FXML
    private JFXToggleButton toggleKids;
    @FXML
    private Label lVersion;
    @FXML
    private JFXComboBox<String> cbRegion;
    @FXML
    private VBox mainBox;

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
        });
    }    
    private void initiation(){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                nodeInitiation(); 
                return null;
            }
        };
        task.setOnSucceeded((t) -> { 
            //properties
            settingProperties();
        });        
        task.setOnFailed((t) -> {
            //failed
        });
        new Thread(task).start();
    }
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        parent.setBackground(Background.EMPTY);
        builder = new NodeBuilder();
        //tooltip
        closeTooltip = "Exit setting";
    }
    
    /** 
     * Describe Menu Setting 
     *  <br>handle request: 
     *      youtubeKids, region, version
     */
    private void settingProperties(){
        Session session = new Session();
        userBox.getChildren().add(builder.userSetting(userChannel));
        
        toggleKids.selectedProperty().addListener((obs, oldVal, newVal) -> {
            String text = "YoutubeForKids";
            lKids.setText((newVal)? text+" ON!": text+" OFF!");
            session.setYoutubeKids(newVal);
        });
        toggleKids.setSelected(session.isYoutubeKids());
        
        cbRegion.setItems(new RegionCode().getAll());
        cbRegion.setOnAction((e) -> {
            String region = cbRegion.getSelectionModel().getSelectedItem();
            session.setRegion(region);
            lRegion.setText("Region "+new Locale("", region).getDisplayName());
        });
        cbRegion.valueProperty().addListener((obs, oldVal, newVal) -> {
            
        });
        cbRegion.getSelectionModel().select(session.getRegion());
        
        lVersion.setText(session.getInstalledVersion());
    }
    
    /**
     * Draggable properties
     *  <br>handle : movable app, maximize request
     */
    private void dragableProperties(){
        final Delta dragDelta = new Delta();
        Stage stage = (Stage) parent.getScene().getWindow();
        parent.setOnMousePressed((e) -> {
            stage.setOpacity(0.8);
            dragDelta.x = stage.getX() - e.getScreenX();
            dragDelta.y = stage.getY() - e.getScreenY();
            
        });
        parent.setOnMouseReleased((e) -> { stage.setOpacity(1); });
        parent.setOnMouseDragged((e) -> {
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
    }
    private static class Delta {
        double x, y;
    }
    
    /**
     * Window properties
     *  <br>handle : tooltips, close request
     */
    private void windowProperties(){
        //set tooltip nodes
        bClose.setTooltip(new Tooltip(closeTooltip));
        
        Stage stage = (Stage) parent.getScene().getWindow();
        bClose.setOnAction((e) -> { stage.close(); });
    }
}
