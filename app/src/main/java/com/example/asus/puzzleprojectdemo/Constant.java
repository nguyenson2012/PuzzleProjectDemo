package com.example.asus.puzzleprojectdemo;

import java.util.ArrayList;

/**
 * Created by Asus on 5/20/2016.
 */
public class Constant {
    public static ArrayList<QuestionPuzzle> listQuestion;
    public static Constant instance;
    public static Constant getInstance(){
        if(instance==null){
            instance=new Constant();
        }
        return instance;
    }
    public Constant(){
        setDefaultData();
    }
    private void setDefaultData() {
        listQuestion=new ArrayList<QuestionPuzzle>();
        QuestionPuzzle read=new QuestionPuzzle(0,"READ","I____a book",new Vector2(9,2),true);
        listQuestion.add(read);
        QuestionPuzzle dance=new QuestionPuzzle(0,"DANCE","I can ______",new Vector2(5,5),true);
        listQuestion.add(dance);
        QuestionPuzzle listen=new QuestionPuzzle(0,"LISTEN","I____ to music",new Vector2(0,8),true);
        listQuestion.add(listen);
        QuestionPuzzle jump=new QuestionPuzzle(0,"JUMP","I can____",new Vector2(0,11),true);
        listQuestion.add(jump);
        QuestionPuzzle eat=new QuestionPuzzle(0,"EAT","___ a humberger",new Vector2(11,1),false);
        listQuestion.add(eat);

    }
}
