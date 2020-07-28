package com.app.tosstra.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tosstra.R;

public class RVnotificationAdapter extends RecyclerView.Adapter<RVnotificationAdapter.notificationAdapterHolder> {
    @NonNull
    @Override
    public notificationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new notificationAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class notificationAdapterHolder extends RecyclerView.ViewHolder {
        public notificationAdapterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
