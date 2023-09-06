package com.example.atfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.atfood.Model.ChucNang;
import com.example.atfood.R;

import java.util.List;

public class ChucNangAdapter extends BaseAdapter {
    List<ChucNang> arrayChucNang;
    Context context;

    public ChucNangAdapter(Context context,List<ChucNang> array) {
        this.arrayChucNang = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayChucNang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        TextView txtTenChucNang;
        ImageView imgHinhanh;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_chuc_nang, null);
            viewHolder.txtTenChucNang = convertView.findViewById(R.id.txt_itemTenCN);
            viewHolder.imgHinhanh = convertView.findViewById(R.id.img_itemChucNang);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.txtTenChucNang.setText(arrayChucNang.get(position).getTenchucnang());
        Glide.with(context).load(arrayChucNang.get(position).getHinhanh()).into(viewHolder.imgHinhanh);
        return convertView;
    }
}