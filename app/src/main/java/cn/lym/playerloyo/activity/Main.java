package cn.lym.playerloyo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.lym.playerloyo.R;

/**
 * Created by lym on 2015/6/19.
 */
public class Main extends Activity implements View.OnClickListener{
    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.to_guide_activity_main);
        button2 = (Button) findViewById(R.id.to_current_play_activity_main);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_guide_activity_main:
                toActivity(Main.this,Guide.class);
                break;
            case R.id.to_current_play_activity_main:
                toActivity(Main.this,CurrentPlay.class);
                break;
            default:
                break;
        }
    }

    public void toActivity(Context context,Class s){
        Intent intent = new Intent(context,s);
        startActivity(intent);
        finish();
    }
}
