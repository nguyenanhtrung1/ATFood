package com.example.atfood.Adapter;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.Activity.ChiTietCuaHang;
import com.example.atfood.Activity.CuaHangActivity;
import com.example.atfood.InterFace.ItemClickListener;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;

import java.util.List;

public class CuaHangAdapter extends RecyclerView.Adapter<CuaHangAdapter.Myviewholder>{
    Context context;
    List<CuaHang> arrCuaHang;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public CuaHangAdapter(Context context, List<CuaHang> arrCuaHang) {
        this.context = context;
        this.arrCuaHang = arrCuaHang;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuahang, parent, false);
        return new Myviewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        CuaHang cuaHang = arrCuaHang.get(position);
        holder.txtTenCH.setText(cuaHang.getTencuahang());
        holder.txtDiaChiCH.setText(cuaHang.getDiachi());
        Glide.with(context).load(cuaHang.getHinhanh()).into(holder.imgCuaHang);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isClick) {
                Intent intent = new Intent(context, ChiTietCuaHang.class);
                intent.putExtra("cuahang", cuaHang);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCuaHang.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenCH, txtDiaChiCH;
        ImageView imgCuaHang;
        private ItemClickListener itemClickListener;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            txtTenCH = itemView.findViewById(R.id.txt_itemTenCH);
            txtDiaChiCH = itemView.findViewById(R.id.txt_itemDiaChiCH);
            imgCuaHang = itemView.findViewById(R.id.img_itemCuaHang);
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
