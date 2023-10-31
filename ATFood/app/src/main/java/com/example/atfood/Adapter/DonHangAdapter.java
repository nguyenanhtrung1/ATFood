package com.example.atfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atfood.Model.DonHang;
import com.example.atfood.R;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    public RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> arrDonHang;

    public DonHangAdapter(Context context, List<DonHang> arrDonHang) {
        this.context = context;
        this.arrDonHang = arrDonHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = arrDonHang.get(position);
        holder.txtDonHang.setText("Mã Đơn Hàng : " + donHang.getMadonhang());
        holder.test.setText("Ngày đặt : "  + donHang.getNgaydathang());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recycleChiTietDH.getContext(),
                LinearLayoutManager.VERTICAL,
                false );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        ChiTietDonHangAdapter chiTietDhAdapter = new ChiTietDonHangAdapter(donHang.getItem(), context);
        holder.recycleChiTietDH.setLayoutManager(layoutManager);
        holder.recycleChiTietDH.setAdapter(chiTietDhAdapter);
        holder.recycleChiTietDH.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return arrDonHang.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView txtDonHang, test;
        RecyclerView recycleChiTietDH;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDonHang = itemView.findViewById(R.id.txtMaDonHang);
            recycleChiTietDH = itemView.findViewById(R.id.recycleViewChiTietDH);
            test = itemView.findViewById(R.id.test);
        }
    }
}