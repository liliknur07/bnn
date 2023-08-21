package com.example.bnn.kediri.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bnn.kediri.R;
import com.example.bnn.kediri.listener.AdapterListener;
import com.example.bnn.kediri.models.Abuse;
import com.example.bnn.kediri.models.Counseling;
import com.example.bnn.kediri.models.Rehab;
import com.example.bnn.kediri.models.SocializationP4GN;

import java.util.List;

public class DataAdapter<T> extends RecyclerView.Adapter<DataAdapter<T>.ViewHolder> {
    private Context context;
    private List<T> data;
    private AdapterListener<T> listener;
    private String type;

    public DataAdapter(Context context, String type, List<T> data, AdapterListener<T> listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
        this.type = type;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter<T>.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new DataAdapter<T>.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        if (position < data.size() - 1) {
            holder.rlRight.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
            holder.rlLeft.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        } else {
            Log.d("Else Condition", "iNDex "+position);
            holder.rlRight.setBackgroundResource(R.drawable.custom_bottom_right_radius);//ContextCompat.getDrawable(context, R.drawable.custom_bottom_right_radius)
            holder.rlLeft.setBackgroundResource(R.drawable.custom_bottom_left_radius);//ContextCompat.getDrawable(context, R.drawable.custom_bottom_left_radius)
        }

        if (type.equals("abuse")) {
            Abuse abuse = (Abuse) data.get(position);
            holder.tvData.setText(abuse.getDistrict());
            holder.tvStatus.setText(abuse.getStatus());
        } else if (type.equals("socialize")) {
            SocializationP4GN socialize = (SocializationP4GN) data.get(position);
            holder.tvData.setText(socialize.getCompany());
            holder.tvStatus.setText(socialize.getStatus());
        } else if (type.equals("rehab")) {
            Rehab rehab = (Rehab) data.get(position);
            holder.tvData.setText(rehab.getFullName());
            holder.tvStatus.setText(rehab.getStatus());
        } else if (type.equals("counseling")) {
            Counseling counseling = (Counseling) data.get(position);
            holder.tvData.setText(counseling.getFullName());
            holder.tvStatus.setText(counseling.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvData, tvStatus;
        RelativeLayout rlLeft, rlRight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data);
            tvStatus = itemView.findViewById(R.id.tv_status);
            rlLeft = itemView.findViewById(R.id.rl_left_radius);
            rlRight = itemView.findViewById(R.id.rl_right_radius);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemSelected(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
