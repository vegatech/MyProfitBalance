package com.capcenter.ec.myprofitbalance.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.view.KeyEvent;

import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.ui.fragments.homeFragment;
import com.capcenter.ec.myprofitbalance.ui.fragments.reportsFragment;
import com.capcenter.ec.myprofitbalance.ui.fragments.transaccionesFragment;
import com.capcenter.ec.myprofitbalance.ui.interfaces.IComunicaFragments;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_HOME;

public class MainActivity extends AppCompatActivity  implements IComunicaFragments, homeFragment.OnFragmentInteractionListener,transaccionesFragment.OnFragmentInteractionListener, reportsFragment.OnFragmentInteractionListener{
    Fragment fragmentInicio, fragmentTransacciones, fragmentReporte;
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
        //Toast.makeText(getApplicationContext(), "ruta de la apk externa"+Utilidades.RUTA_APK, Toast.LENGTH_SHORT).show();
        fragmentInicio=new homeFragment();
        fragmentTransacciones=new transaccionesFragment();
        fragmentReporte = new reportsFragment();

        mbotomnavigationview =(BottomNavigationView) findViewById(R.id.navigationbariew);
        mbotomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()== R.id.menu_home){
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                    showSelectedFragment(new homeFragment());
                    //finish();
                }
                if (menuItem.getItemId()== R.id.menu_Reportes){
                    Intent intent = new Intent(getApplicationContext(), reportesActivity.class);
                    startActivity(intent);
                    finish();
                    //showSelectedFragment(new reportsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_ajustes){
                   // showSelectedFragment(new settingsFragment());
                    Intent intent = new Intent(getApplicationContext(), ajusteActivity.class);
                    startActivity(intent);
                    //finish();
                }
                if (menuItem.getItemId()== R.id.menu_salir){
                    // showSelectedFragment(new settingsFragment());
                    //finish();
                    //(System.exit(0);
                    /*Intent intent = new Intent(ACTION_MAIN);
                    intent.addCategory(CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/
/*                    int p = android.os.Process.myPid();
                    android.os.Process.killProcess(p);
                    System.exit(0);*/

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("¿Desea salir de Finanzas Personales?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
                return true;
            }
        });



        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }// on Create
    public void onClick(View view) {

    }
    @Override
    public void llamaTransacciones(){

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentTransacciones).commit();
    }
    @Override
    public  void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    /*se controla la pulsacion del boton atras*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Finanzas Personales?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
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