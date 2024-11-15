package com.example.project.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
private RecyclerView mRecycleView;
private RecyclerView.Adapter mMatchesAdapter;
private RecyclerView.LayoutManager mMatchesLayoutManager;
private ImageButton mBack;
private DatabaseReference current;
private ValueEventListener listen;
private HashMap<String,Integer> mList=new HashMap<>();
private String currentUserId,mLastTimeStamp,mLastMessage,lastSeen;
DatabaseReference mCurrUserIdInsideMatchConnections,mCheckLastSeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        mBack=findViewById(R.id.matchesBack);
currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
mRecycleView=(RecyclerView) findViewById(R.id.recyclerView);
mRecycleView.setNestedScrollingEnabled(false);
mRecycleView.setHasFixedSize(true);
mMatchesLayoutManager=new LinearLayoutManager(MatchesActivity.this);
mRecycleView.setLayoutManager(mMatchesLayoutManager);
mMatchesAdapter=new MatchesAdapter(getDatSetMatches(),MatchesActivity.this);
mRecycleView.setAdapter(mMatchesAdapter);
mBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MatchesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }
});
getUserMatchId();
mLastMessage=mLastTimeStamp=lastSeen="";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    public void getLastMessageInfo(DatabaseReference userDb){
        mCurrUserIdInsideMatchConnections=userDb.child("connections").child("matches").child(currentUserId);
        mCurrUserIdInsideMatchConnections.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("lastMessage").getValue()!=null&& dataSnapshot.child("lastTimeStamp")
                            .getValue()!=null&& dataSnapshot.child("lastSend").getValue()!=null){
                        mLastMessage=dataSnapshot.child("lastMessage").getValue().toString();
                        mLastTimeStamp=dataSnapshot.child("lastTimeStamp").getValue().toString();
                        lastSeen=dataSnapshot.child("lastSend").getValue().toString();
                    }else{
                        mLastMessage="Start Chatting one";
                        mLastTimeStamp="  ";
                        lastSeen="true";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getUserMatchId(){
        Query sortedMatchesByLastTimeStamp= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId)
                .child("connections").child("matches").orderByChild("lastTimeStamp");
        sortedMatchesByLastTimeStamp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot match:dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey(),match.child("ChatId").toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void FetchMatchInformation(final String key,final String ChatId){
        DatabaseReference userDb=FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        getLastMessageInfo(userDb);
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userId=dataSnapshot.getKey();
                    String name="";
                    String profileImageUrl="";
                    String need="";
                    String give="";
                    String budget="";
                    final String lastMessage="";
                    String lastTimeStamp="";
                    if(dataSnapshot.child("name").getValue()!=null){
                        name=dataSnapshot.child("name").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                        profileImageUrl=dataSnapshot.child("profileImageUrl").getValue().toString();
                    }
                    if(dataSnapshot.child("need").getValue()!=null){
                        need=dataSnapshot.child("need").getValue().toString();
                    }
                    if(dataSnapshot.child("give").getValue()!=null){
                        give=dataSnapshot.child("give").getValue().toString();
                    }
                    if(dataSnapshot.child("budget").getValue().toString()!=null){
                        budget=dataSnapshot.child("budget").getValue().toString();
                    }
                    String milliSec=mLastTimeStamp;
                    Long now;
                    try{
                        now=Long.parseLong(milliSec);
                        lastTimeStamp=convertMilliToRelative(now);
                        String[] arrOfStr=lastTimeStamp.split(",");
                        mLastTimeStamp=arrOfStr[0];
                    }catch (Exception e) {
                        MatchesObject obj=new MatchesObject(userId,name,profileImageUrl,need,give,budget,mLastMessage
                        ,mLastTimeStamp,ChatId,lastMessage);
                        if(mList.containsKey(ChatId)){
                            int key=mList.get(ChatId);
                            resultsMatches.set(resultsMatches.size()-key,obj);
                        }
                        else{
        resultsMatches.add(0,obj);
        mList.put(ChatId,resultsMatches.size());

                        }
                        mMatchesAdapter.notifyDataSetChanged();
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String convertMilliToRelative(Long now) {
        String time= DateUtils.getRelativeDateTimeString(this,now,DateUtils.SECOND_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,DateUtils.FORMAT_ABBREV_ALL).toString();
        return time;
    }
    private ArrayList<MatchesObject> resultsMatches=new ArrayList<>();

    private List<MatchesObject> getDatSetMatches(){
        return resultsMatches;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }
}