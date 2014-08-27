package jp.ac.it_college.std.s13006.androidchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import jp.ac.it_college.std.s13006.androidchallenge.R;

public class resultActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TetrisActivity.mFlag.setFinishFlg(true);

        setContentView(R.layout.activity_result);
        TextView textSetting = (TextView)findViewById(R.id.score);
        textSetting.setText(Integer.toString(Board.score));
        findViewById(R.id.retry_button).setOnClickListener(this);
        findViewById(R.id.end_button).setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.retry_button:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.end_button:
                moveTaskToBack(true);
                break;
        }
        finish();
    }
}
