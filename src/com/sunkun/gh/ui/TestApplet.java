package com.sunkun.gh.ui;

import java.applet.Applet;
import java.awt.Graphics;

public class TestApplet extends Applet {

    private static final long serialVersionUID = 5511892956119084309L;

    @Override
    public void init() {
        Graphics g = this.getGraphics();
        paint(g);
    }

    public void paint(Graphics g) {
        g.drawString("Hello Applet!", 45, 45);
    }
}