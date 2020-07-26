package com.youtubeplayer.controller.swing.event;

import java.awt.Component;
import java.awt.event.MouseEvent;

public abstract interface ActionComponent {

    public abstract void execute(Component component, MouseEvent me);
}
