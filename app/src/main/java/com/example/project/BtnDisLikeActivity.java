package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BtnDisLikeActivity extends AppCompatActivity {
private static final String TAG="BtnDisLikeActivity";
private static final int ACTIVITY_NUM=1;
private Context mContext=BtnDisLikeActivity.this;
private ImageView dislike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_dis_like);
setupTopNavigationView();
dislike=findViewById(R.id.dislike);
        Intent intent=getIntent();
        String profileUrl=intent.getStringExtra("url");
        switch (profileUrl){
            case "default":
                Glide.with(mContext).load(R.drawable.profile).into(dislike);
                break;
            default:
                Glide.with(mContext).load(profileUrl).into(dislike);
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
                Intent mainIntent=new Intent(BtnDisLikeActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        }).start();
    }

    private void setupTopNavigationView() {
        BottomNavigationViewEx tvEx=findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext,tvEx);
        Menu menu=tvEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}