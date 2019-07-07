package com.example.bahroel.sigapbencana.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.network.model.Notif;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
    private Context context;
    ArrayList<Notif> notifArrayList;

    public NotifAdapter(ArrayList<Notif> notifArrayList) {
        this.notifArrayList = notifArrayList;
    }

    public NotifAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Notif> getNotifArrayList() {
        return notifArrayList;
    }

    public void setNotifArrayList(ArrayList<Notif> notifArrayList){
        this.notifArrayList = notifArrayList;
    }


    @Override
    public NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        holder.tvStatus.setText(getNotifArrayList().get(position).getStatus());
        holder.tvTime.setText(getNotifArrayList().get(position).getTime());
        holder.tvDescription.setText(getNotifArrayList().get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return getNotifArrayList().size();
    }

    class NotifViewHolder extends RecyclerView.ViewHolder{
        ImageView ivStatus;
        TextView tvStatus;
        TextView tvTime;
        TextView tvDescription;

        public NotifViewHolder(View itemView) {
            super(itemView);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status_item_notification);
            tvStatus = (TextView)itemView.findViewById(R.id.tv_status_item_notification);
            tvTime = (TextView)itemView.findViewById(R.id.tv_time_item_notification);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_description_item_notification);
        }
    }
}
