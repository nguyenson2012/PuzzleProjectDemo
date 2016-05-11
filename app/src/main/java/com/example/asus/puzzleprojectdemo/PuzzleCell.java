package com.example.asus.puzzleprojectdemo;

import android.graphics.Color;

/**
 * Created by Asus on 5/9/2016.
 */
public class PuzzleCell {
    private int colorCell;
    private String characterInCell;

    public PuzzleCell(int colorCell, String characterInCell) {
        this.colorCell = colorCell;
        this.characterInCell = characterInCell;
    }
    public PuzzleCell(){

    }

    public String getCharacterInCell() {
        return characterInCell;
    }

    public void setCharacterInCell(String characterInCell) {
        this.characterInCell = characterInCell;
    }

    public int getColorCell() {
        return colorCell;
    }

    public void setColorCell(int colorCell) {
        this.colorCell = colorCell;
    }
}
