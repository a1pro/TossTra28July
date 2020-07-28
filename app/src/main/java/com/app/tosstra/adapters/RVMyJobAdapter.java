package com.app.tosstra.adapters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tosstra.activities.AppUtil;
import com.app.tosstra.activities.JobDetailActivity;
import com.app.tosstra.R;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.models.GenricModel;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVMyJobAdapter extends RecyclerView.Adapter<RVMyJobAdapter.MyJobAdapterHolder> {
    FragmentActivity context;
    AllJobsToDriver data;
    int pos;


    public RVMyJobAdapter(FragmentActivity context, AllJobsToDriver data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyJobAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_job_item, parent, false);
        return new MyJobAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyJobAdapterHolder holder, int position) {

        holder.tv_header1.setText(data.getData().get(position).getFirstName() + " " + data.getData().get(position).getLastName());
        holder.tv_header2.setText(data.getData().get(position).getCompanyName());
        if (data.getData().get(position).getWorkStartStatus().equalsIgnoreCase("1")) {
            holder.bt_start.setText("Started");
        } else {
            holder.bt_start.setText("Start");
        }
    }

    @Override
    public int getItemCount() {
        if (data.getData() != null)
            return data.getData().size();
        else
            return 0;
    }

    public class MyJobAdapterHolder extends RecyclerView.ViewHolder {
        TextView tv_header1, tv_header2;
        Button bt_start;

        public MyJobAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tv_header1 = itemView.findViewById(R.id.tv_header1);
            tv_header2 = itemView.findViewById(R.id.tv_header2);
            bt_start = itemView.findViewById(R.id.bt_start);
            bt_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    hitStartJobAPI(pos);
                }
            });

        }
    }

    private void hitStartJobAPI(final int pos) {
        final Dialog dialog = AppUtil.showProgress(context);
        Interface service = CommonUtils.retroInit();
        Call<GenricModel> call = service.start_job(PreferenceHandler.readString(context, PreferenceHandler.USER_ID, ""),
                data.getData().get(this.pos).getJobId());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data_start = response.body();
                assert data_start != null;
                if (data_start.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    Intent i = new Intent(context, JobDetailActivity.class);
                    i.putExtra("our_job", data);
                    i.putExtra("position_my", pos);
                    context.startActivity(i);
                } else {
                    dialog.dismiss();
                    Intent i = new Intent(context, JobDetailActivity.class);
                    i.putExtra("our_job", data);
                    i.putExtra("position_my", pos);
                    context.startActivity(i);
                    dialog.dismiss();
                    // CommonUtils.showLongToast(context, data.getMessage() + ChildActieTruckAdapter.this.driver_id);
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(context, t.getMessage());
            }
        });
    }
}
