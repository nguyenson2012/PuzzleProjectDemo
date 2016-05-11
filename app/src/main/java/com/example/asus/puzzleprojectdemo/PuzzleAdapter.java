package com.example.asus.puzzleprojectdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    //Constructor to initialize values
    public PuzzleAdapter(Context context, ArrayList<PuzzleCell> listPuzzleCell) {

        this.context        = context;
        this.listPuzzleCell     = listPuzzleCell;
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
        gridView.setBackgroundColor(listPuzzleCell.get(position).getColorCell());

        return gridView;
    }
}
