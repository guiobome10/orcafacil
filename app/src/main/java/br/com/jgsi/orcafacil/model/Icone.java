package br.com.jgsi.orcafacil.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by guilherme.costa on 27/01/2016.
 */
public class Icone implements Serializable{

    private Bitmap bitmap;
    private int drawableID;

    public Icone(Bitmap bitmap, int drawableID) {
        this.bitmap = bitmap;
        this.drawableID = drawableID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

}
