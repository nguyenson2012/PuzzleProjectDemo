package com.example.asus.puzzleprojectdemo;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private PuzzleAdapter adapter;
    private Vector2 currentPosition;
    private QuestionPuzzle currentQuestion;
    private ArrayList<QuestionPuzzle> listQuestion;
    private ArrayList<PuzzleCell> puzzleCells=new ArrayList<PuzzleCell>();
    private ArrayList<Button> listKeyboard=new ArrayList<Button>();
    private int[] arrayButtonKeyboard={R.id.bt_answer_A,R.id.bt_answer_B,R.id.bt_answer_C,R.id.bt_answer_D,
            R.id.bt_answer_D,R.id.bt_answer_E,R.id.bt_answer_F,R.id.bt_answer_G,R.id.bt_answer_H,R.id.bt_answer_I,
            R.id.bt_answer_J,R.id.bt_answer_K,R.id.bt_answer_L,R.id.bt_answer_M,R.id.bt_answer_N,R.id.bt_answer_O,
            R.id.bt_answer_P,R.id.bt_answer_Q,R.id.bt_answer_R,R.id.bt_answer_S,R.id.bt_answer_T,R.id.bt_answer_U,
            R.id.bt_answer_V,R.id.bt_answer_W,R.id.bt_answer_X,R.id.bt_answer_Y,R.id.bt_answer_Z};
    private ImageButton btBackspace;
    private TextView tvQuestion;
    private Button btCheckAnswer;
    private Button btSolve;
    private boolean firstDisplayGridView=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultData();
        setKeyboard();
        // Get gridview object from xml file

        gridView = (GridView) findViewById(R.id.grid_puzzle);


        for(int i=0;i<169;i++){
            PuzzleCell puzzleCell=new PuzzleCell(Color.BLACK,"");
            puzzleCells.add(puzzleCell);
        }
        setInitialColor();

        adapter=new PuzzleAdapter(this,puzzleCells,firstDisplayGridView);
        firstDisplayGridView=false;
        // Set custom adapter (GridAdapter) to gridview

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int positionX=position%13;
                int positionY=position/13;
                currentPosition=new Vector2(positionX,positionY);
                changeColorOfItem(positionX,positionY);
//                adapter=new PuzzleAdapter(getApplicationContext(),puzzleCells);
//                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                gridView.invalidateViews();
            }
        });
        for(final Button button:listKeyboard){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fillCharacter(button.getText()+"");
                }
            });
            button.setOnTouchListener(new ChoiceTouchListener());
        }
        gridView.setOnDragListener(new ChoiceDragListener());
        btBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCharacter();
            }
        });
        btCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAnswer())
                    Toast.makeText(getApplicationContext(),"You win",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Please Try Again",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setInitialColor(){
        for(QuestionPuzzle question:listQuestion){
            int originalX=question.getPositionFirst().getX();
            int originalY=question.getPositionFirst().getY();
            int lengthAnswer=question.getAnswer().length();
            if(question.isCross()){
                for(int i=0;i<lengthAnswer;i++){
                    puzzleCells.get(13*originalY+originalX+i).setColorCell(Color.WHITE);
                }
            }else {
                for(int i=0;i<lengthAnswer;i++){
                    puzzleCells.get(13*(originalY+i)+originalX).setColorCell(Color.WHITE);
                }
            }
        }
    }

    private void deleteCharacter() {
        if(currentQuestion!=null){
            int positionX=currentPosition.getX();
            int positionY=currentPosition.getY();
            if(currentQuestion.isCross()){
                if(currentPosition.getX()>=currentQuestion.getPositionFirst().getX()) {
                    puzzleCells.get(positionX+positionY*13).setCharacterInCell("");
                    currentPosition.setX(positionX - 1);
                    adapter.notifyDataSetChanged();
                    gridView.invalidateViews();
                }
            }else {
                if(currentPosition.getY()>currentQuestion.getPositionFirst().getY()) {
                    puzzleCells.get(positionX+positionY*13).setCharacterInCell("");
                    currentPosition.setY(positionY - 1);
                    adapter.notifyDataSetChanged();
                    gridView.invalidateViews();
                }
            }
        }
    }

    private void fillCharacter(String character) {
        if(currentQuestion!=null){
            int positionX=currentPosition.getX();
            int positionY=currentPosition.getY();
            if(currentQuestion.isCross()){
                if(currentPosition.getX()<currentQuestion.getPositionFirst().getX()+currentQuestion.getAnswer().length()) {
                    currentPosition.setX(positionX + 1);
                    puzzleCells.get(positionX + positionY *13).setCharacterInCell(character);
                    adapter.notifyDataSetChanged();
                    gridView.invalidateViews();
                }
            }else {
                if(currentPosition.getY()<currentQuestion.getPositionFirst().getY()+currentQuestion.getAnswer().length()) {
                    currentPosition.setY(positionY + 1);
                    puzzleCells.get(positionX + positionY * 13).setCharacterInCell(character);
                    adapter.notifyDataSetChanged();
                    gridView.invalidateViews();
                }
            }
        }
    }

    private void setKeyboard() {
        for(int i=0;i<arrayButtonKeyboard.length;i++){
            Button button=(Button)findViewById(arrayButtonKeyboard[i]);
            listKeyboard.add(button);
        }
        btBackspace=(ImageButton)findViewById(R.id.bt_answer_back_space);
        btCheckAnswer=(Button)findViewById(R.id.bt_answer_check_answer);
        btSolve=(Button)findViewById(R.id.button_solve);
        tvQuestion=(TextView)findViewById(R.id.tv_question);
    }

    private void changeColorOfItem(int positionX, int positionY) {
        boolean isCellInCross=false;
        for(QuestionPuzzle questionPuzzle:listQuestion){
            if(questionPuzzle.isCross()){
                if(positionY==questionPuzzle.getPositionFirst().getY()) {
                    if(positionX>=questionPuzzle.getPositionFirst().getX()&&positionX<questionPuzzle.getPositionFirst().getX()+questionPuzzle.getAnswer().length()) {
                        setInitialColor();
                        currentQuestion = questionPuzzle;
                        updateQuestion();
                        isCellInCross = true;
                        for (int i = 0; i < questionPuzzle.getAnswer().length(); i++)
                            puzzleCells.get(questionPuzzle.getPositionFirst().getY() * 13 + questionPuzzle.getPositionFirst().getX() + i).setColorCell(Color.YELLOW);
                        puzzleCells.get(positionX + positionY * 13).setColorCell(Color.rgb(255, 153, 0));
                    }
                }
            }else
                continue;
        }
        if(!isCellInCross){
            for(QuestionPuzzle questionPuzzle:listQuestion){
                if(!questionPuzzle.isCross()){

                    if(positionX==questionPuzzle.getPositionFirst().getX()) {
                        if(positionY>=questionPuzzle.getPositionFirst().getY()&&positionY<questionPuzzle.getPositionFirst().getY()+questionPuzzle.getAnswer().length()) {
                            setInitialColor();
                            currentQuestion=questionPuzzle;
                            updateQuestion();
                            for (int i = 0; i < questionPuzzle.getAnswer().length(); i++)
                            puzzleCells.get((questionPuzzle.getPositionFirst().getY() + i) * 13 + questionPuzzle.getPositionFirst().getX()).setColorCell(Color.YELLOW);
                        puzzleCells.get(positionX + positionY * 13).setColorCell(Color.rgb(255, 153, 0));
                    }
                    }
                }else
                    continue;
            }
        }

    }

    private void updateQuestion() {
        tvQuestion.setText(currentQuestion.getQuestion()+"");
    }
    private boolean checkAnswer(){
        boolean checkAnswer=true;
        for(QuestionPuzzle question:listQuestion){
            String answer="";
            int firstX=question.getPositionFirst().getX();
            int firstY=question.getPositionFirst().getY();
            if(question.isCross()){
                for(int i=0;i<question.getAnswer().length();i++){
                    answer=answer.concat(puzzleCells.get(13*firstY+firstX+i).getCharacterInCell());
                }
                if(!answer.equals(question.getAnswer()))
                    checkAnswer=false;
            }else {
                for(int i=0;i<question.getAnswer().length();i++){
                    answer=answer.concat(puzzleCells.get(13*(firstY+i)+firstX).getCharacterInCell());
                }
                if(!answer.equals(question.getAnswer()))
                    checkAnswer=false;
            }

        }
        return checkAnswer;
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

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    int newPosition = gridView.pointToPosition(
                            (int) (dragEvent.getX()), (int) dragEvent.getY());
                    View view = (View) dragEvent.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.INVISIBLE);
                    //view being dragged and dropped
                    Button dropped = (Button) view;
                    if(newPosition!=GridView.INVALID_POSITION) {
                        puzzleCells.get(newPosition).setCharacterInCell(dropped.getText() + "");
                        adapter.notifyDataSetChanged();
                        gridView.invalidateViews();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
