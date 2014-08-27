package jp.ac.it_college.std.s13006.androidchallenge;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class difficulty extends Fragment implements View.OnClickListener{
    public static int fallspeed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_difficulty, container, false);

        Button easy_button = (Button) rootView.findViewById(R.id.easy_Button);
        Button normal_button = (Button) rootView.findViewById(R.id.normal_Button);
        Button hard_button = (Button) rootView.findViewById(R.id.hard_Button);

        easy_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fallspeed = 20;
                difficultySelect("EASY");
            }
        });
        normal_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fallspeed = 10;
                difficultySelect("NORMAL");
            }
        });
        hard_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fallspeed = 5;
                difficultySelect("HARD");
            }
        });

        return rootView;
    }

    private void difficultySelect(String difficulty) {
        Intent intent = new Intent(getActivity(),TetrisActivity.class);
        intent.putExtra("Defficulty",difficulty);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {

    }

    public static difficulty newInstance(){
        difficulty fragment = new difficulty();
        return fragment;
    }
}
