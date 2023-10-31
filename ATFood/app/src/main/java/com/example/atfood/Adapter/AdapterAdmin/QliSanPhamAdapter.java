package com.example.atfood.Adapter.AdapterAdmin;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.ActivityAdmin.QuanLiSanPhamActivity;
import com.example.atfood.EvenBus.SetBadgeEven;
import com.example.atfood.EvenBus.SuaXoaEvent;
import com.example.atfood.EvenBus.SuaXoaEventSP;
import com.example.atfood.InterFace.ItemClickListener;
import com.example.atfood.Model.GioHang;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class QliSanPhamAdapter extends RecyclerView.Adapter<QliSanPhamAdapter.Myviewholder> {
    Context context;
    List<SanPham> arrSanPham;

    public QliSanPhamAdapter(Context context, List<SanPham> arrSanPham) {
        this.context = context;
        this.arrSanPham = arrSanPham;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlisanpham, parent,false);
        return new Myviewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        SanPham sanPham = arrSanPham.get(position);
        holder.txtTenSp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSp.setText("Giá : " + decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham())) + "Đ");
        holder.txtMoTaSp.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.img_itemSp);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isClick) {
                if(isClick == true){
                    EventBus.getDefault().postSticky(new SuaXoaEventSP(sanPham));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrSanPham.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        ImageView img_itemSp;
        TextView txtTenSp, txtMoTaSp, txtGiaSp;
        Button btnThemSp;
        private ItemClickListener itemClickListener;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            img_itemSp = itemView.findViewById(R.id.img_itemSanPham);
            txtTenSp = itemView.findViewById(R.id.txt_itemTenSp);
            txtMoTaSp = itemView.findViewById(R.id.txt_itemMoTaSp);
            txtGiaSp = itemView.findViewById(R.id.txt_itemGiaSp);
            btnThemSp = itemView.findViewById(R.id.btnThemSPTrongCH);
            itemView.setOnClickListener(this);
            btnThemSp = itemView.findViewById(R.id.btnThemSPTrongCH);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"Sửa");
            menu.add(0,1,getAdapterPosition(),"Xoá");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}

