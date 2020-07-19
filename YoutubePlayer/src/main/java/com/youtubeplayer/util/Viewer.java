/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.youtubeplayer.util;

import java.math.BigInteger;

/**
 *
 * @author rizal
 */
public class Viewer {
    
    public String format(long v){
        String viewer="0";
        long big = v / 1000000;
        long small = v / 1000;
        
        if(big >= 1000){ viewer = ((int) (big/1000))+"B"; }
        else if(big >= 1){ 
            viewer = ((int) big)+"M"; 
        }else {
            viewer = ((int) small)+"K"; 
        }
        
        return viewer+" views";
    }
}
