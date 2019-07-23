package com.example.priyank.final_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

class MyHolder extends RecyclerView.ViewHolder {
        TextView vname, vcontact, vemail;
        ImageButton call;

        public MyHolder(View itemView) {
            super(itemView);

            vname = (TextView) itemView.findViewById(R.id.nme);
            vemail = (TextView) itemView.findViewById(R.id.mail);
            vcontact = (TextView) itemView.findViewById(R.id.con);
            call = (ImageButton) itemView.findViewById(R.id.phone);

        }
    }