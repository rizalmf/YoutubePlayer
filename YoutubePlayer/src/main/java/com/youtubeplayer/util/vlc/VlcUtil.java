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
package com.youtubeplayer.util.vlc;

import java.awt.Panel;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author rizal
 */
public class VlcUtil {
    private final String DUMP_NATIVE_MEMORY = "false";
    private static EmbeddedMediaPlayer mediaPlayer;
    private static MediaPlayerFactory factory;
    
    public EmbeddedMediaPlayer getMediaPlayer(){
        if (mediaPlayer == null) {
            initMediaPlayer();
        }
        return mediaPlayer;
    }
    
    private void initMediaPlayer(){
        System.setProperty("jna.dump_memory", DUMP_NATIVE_MEMORY);
        factory = new MediaPlayerFactory();
        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
        mediaPlayer.input().enableKeyInputHandling(false);
        mediaPlayer.input().enableMouseInputHandling(false);
    }
    
    public void registerCanvas(Panel canvas){
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
    }
}
