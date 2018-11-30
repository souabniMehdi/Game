package com.example.souabni.drawing_game;

/**
 * Created by Souabni on 14/11/2018.
 */
import android.graphics.Path;

public class FingerPath {

    public int color;
    public boolean emboss;
    public boolean blur;
    public int strokeWidth;
    public Path path;

    public FingerPath(int color, boolean emboss, boolean blur, int strokeWidth, Path path) {
        this.color = color;
        this.emboss = emboss;
        this.blur = blur;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
}