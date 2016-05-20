package com.example.asus.puzzleprojectdemo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Asus on 5/9/2016.
 */
public class PuzzleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PuzzleCell> listPuzzleCell;
    private ArrayList<QuestionPuzzle> listQuestion;
    private Constant constant;
    private int[] positionCharacter;
    private int dem=0;
    private boolean firstDisplay=false;

    //Constructor to initialize values
    public PuzzleAdapter(Context context, ArrayList<PuzzleCell> listPuzzleCell,Boolean firstDisplay) {
        this.context        = context;
        this.listPuzzleCell     = listPuzzleCell;
        constant=Constant.getInstance();
        listQuestion=constant.listQuestion;
        positionCharacter=new int[100];
        this.firstDisplay=firstDisplay;
        for(QuestionPuzzle questionPuzzle:listQuestion) {
            int positionX=questionPuzzle.getPositionFirst().getX();
            int positionY=questionPuzzle.getPositionFirst().getY();
            if(questionPuzzle.isCross())
                for (int i = 0; i < questionPuzzle.getAnswer().length(); i++) {
                    positionCharacter[dem]=positionY*13+positionX+i;
                    dem++;
                }
            else
                for (int i = 0; i < questionPuzzle.getAnswer().length(); i++) {
                    positionCharacter[dem]=(positionY+i)*13+positionX;
                    dem++;
                }
        }

    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return listPuzzleCell.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {
        // LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            // get layout from grid_item.xml ( Defined Below )
            gridView = inflater.inflate( R.layout.grid_view_item , null);
        } else {
            gridView = (View) convertView;

        }
        ImageView buttonPuzzle=(ImageView)gridView.findViewById(R.id.grid_item_button);
        TextView tvPuzzle=(TextView)gridView.findViewById(R.id.textview_puzzle);
        tvPuzzle.setText(listPuzzleCell.get(position).getCharacterInCell());
        if(listPuzzleCell.get(position).getColorCell()== Color.BLACK)
            gridView.setAlpha(0.0f);
        else
            gridView.setBackgroundColor(listPuzzleCell.get(position).getColorCell());
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.gridview_animation);
        for(int i=0;i<dem;i++)
            if(position==positionCharacter[i]&&firstDisplay)
                gridView.startAnimation(animation);
        if(position==168)
            firstDisplay=false;
        return gridView;
    }
}
