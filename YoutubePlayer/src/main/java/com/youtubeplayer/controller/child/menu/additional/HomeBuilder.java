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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.YoutubePlayer;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Mixes;
import com.youtubeplayer.model.Video;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author rizal
 */
public class HomeBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private final HBox parent;
    
    public HomeBuilder(HBox parent) {
        this.parent = parent;
    }
    
    //START RECOMEND CONTENT
    /**
     * Build grid recommended content with 1 row
     * @param list
     * @return grid 1 row
     */
    public GridPane buildRecommendCollapse(List<Video> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(0);
        grid.setHgap(22);
        grid.setStyle("-fx-alignment: CENTER;");
        int col = 0;
        for (Video v : list) {
            grid.add(recommendButton(v), col++, 0);
        }
        return grid;
    }
    
    /**
     * Build grid recommended content with multiple cols
     * @param list
     * @return grid 
     */
    public GridPane buildRecommendExpand(List<Video> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(22);
        grid.setHgap(22);
        grid.setStyle("-fx-alignment: CENTER;");
        Stage stage =(Stage) parent.getScene().getWindow();
        int col = 0, row = 0, max_col = stage.isMaximized() ? 4 : 3;
        for (Video v : list) {
            grid.add(recommendButton(v), col, row);
            if (max_col <= col++) { col = 0; row++; }
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
    private JFXButton recommendButton(Video v){
        Image img = null;
        try {
            if (!v.getThumbnailURL().isEmpty()) {
                img =new Image(v.getThumbnailURL(), 175, 80, false, false, true);
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
        Label lDuration = new Label(v.getDuration().replaceAll(" ", ""));
        lDuration.setId("lDuration");
        HBox hb = new HBox(lDuration);
        hb.setStyle("-fx-alignment: BOTTOM_RIGHT;-fx-padding:5px");
        hb.setPrefSize(170, 75);
        StackPane stack = new StackPane(imageView, hb);

        Label lTitle = new Label(v.getVideoTitle());
        lTitle.setId("lTitle");
        lTitle.setPrefWidth(150);
        lTitle.setMaxHeight(30);
        lTitle.setPrefHeight(30);

        Label lChannel = new Label(v.getChannelTitle());
        lChannel.setId("lOther");
        lChannel.setPrefWidth(150);

        Label lDate = new Label(v.getPublishedAt());
        lDate.setId("lOther");
        //lDate.setPrefWidth(150);
        
        FontAwesomeIconView view = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
        view.setFill(Paint.valueOf("#00000066"));
        view.setSize("6");
        Label lView = new Label(v.getViews(), view);
        lView.setId("lOther");
        
        HBox boxBottom = new HBox(lDate, lView);
        boxBottom.setSpacing(5);
        
        VBox boxTxt = new VBox(lTitle, lChannel, boxBottom);
        boxTxt.setId("boxTxt");
        boxTxt.setSpacing(1);

        VBox box = new VBox(stack, boxTxt);
        box.setId("hbBody");
        box.setPadding(new Insets(0));
        JFXButton button = new JFXButton("", box);
        button.setId("buttonnode");
        button.setMinWidth(175);
        button.setMinHeight(155);
        button.setPrefSize(175, 155);
        button.setTooltip(new Tooltip(v.getVideoTitle()));
        Rectangle rect = new Rectangle(175, 155);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        button.setClip(rect);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    //END RECOMEND CONTENT
    
    //START SUBSCRIPTION CONTENT
    /**
     * Build grid popularChannel content
     * @param list
     * @return grid multiple rows
     */
    public GridPane buildPopularChannel(List<Channel> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(-1);
        grid.setPrefWidth(350);
        grid.setStyle("-fx-alignment: CENTER;");
        int row = 0;
        for (Channel c : list) {
            grid.add(channelBox(c), 0, row++);
        }
        return grid;
    } 
    private Image defaultThumbnailChannel(){
        return new Image(
                    getClass().getClassLoader().getResourceAsStream(
                            "images/human-min.png"), 
                            24, 
                            24, 
                            false, 
                            false
            );
    }
    private HBox channelBox(Channel c){
        Image img = null;
        try {
            if (!c.getThumbnailURL().isEmpty()) {
                img =new Image(c.getThumbnailURL(), 24, 24, false, false, true);
            }else img =defaultThumbnailChannel();
        } catch (Exception e) {
            img =defaultThumbnailChannel();
            exceptions.log(e);
        }
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.prefWidth(24);
        imageView.prefHeight(24);
        imageView.setFitHeight(24);
        Pane pane = new Pane(imageView);
        pane.setPrefSize(24, 24);
        pane.setMaxHeight(24);
        pane.setId("whitenode");
        pane.setClip(new Circle(12, 12, 12));

        Label lChannel = new Label(c.getChannelTitle());
        lChannel.setTooltip(new Tooltip(c.getDescription()));
        lChannel.setId("lWhite");
        lChannel.setMaxWidth(135);

        Label lVideos = new Label(c.getTotalItemCount()+" Videos");
        lVideos.setId("lDark");

        JFXButton button = new JFXButton("OPEN");
        button.setId("bSubscribe");
        HBox buttonBox = new HBox(button);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);
        buttonBox.setId("channelRight");
        
        HBox hbox = new HBox(pane, lChannel, lVideos, buttonBox);
        hbox.setPrefSize(310, 43);
        hbox.setMinHeight(43);
        hbox.setMaxHeight(43);
        hbox.setId("channelBody");
        return hbox;
    }
    //END SUBSCRIPTION CONTENT
    
    //START VIDEO MIX CONTENT
    /**
     * Build grid videomix content
     * @param list
     * @return grid multiple cols & rows
     */
    public GridPane buildVideoMix(List<Mixes> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setStyle("-fx-alignment: CENTER;");
        int col = 0, row = 0, max_row = 1;
        for (Mixes m : list) {
            grid.add(videoMixButton(m), col, row);
            if (max_row <= row++) { col++; row = 0; }
        }
        return grid;
    } 
    private Image defaultThumbnailMix(){
        return new Image(
                    getClass().getClassLoader().getResourceAsStream(
                            "images/default-min.png"), 
                            175, 
                            90, 
                            true, //preseve ratio enable
                            false
            );
    }
    private JFXButton videoMixButton(Mixes m){
        Image img = null;
        try {
            if (!m.getThumbnailURL().isEmpty()) {
                img =new Image(m.getThumbnailURL(), 175, 90, false, false, true);
            }else img =defaultThumbnailMix();
        } catch (Exception e) {
            img =defaultThumbnailMix();
            exceptions.log(e);
        }
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.prefWidth(175);
        imageView.prefHeight(90);
        //imageView.setFitHeight(90);
        //imageView.setFitWidth(175);
        
        Label lTitle = new Label(m.getVideoTitle());
        lTitle.setId("lMix");
        lTitle.setEffect(new DropShadow(6, Color.BLACK));
        
        HBox titleBox = new HBox(lTitle);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        icon.setFill(Color.WHITE);
        VBox.setVgrow(titleBox, Priority.ALWAYS);
        
        VBox leftBox = new VBox(titleBox, icon);
        leftBox.setId("mixBox");
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        
        Label lCount = new Label("play");
        lCount.setId("lGreyBox");
        HBox greyBox = new HBox(lCount);
        greyBox.setId("greyBox");
        greyBox.setMinWidth(65);
        greyBox.setMaxWidth(65);
        
        StackPane stack = new StackPane(imageView, new HBox(leftBox, greyBox));
        
        JFXButton button = new JFXButton("", stack);
        button.setId("bMix");
        button.setMinWidth(175);
        button.setMinHeight(90);
        button.setPrefSize(175, 90);
        button.setTooltip(new Tooltip(m.getVideoTitle()));
        Rectangle rect = new Rectangle(175, 90);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        button.setClip(rect);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    //END VIDEO MIX CONTENT
}
