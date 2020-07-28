package com.app.tosstra.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tosstra.R;
import com.app.tosstra.activities.ActiveJobDetail;
import com.app.tosstra.activities.JobDetailActivity;
import com.app.tosstra.interfaces.PassDriverIds;
import com.app.tosstra.models.AllDrivers;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewAdapterHolder> {
    AllDrivers data;
    Context context;
    String mob_number;
    PassDriverIds passDriverIds;
    public static List<String> interestList_LV = new ArrayList<>();

    public ListViewAdapter(Context context, AllDrivers data, PassDriverIds passDriverIds) {
        this.data = data;
        this.context = context;
        this.passDriverIds = passDriverIds;
    }

    @NonNull
    @Override
    public ListViewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapterHolder holder, int position) {
        mob_number = data.getData().get(position).getPhone();
        holder.tv_header1.setText(data.getData().get(position).getCompanyName());
        holder.tv_header2.setText(data.getData().get(position).getFirstName() + " " + data.getData().get(position).getFirstName());
    }

    @Override
    public int getItemCount() {
        return data.getData().size();
    }

    public class ListViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_header1;
        private TextView tv_header2;
        private ImageView iv_call;
        private String mob_number;
        private CheckBox lvCheckbox;
        String driver_id;
        private int pos;

        public ListViewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tv_header1 = itemView.findViewById(R.id.tv_header1);
            tv_header2 = itemView.findViewById(R.id.tv_header2);
            iv_call = itemView.findViewById(R.id.iv_call);
            iv_call.setOnClickListener(this);
            lvCheckbox = itemView.findViewById(R.id.lvCheckbox);
            lvCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    driver_id = data.getData().get(getAdapterPosition()).getJobId();
                    boolean checked = ((CheckBox) v).isChecked();
                    if (checked) {
                        interestList_LV.add(driver_id + ",");
                        passDriverIds.selectedDriverIdList(interestList_LV);
                    } else {
                        interestList_LV.remove(driver_id + ",");
                        passDriverIds.selectedDriverIdList(interestList_LV);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    Intent i = new Intent(context, ActiveJobDetail.class);
                    i.putExtra("job_id1", data.getData().get(pos).getJobId());
                    i.putExtra("dis_id1", data.getData().get(pos).getDispatcherId());
                    i.putExtra("position_my1", pos);
                    context.startActivity(i);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_call:
                    call();
                    break;
            }
        }


        private void call() {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("Do you want to Call ?");
            alertDialog.setIcon(R.mipmap.call_icon);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mob_number));
                        context.startActivity(callIntent);
                    }

                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
}
