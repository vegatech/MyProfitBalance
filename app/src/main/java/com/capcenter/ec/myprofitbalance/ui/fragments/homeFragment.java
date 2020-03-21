package com.capcenter.ec.myprofitbalance.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capcenter.ec.myprofitbalance.R;
//import com.capcenter.ec.myprofitbalance.ui.VivzAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
//    ConexionSQLiteHelper conn =new ConexionSQLiteHelper(getActivity(), Utilidades.NOMBRE_BD,null, 1);
//
//    ListView lst;
//    String[] opcionnombre={"Ingresos","Egresos","Transferencias"};
//    String[] desc={"Administrar Ingresos","Administrar Egresos","Administrar Transferencias"};
//    int [] imgid ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones};
//
//    public homeFragment() {
//        // Required empty public constructor
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//        BottomNavigationView mbotomnavigationview;
//        mbotomnavigationview =(BottomNavigationView) container.findViewById(R.id.navigationbariew);
//        mbotomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                if (menuItem.getItemId()== R.id.menu_home){
//                    // Intent intent = new Intent(getApplicationContext(), menuActivity.class);
//                    // startActivity(intent);
//                    showSelectedFragment(new homeFragment());
//                }
//                if (menuItem.getItemId()== R.id.menu_Reportes){
//                    Intent intent = new Intent(getActivity(), listaIngresos.class);
//                    startActivity(intent);
//                    //showSelectedFragment(new reportsFragment());
//                }
//                if (menuItem.getItemId()== R.id.menu_ajustes){
//                    showSelectedFragment(new settingsFragment());
//                }
//                return true;
//            }
//        });
//
//        lst= (ListView) container.findViewById(R.id.listview);
//        VivzAdapterh adapter1 = new VivzAdapterh(this,opcionnombre, imgid);
//        lst.setAdapter(adapter1);
//
//        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "se llama a la ventana Ingresos", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), transaccionesActivity.class);
//                long v =lst.getSelectedItemId();//getSelectedItemPosition();
//                int  pa= i+1;
//                intent.putExtra("tipoper",pa);
//                startActivity(intent);
//
//
//            }
//        });
//
//    }// on Create
//
//    private void showSelectedFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
//
//    }
//
//
//}
//
//
//class VivzAdapterh extends ArrayAdapter<String>
//{
//    Context context;
//    int[] images;
//    String[] tituloArray;
//    VivzAdapterh(Context c, String[] titulos,int imagenes[])
//    {
//        super(c,R.layout.single_row,titulos);
//        this.context = c;
//        this.images = imagenes;
//        this.tituloArray=titulos;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row =inflater.inflate(R.layout.single_row,parent,false);
//        ImageView myImage= (ImageView) row.findViewById(R.id.imageView);
//        TextView myTitle=(TextView) row.findViewById(R.id.textView);
//
//        //myImage.setImageDrawable(images[position]);
//        myImage.setImageResource(images[position]);
//        myTitle.setText(tituloArray[position]);
//
//        //return super.getView(position, convertView, parent);
//        return row;
//    }
}