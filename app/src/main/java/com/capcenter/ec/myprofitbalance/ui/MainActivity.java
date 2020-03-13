package com.capcenter.ec.myprofitbalance.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.ui.fragments.homeFragment;
import com.capcenter.ec.myprofitbalance.ui.fragments.reportsFragment;
import com.capcenter.ec.myprofitbalance.ui.fragments.settingsFragment;

public class MainActivity extends AppCompatActivity  {
    BottomNavigationView mbotomnavigationview;

    ConexionSQLiteHelper conn =new ConexionSQLiteHelper(this, Utilidades.NOMBRE_BD,null, 1);

    ListView lst;
    String[] opcionnombre={"Ingresos","Egresos","Transferencias"};
        String[] desc={"Administrar Ingresos","Administrar Egresos","Administrar Transferencias"};
        int [] imgid ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbotomnavigationview =(BottomNavigationView) findViewById(R.id.navigationbariew);
        mbotomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()== R.id.menu_home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
                    //showSelectedFragment(new homeFragment());
                    finish();
                }
                if (menuItem.getItemId()== R.id.menu_Reportes){
                    Intent intent = new Intent(getApplicationContext(), listaIngresos.class);
                    startActivity(intent);
                    finish();
                    //showSelectedFragment(new reportsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_ajustes){
                   // showSelectedFragment(new settingsFragment());
                    finish();
                }
                return true;
            }
        });

    lst= (ListView) findViewById(R.id.listview);
        VivzAdapter adapter1 = new VivzAdapter(this,opcionnombre, imgid);
        lst.setAdapter(adapter1);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "se llama a la ventana Ingresos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), transaccionesActivity.class);
                long v =lst.getSelectedItemId();//getSelectedItemPosition();
                int  pa= i+1;
                intent.putExtra("tipoper",pa);
                startActivity(intent);


            }
        });

    }// on Create

    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }


}

class VivzAdapter extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    String[] tituloArray;
    VivzAdapter(Context c, String[] titulos,int imagenes[])
    {
        super(c,R.layout.single_row,titulos);
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