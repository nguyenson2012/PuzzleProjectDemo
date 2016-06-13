package com.example.asus.puzzleprojectdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
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
    ObjectAnimator scaleAnimationX=new ObjectAnimator();
    ObjectAnimator scaleAnimationY=new ObjectAnimator();
    ObjectAnimator scaleAnimationSmallerX=new ObjectAnimator();
    ObjectAnimator scaleAnimationSmallerY=new ObjectAnimator();

    //Constructor to initialize values
    public PuzzleAdapter(Context context, ArrayList<PuzzleCell> listPuzzleCell,Boolean firstDisplay) {
        this.context        = context;
        this.listPuzzleCell     = listPuzzleCell;
        settingAnimation();
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

    private void settingAnimation() {
        scaleAnimationX.setStartDelay(0);
        scaleAnimationX.setRepeatCount(0);
        scaleAnimationX.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimationX.setInterpolator(new LinearInterpolator());
        scaleAnimationY.setStartDelay(0);
        scaleAnimationY.setRepeatCount(0);
        scaleAnimationY.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimationY.setInterpolator(new LinearInterpolator());
    }
    private void startScaleAnimation(View view,int duration){
        scaleAnimationX=ObjectAnimator.ofFloat(view,"scaleX",new float[]{0.5f,1.5f}).setDuration(duration);
        scaleAnimationY=ObjectAnimator.ofFloat(view,"scaleY",new float[]{0.5f,1.5f}).setDuration(duration);
        final AnimatorSet animation = new AnimatorSet();
        ((AnimatorSet) animation).playTogether(scaleAnimationX,scaleAnimationY);
        animation.start();
        if(!animation.isRunning()) {
            scaleAnimationSmallerX = ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.5f, 1.0f}).setDuration(duration);
            scaleAnimationSmallerY = ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.5f, 1.0f}).setDuration(duration);
            final AnimatorSet animationSmall = new AnimatorSet();
            ((AnimatorSet) animationSmall).playTogether(scaleAnimationSmallerX, scaleAnimationSmallerY);
            animationSmall.start();
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

        View gridViewItem;

        if (convertView == null) {
            gridViewItem = new View(context);
            // get layout from grid_item.xml ( Defined Below )
            gridViewItem = inflater.inflate( R.layout.grid_view_item , null);
        } else {
            gridViewItem = (View) convertView;

        }
        ImageView buttonPuzzle=(ImageView)gridViewItem.findViewById(R.id.grid_item_button);
        TextView tvPuzzle=(TextView)gridViewItem.findViewById(R.id.textview_puzzle);
        tvPuzzle.setText(listPuzzleCell.get(position).getCharacterInCell());
        if(listPuzzleCell.get(position).getColorCell()== Color.BLACK)
            gridViewItem.setAlpha(0.0f);
        else
            gridViewItem.setBackgroundColor(listPuzzleCell.get(position).getColorCell());
        for(QuestionPuzzle question:listQuestion){
            int positionImage;
            if(question.isCross()) {
                positionImage = question.getPositionFirst().getX() + question.getPositionFirst().getY() * 13 + question.getAnswer().length();
            }else {
                positionImage = question.getPositionFirst().getX() + question.getPositionFirst().getY() * 13 + question.getAnswer().length()*13;
            }
            if(positionImage%13==0||positionImage>168){
                positionImage=question.getPositionFirst().getX() + question.getPositionFirst().getY() * 13-1;
            }
            if(position==positionImage){
                gridViewItem.setAlpha(1.0f);
                buttonPuzzle.setImageResource(R.drawable.action_word);
            }
            }
//        Animation animation= AnimationUtils.loadAnimation(context,R.anim.gridview_animation);
//        Animation animation2= AnimationUtils.loadAnimation(context,R.anim.gridview_animation2);
//        for(int i=0;i<dem;i++)
//            if(position==positionCharacter[i]&&firstDisplay) {
//                if(position%2==0)
//                    gridView.startAnimation(animation);
//                else
//                    gridView.startAnimation(animation2);
//            }
        for(int i=0;i<dem;i++)
            if(position==positionCharacter[i]&&firstDisplay) {
                int column=position%13;
//                Animation animationScale = AnimationUtils.loadAnimation(context, R.anim.scale);
//                gridViewItem.startAnimation(animationScale);
                //startScaleAnimation(gridView,1000+column*130);
            }
        if(position==168)
            firstDisplay=false;
        return gridViewItem;
    }
}
