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

import com.jfoenix.controls.JFXButton;
import com.youtubeplayer.controller.additional.UploadBuilder;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class UploadController implements Initializable {

    @FXML
    private VBox box;
    @FXML
    private VBox boxUpload;
    @FXML
    private JFXButton bBrowse;

    private UploadBuilder builder;
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
            uploadProperties();
        });
    }    
    
    /**
     * Initiate necessary node on start stage
     */
    private void nodeInitiation(){
        builder = new UploadBuilder((HBox) box.getParent());
        HBox.setHgrow(box, Priority.ALWAYS);
        boxUpload.setOnDragOver((event) -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        boxUpload.setOnDragDropped((event) -> {
            List<File> files = event.getDragboard().getFiles();
            if (files.isEmpty()) return;
            File file = files.get(0);
            processFile(file);
        });
    }
    
    /** 
     * Describe Upload Contents
     *  <br>handle request: 
     *      upload video
     */
    private void uploadProperties(){
       bBrowse.setOnAction((e) -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Choose video file");
            List<String> ext = new ArrayList<>(
                    Arrays.asList(
                            "*.avi",
                            "*.flv", 
                            "*.mkv",
                            "*.mov", 
                            "*.mp4", 
                            "*.mpeg4", 
                            "*.webm", 
                            "*.wmv"
                    )
            );
            FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("Video format", ext);
            fc.getExtensionFilters().add(extFilter);
            Stage thisStage = (Stage) box.getScene().getWindow();
            File file = fc.showOpenDialog(thisStage);
            if (file != null) {
                //here
                processFile(file);
            }
       });
       
    }
    private void processFile(File file){
        //get metadata then uploading
    }
}
