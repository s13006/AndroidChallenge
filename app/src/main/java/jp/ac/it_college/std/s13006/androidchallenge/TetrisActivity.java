package jp.ac.it_college.std.s13006.androidchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class TetrisActivity extends Activity{
    public static Flag mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = new Flag();
        setContentView(R.layout.activity_game);

        setContentView(new Board(this));

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    @Override
    public void onRestart(){
        super.onRestart();
        if(mFlag.getFinishFlg()){
            finish();
        }
    }
}
