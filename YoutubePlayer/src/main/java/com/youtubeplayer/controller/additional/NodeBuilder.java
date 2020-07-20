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
package com.youtubeplayer.controller.additional;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.YoutubePlayer;
import static com.youtubeplayer.controller.additional.RoleMenu.PROJECT_URL;
import javafx.application.Application;

/**
 *
 * @author rizal
 */
public class NodeBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    
    /**
     * getContent based on menu path
     * @param load menu path
     * @param row destination node
     */
    public void openContent(String load, HBox row){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent content = fxmlLoader.load(getClass().getClassLoader().getResourceAsStream(load));
            content.getStylesheets()
                    .add(getClass().getClassLoader().getResource("css/main.css")
                    .toExternalFo‌rm());
            //row.setVisible(true);//node always true
            row.getChildren().clear();
            row.getChildren().add(content);  
        } catch (IOException e) {
            exceptions.log(e);
        }
    }
    
    /**
     * open YoutubePlayer project on github
     */
    public void github(){
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(URI.create(PROJECT_URL));
            } catch (IOException e) {
                exceptions.log(e);
            }
        }).start();
    }
    
    /**
     * (Unused)
     * initiateContent menu
     * @param load fxml path 
     * @return content
     * @throws Exception 
     */
    public Parent initiateContent(String load) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        return fxmlLoader.load(YoutubePlayer.class.getResourceAsStream(load));
    }
    
}
