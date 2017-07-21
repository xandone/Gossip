package com.example.xandone.gossip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private GossipView gossipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gossipView = (GossipView) findViewById(R.id.gossip);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                gossipView.startRotate();
                break;
            case R.id.btn_stop:
                gossipView.stopRoate();
                break;
        }
    }
}
