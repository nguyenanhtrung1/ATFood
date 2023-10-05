package com.example.atfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.Model.ItemDonHang;
import com.example.atfood.R;

import org.w3c.dom.Text;

import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.MyViewHolder> {
    List<ItemDonHang> arrItem;
    Context context;

    public ChiTietDonHangAdapter(List<ItemDonHang> arrItem, Context context) {
        this.arrItem = arrItem;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitietdonhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemDonHang item = arrItem.get(position);
        holder.txtTenSP.setText(item.getTensanpham() + "");
        holder.txtSoLuong.setText("Số lượng : " + item.getSoluong());
        Glide.with(context).load(item.getHinhanh()).into(holder.imgChiTietDH);
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChiTietDH;
        TextView txtTenSP, txtSoLuong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChiTietDH = itemView.findViewById(R.id.img_itemChiTietDH);
            txtTenSP = itemView.findViewById(R.id.txt_itemTenSPChiTietDH);
            txtSoLuong = itemView.findViewById(R.id.txt_itemSoLuongSPChiTietDH);
        }
    }
}