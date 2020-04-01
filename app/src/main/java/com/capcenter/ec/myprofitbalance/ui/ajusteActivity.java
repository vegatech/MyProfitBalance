package com.capcenter.ec.myprofitbalance.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capcenter.ec.myprofitbalance.R;

public class ajusteActivity extends AppCompatActivity {
    BottomNavigationView mbotomnavigationviewA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuste);

        mbotomnavigationviewA =(BottomNavigationView) findViewById(R.id.navigationbariewA);
        mbotomnavigationviewA.getMenu().getItem(2).setChecked(true);
        mbotomnavigationviewA.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()== R.id.menu_home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //showSelectedFragment(new homeFragment());
                }
                if (menuItem.getItemId()== R.id.menu_Reportes){
                    Intent intent = new Intent(getApplicationContext(), reportesActivity.class);
                    startActivity(intent);
                    //showSelectedFragment(new reportsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_ajustes){
                    //showSelectedFragment(new settingsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_salir){
                    //showSelectedFragment(new settingsFragment());
                    int p = android.os.Process.myPid();
                    android.os.Process.killProcess(p);
                }
                return true;
            }
        });
    }
}
class VivzAdapterA extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    String[] tituloArray;
    VivzAdapterA(Context c, String[] titulos,int imagenes[])
    {
        super(c,R.layout.listview_ajuste,titulos);
        this.context = c;
        this.images = imagenes;
        this.tituloArray=titulos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.single_row,parent,false);
        ImageView myImage= (ImageView) row.findViewById(R.id.imageView);
        TextView myTitle=(TextView) row.findViewById(R.id.textView);

        //myImage.setImageDrawable(images[position]);
        myImage.setImageResource(images[position]);
        myTitle.setText(tituloArray[position]);

        //return super.getView(position, convertView, parent);
        return row;
    }
}