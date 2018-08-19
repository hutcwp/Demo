package com.hutcwp.livebiz.danmu;

import android.graphics.Color;

public class Barrage {

    private int MarginHeight = 20;
    private int textSize = 12;
    private int color = Color.WHITE;
    private String content = "msg";

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getMarginHeight() {
        return MarginHeight;
    }

    public void setMarginHeight(int marginHeight) {
        MarginHeight = marginHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
