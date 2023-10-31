package com.example.atfood.Adapter.AdapterAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atfood.Model.ModelAdmin.ThongKeSanPham;
import com.example.atfood.R;

import java.util.List;

public class ThongKeSPAdapter extends RecyclerView.Adapter<ThongKeSPAdapter.Myviewholder> {
    Context context;
    List<ThongKeSanPham> arrThongKe;

    public ThongKeSPAdapter(Context context, List<ThongKeSanPham> arrThongKe) {
        this.context = context;
        this.arrThongKe = arrThongKe;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongkesanpham, parent,false);
        return new Myviewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
    ThongKeSanPham thongKeSanPham = arrThongKe.get(position);
        holder.txtMaSp.setText(String.valueOf(thongKeSanPham.getMasanpham()));
        holder.txtTenSp.setText(thongKeSanPham.getTensanpham());
        holder.txtSLBanSp.setText(String.valueOf(thongKeSanPham.getTong()));

    }

    @Override
    public int getItemCount() {
        return arrThongKe.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder  {
        TextView txtMaSp, txtTenSp, txtSLBanSp;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            txtMaSp = itemView.findViewById(R.id.txt_itemMaSanPham);
            txtTenSp = itemView.findViewById(R.id.txt_itemTenSanPham);
            txtSLBanSp = itemView.findViewById(R.id.txt_itemSoluongBan);
        }

    }
}

