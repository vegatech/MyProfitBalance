package com.capcenter.ec.myprofitbalance.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capcenter.ec.myprofitbalance.R;

public class CustomListview extends ArrayAdapter<String> {

private String[] opcionnombre;
private String[] desc;
private Integer [] imgid;
private Activity context;
    public CustomListview(Activity  context, String[] opcionnombre,String[] desc,Integer [] imgid) {
        super(context, R.layout.activity_listaopciones,opcionnombre);
        this.context=context;
        this.opcionnombre=opcionnombre;
        this.desc=desc;
        this.imgid=imgid;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  r =convertView;
        ViewHolder viewHolder =null;
        if (r ==null){
            LayoutInflater layoutInflater= context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.activity_listaopciones,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) r.getTag();
        }
        viewHolder.ivw.setImageResource(imgid[position]);
        viewHolder.tvw1.setText(opcionnombre[position]);
        viewHolder.tvw2.setText(desc[position]);

        return r;
        //return super.getView(position, convertView, parent);
    }
    class ViewHolder
    {
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        ViewHolder(View v)
        {
            tvw1=v.findViewById(R.id.tvopcionname);
            tvw2=v.findViewById(R.id.tvopciondescripcion);
            ivw=v.findViewById(R.id.imageView);
        }
    }
}
