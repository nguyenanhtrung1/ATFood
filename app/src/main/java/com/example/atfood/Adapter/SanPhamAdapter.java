package com.example.atfood.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.EvenBus.SetBadgeEven;
import com.example.atfood.Model.GioHang;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.Myviewholder> {
    Context context;
    List<SanPham> arrSanPham;
    NotificationBadge badgeSoLuongSpGH;

    public SanPhamAdapter(Context context, List<SanPham> arrSanPham) {
        this.context = context;
        this.arrSanPham = arrSanPham;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chi_tiet_cua_hang, parent,false);
        badgeSoLuongSpGH = view.findViewById(R.id.badge_SoLuongSP);
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
        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isClick) {

            }
        });*/
        holder.btnThemSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.arrGioHang.size() > 0){
                    boolean flag = false;
                    int soLuong = 1;
                    for (int i = 0; i < Utils.arrGioHang.size(); i++) {
                        if (Utils.arrGioHang.get(i).getMasanpham() == sanPham.getMasanpham()) {
                            Utils.arrGioHang.get(i).setSoluong(soLuong + Utils.arrGioHang.get(i).getSoluong());
                            long gia = Long.parseLong(sanPham.getGiasanpham());
                            Utils.arrGioHang.get(i).setGiasanpham(gia);
                            flag = true;
                        }
                    }
                    if (flag == false) {
                        long gia = Long.parseLong(sanPham.getGiasanpham());
                        GioHang gioHang = new GioHang();
                        gioHang.setMasanpham(sanPham.getMasanpham());
                        gioHang.setTensanpham(sanPham.getTensanpham());
                        gioHang.setHinhanh(sanPham.getHinhanh());
                        gioHang.setSoluong(soLuong);
                        gioHang.setGiasanpham(gia);
                        Utils.arrGioHang.add(gioHang);
                    }
                }else {
                    int soLuong = 1;
                    long gia = Long.parseLong(sanPham.getGiasanpham());
                    GioHang gioHang = new GioHang();
                    gioHang.setMasanpham(sanPham.getMasanpham());
                    gioHang.setTensanpham(sanPham.getTensanpham());
                    gioHang.setHinhanh(sanPham.getHinhanh());
                    gioHang.setSoluong(soLuong);
                    gioHang.setGiasanpham(gia);
                    Utils.arrGioHang.add(gioHang);
                }
                //test();
                EventBus.getDefault().postSticky(new SetBadgeEven());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSanPham.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img_itemSp;
        TextView txtTenSp, txtMoTaSp, txtGiaSp;
        Button btnThemSp;
        //private ItemClickListener itemClickListener;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            img_itemSp = itemView.findViewById(R.id.img_itemSanPham);
            txtTenSp = itemView.findViewById(R.id.txt_itemTenSp);
            txtMoTaSp = itemView.findViewById(R.id.txt_itemMoTaSp);
            txtGiaSp = itemView.findViewById(R.id.txt_itemGiaSp);
            btnThemSp = itemView.findViewById(R.id.btnThemSPTrongCH);
            //itemView.setOnClickListener(this);
            btnThemSp = itemView.findViewById(R.id.btnThemSPTrongCH);

        }

        /*public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }*/

    }
}
