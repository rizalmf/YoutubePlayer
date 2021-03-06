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
package com.youtubeplayer.controller.child.menu.additional;

import com.jfoenix.controls.JFXButton;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.YoutubePlayer;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Video;

/**
 *
 * @author rizal
 */
public class SubscriptionBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private final HBox parent;
    
    public SubscriptionBuilder(HBox parent) {
        this.parent = parent;
    }
    
    //START SUBSCRIPTION CONTENT
    /**
     * Build grid Subscription content
     * @param list
     * @return content multiple rows
     */
    public GridPane buildSubscription(List<Channel> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(8);
        grid.setStyle("-fx-alignment: CENTER_LEFT;");
        int row = 0;
        for (Channel c : list) {
            grid.add(subscriptionButton(c), 0, row++);
        }
        return grid;
    }
    private Image defaultThumbnailVideo(){
        return new Image(
                    getClass().getClassLoader().getResourceAsStream(
                            "images/default-min.png"), 
                            175, 
                            80, 
                            true, //enable preseve ratio 
                            false
            );
    }
    private JFXButton subscriptionButton(Channel channel){
        Image img = null;
        try {
            if (!channel.getThumbnailURL().isEmpty()) {
                img =new Image(channel.getThumbnailURL(), 175, 80, false, false, true);
            }else img =defaultThumbnailVideo();
        } catch (Exception e) {
            img =defaultThumbnailVideo();
            exceptions.log(e);
        }
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.prefWidth(175);
        imageView.prefHeight(80);
        //imageView.setFitHeight(80);
        imageView.setFitWidth(175);
        StackPane stack = new StackPane(imageView);
        Rectangle rect = new Rectangle(175, 80);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        stack.setClip(rect);

        Label lTitle = new Label(channel.getChannelTitle());
        lTitle.setId("lTrendinTitle");
        //lTitle.setPrefWidth(150);
        lTitle.setMaxHeight(30);
        lTitle.setPrefHeight(30);

        Label lChannel = new Label(channel.getDescription().replaceAll("\n", " "));
        lChannel.setId("lTrendingOther");
        //lChannel.setPrefWidth(150);

        Label lDate = new Label(channel.getTotalItemCount()+" videos");
        lDate.setId("lTrendingOther");
        //lDate.setPrefWidth(150);

        VBox boxTxt = new VBox(lTitle, lChannel, lDate);
        boxTxt.setId("boxTxt");
        boxTxt.setSpacing(1);

        HBox box = new HBox(stack, boxTxt);
        box.setId("hbBody");
        box.setPadding(new Insets(0));
        JFXButton button = new JFXButton("", box);
        button.setId("trendingButton");
        button.setMinWidth(600);
        button.setMinHeight(90);
        button.setPrefSize(1920, 90);
        button.setTooltip(new Tooltip(channel.getDescription().replaceAll("\n", " ")));
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    //END SUBSCRIPTION CONTENT
}
