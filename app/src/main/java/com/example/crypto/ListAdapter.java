package com.example.crypto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SharedMemory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.lang.ref.WeakReference;
import java.util.List;



import static android.content.Context.MODE_PRIVATE;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<Listdata> crypto_data;
    Context mContext;
    String goIntent;

    public ListAdapter(Context context, List crypto_data) {
        this.context = context;
        this.crypto_data = crypto_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(crypto_data.get(position));
        Listdata pu = crypto_data.get(position);

        if(pu.getBuy_sell().equals("sell")){
            Log.e("adapter_buysell" , pu.getBuy_sell());
            holder.buysell_adapter.setTextColor(Color.RED);
        }else if(pu.getBuy_sell().equals("buy")){
            holder.buysell_adapter.setTextColor(Color.GREEN);
        }

        holder.buysell_adapter.setText(pu.getBuy_sell());
        holder.time_adapter.setText(pu.getTimenow());
        holder.price_adapter.setText(pu.getPrice());
        holder.filled_adapter.setText(pu.getFilled());
        holder.fee_adapter.setText(pu.getFee());
        holder.total_adapter.setText(pu.getTotal());

    }

    @Override
    public int getItemCount() {
        return crypto_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView buysell_adapter,time_adapter,price_adapter,filled_adapter,fee_adapter,total_adapter;

        public ViewHolder(View itemView) {
            super(itemView);

            buysell_adapter = (TextView) itemView.findViewById(R.id.buy_sell_list);
            time_adapter = (TextView) itemView.findViewById(R.id.time_now_list);
            price_adapter = (TextView) itemView.findViewById(R.id.price_usdt_list);
            filled_adapter = (TextView) itemView.findViewById(R.id.filled_data_list);
            fee_adapter = (TextView) itemView.findViewById(R.id.fee_list);
            total_adapter = (TextView) itemView.findViewById(R.id.total_list);

        }
    }

}