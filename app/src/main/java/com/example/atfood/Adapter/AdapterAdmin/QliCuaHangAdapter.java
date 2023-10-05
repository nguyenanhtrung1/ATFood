package com.example.atfood.Adapter.AdapterAdmin;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.ActivityAdmin.QuanLiSanPhamActivity;
import com.example.atfood.ActivityUser.ChiTietCuaHangActivity;
import com.example.atfood.EvenBus.SuaXoaEvent;
import com.example.atfood.InterFace.ItemClickListener;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class QliCuaHangAdapter extends RecyclerView.Adapter<QliCuaHangAdapter.Myviewholder>{
    Context context;
    List<CuaHang> arrCuaHang;

    public QliCuaHangAdapter(Context context, List<CuaHang> arrCuaHang) {
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
                if(isClick == true){
                    EventBus.getDefault().postSticky(new SuaXoaEvent(cuaHang));
                }else {
                    Intent intent = new Intent(context, QuanLiSanPhamActivity.class);
                    intent.putExtra("cuahang", cuaHang);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCuaHang.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView txtTenCH, txtDiaChiCH;
        ImageView imgCuaHang;
        private ItemClickListener itemClickListener;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            txtTenCH = itemView.findViewById(R.id.txt_itemTenCH);
            txtDiaChiCH = itemView.findViewById(R.id.txt_itemDiaChiCH);
            imgCuaHang = itemView.findViewById(R.id.img_itemCuaHang);
            itemView.setOnClickListener(this);
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
