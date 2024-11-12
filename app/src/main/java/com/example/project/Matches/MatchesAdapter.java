package com.example.project.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;

import java.util.List;

public class MatchesAdapter  extends RecyclerView.Adapter<MatchesViewHolders> {
    private List<MatchesObject> matchesObjectList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesObjectList, Context context) {
        this.matchesObjectList = matchesObjectList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolders rcv=new MatchesViewHolders(layoutView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolders holder, int position) {
    holder.mMatchId.setText(matchesObjectList.get(position).getUserId());
    holder.mBudget.setText(matchesObjectList.get(position).getBudget());
    holder.mGive.setText(matchesObjectList.get(position).getGive());
    holder.mProfile.setText(matchesObjectList.get(position).getProfileImageUrl());
    holder.mNeed.setText(matchesObjectList.get(position).getNeed());
    holder.mMatchName.setText(matchesObjectList.get(position).getName());
    holder.mLastMessage.setText(matchesObjectList.get(position).getLastMessage());
    String lastSeen="";
    lastSeen=matchesObjectList.get(position).getLastSeen();
    if(lastSeen.equals("true"))
        holder.mNotifitcationDot.setVisibility(View.VISIBLE);
    else
        holder.mNotifitcationDot.setVisibility(View.INVISIBLE);
    holder.mLastTimeStamp.setText(matchesObjectList.get(position).getLastTimeStamp());
    if(!matchesObjectList.get(position).getProfileImageUrl().equals("default")){
        Glide.with(context).load(matchesObjectList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
    }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
