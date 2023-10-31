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
import com.example.atfood.Model.User;
import com.example.atfood.R;

import java.util.List;

public class QliUserAdapter extends RecyclerView.Adapter<QliUserAdapter.Myviewholder> {
    Context context;
    List<User> arrUser;

    public QliUserAdapter(Context context, List<User> arrUser) {
        this.context = context;
        this.arrUser = arrUser;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanliuser, parent,false);
        return new Myviewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        User user = arrUser.get(position);
        holder.txtTaiKhoan.setText(user.getTaikhoan());
        holder.txtMatKhau.setText(user.getMatkhau());
        holder.txtVaiTro.setText(user.getVaitro());

    }

    @Override
    public int getItemCount() {
        return arrUser.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder  {
        TextView txtTaiKhoan, txtMatKhau, txtVaiTro,txtThayDoi;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            txtTaiKhoan = itemView.findViewById(R.id.txt_itemTaiKhoan);
            txtMatKhau = itemView.findViewById(R.id.txt_itemMatKhau);
            txtVaiTro = itemView.findViewById(R.id.txt_itemVaiTro);
            txtThayDoi = itemView.findViewById(R.id.txt_itemThayDoi);
        }

    }
}


