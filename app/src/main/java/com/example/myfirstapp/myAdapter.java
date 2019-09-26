package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myHolder> {


    ArrayList<String> menuLst;
    //OnItemClickListener listener;

    myAdapter(ArrayList<String> lst){

        menuLst=lst;
    }


    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        myHolder holder=new myHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        String name= menuLst.get(position);
        holder.myBtn.setText(name);

        final Button myBtn=holder.myBtn;
        final int p=position;

        myBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (p){
                        case 0:
                            Intent i1 = new Intent(myBtn.getContext(),DialActivityHw4.class);
                            myBtn.getContext().startActivity(i1);
                            break;
                        case 1:
                            Intent i2 = new Intent(myBtn.getContext(),ProfileActivityHw4.class);
                            myBtn.getContext().startActivity(i2);
                            break;
                        case 2:
                            Intent i3 = new Intent(myBtn.getContext(),IMDBSearchActivity.class);
                            myBtn.getContext().startActivity(i3);
                            break;
                        case 3:
                            Intent i4 = new Intent(myBtn.getContext(),IMDBOfflineList.class);
                            myBtn.getContext().startActivity(i4);
                            break;

                    }

                }
            });

    }

    @Override
    public int getItemCount() {
        int listSize=menuLst.size();
        //Log.d("adapter","List size is "+ listSize);
        return listSize;
    }

    class myHolder extends RecyclerView.ViewHolder{
        Button myBtn;


        public myHolder(@NonNull final View itemView) {
            super(itemView);
            myBtn = itemView.findViewById(R.id.btnMenuItem);
            final int position= getAdapterPosition();

           /*myBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            Intent i1 = new Intent(myBtn.getContext(),DialActivityHw4.class);
                            myBtn.getContext().startActivity(i1);
                            break;
                        case 1:
                            Intent i2 = new Intent(myBtn.getContext(),ProfileActivityHw4.class);
                            myBtn.getContext().startActivity(i2);
                            break;
                        case 2:
                            Intent i3 = new Intent(myBtn.getContext(),IMDBSearchActivity.class);
                            myBtn.getContext().startActivity(i3);
                            break;

                    }

                }
            });*/

        }




    }

    }


