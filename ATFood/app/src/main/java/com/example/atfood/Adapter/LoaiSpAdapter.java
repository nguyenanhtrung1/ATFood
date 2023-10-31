package com.example.atfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.ActivityUser.CuaHangActivity;
import com.example.atfood.InterFace.ItemClickListener;
import com.example.atfood.Model.LoaiSp;
import com.example.atfood.R;

import java.util.List;

public class LoaiSpAdapter extends RecyclerView.Adapter<LoaiSpAdapter.MyViewHolder> {
    Context context;
    List<LoaiSp> arrLoaiSp;

    public LoaiSpAdapter(Context context, List<LoaiSp> arrLoaiSp) {
        this.context = context;
        this.arrLoaiSp = arrLoaiSp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisp, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiSp loaiSp = arrLoaiSp.get(position);
        holder.txtLoaiSp.setText(loaiSp.getTenloaisanpham());
        Glide.with(context).load(loaiSp.getHinhanh()).into(holder.imgLoaiSp);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isClick) {
                Intent intent = new Intent(context, CuaHangActivity.class);
                intent.putExtra("idLoaiSp", loaiSp);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrLoaiSp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtLoaiSp;
        ImageView imgLoaiSp;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super((itemView));
            txtLoaiSp = itemView.findViewById(R.id.txt_LoaiSp);
            imgLoaiSp = itemView.findViewById(R.id.img_loaiSp);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
