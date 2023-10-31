package com.example.atfood.Adapter.AdapterAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atfood.Model.ModelAdmin.ThongKeDonHang;
import com.example.atfood.Model.ModelAdmin.ThongKeSanPham;
import com.example.atfood.R;

import java.util.List;

public class ThongKeDHAdapter extends RecyclerView.Adapter<ThongKeDHAdapter.Myviewholder> {
    Context context;
    List<ThongKeDonHang> arrThongKe;

    public ThongKeDHAdapter(Context context, List<ThongKeDonHang> arrThongKe) {
        this.context = context;
        this.arrThongKe = arrThongKe;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongkedonhang, parent,false);
        return new Myviewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        ThongKeDonHang thongKeDonHang = arrThongKe.get(position);
        holder.txtMaDH.setText(String.valueOf(thongKeDonHang.getMadonhang()));
        holder.txtSoLuong.setText(String.valueOf(thongKeDonHang.getSoluong()));
        holder.txtTongTien.setText(thongKeDonHang.getTongtien());
        holder.txtDiaChi.setText(thongKeDonHang.getDiachi());

    }

    @Override
    public int getItemCount() {
        return arrThongKe.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder  {
        TextView txtMaDH, txtSoLuong, txtTongTien,txtDiaChi;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            txtMaDH = itemView.findViewById(R.id.txt_itemTKeMaSanPham);
            txtSoLuong = itemView.findViewById(R.id.txt_itemTKeSoLuong);
            txtTongTien = itemView.findViewById(R.id.txt_itemTKeTongTien);
            txtDiaChi = itemView.findViewById(R.id.txt_itemTKeDiaChi);
        }

    }
}

