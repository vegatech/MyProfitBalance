package com.capcenter.ec.myprofitbalance.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.ui.MainActivity;

import com.capcenter.ec.myprofitbalance.ui.interfaces.IComunicaFragments;
import com.capcenter.ec.myprofitbalance.ui.transaccionesActivity;
//import com.capcenter.ec.myprofitbalance.ui.VivzAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    ListView lst;
    Activity actividad;
    String[] opcionnombre={"Ingresos","Egresos","Transferencias"};
    String[] desc={"Administrar Ingresos","Administrar Egresos","Administrar Transferencias"};
    int [] imgid ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones};
    View vista;
    IComunicaFragments iComunicaFragments;
    private OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_home, container, false);
        lst= vista.findViewById(R.id.listview);
        VivzAdapterH adapter1 = new VivzAdapterH(getActivity(),opcionnombre, imgid);
        lst.setAdapter(adapter1);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "se llama a la ventana Ingresos", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(getActivity(), transaccionesActivity.class);
                long v =lst.getSelectedItemId();//getSelectedItemPosition();
                int  pa= i+1;
                intent.putExtra("tipoper",pa);
                /* startActivity(intent);*/
                //fragmentTransacciones = new Fragment();
                //iComunicaFragments.showSelectedFragment(fragmentTransacciones);
               // if (pa ==1){
                    iComunicaFragments.llamaTransacciones(pa);
                //}



            }
        });
        return vista;
       // return inflater.inflate(R.layout.fragment_home, container, false);
    }

public void llama(){
    iComunicaFragments.llamaTransacciones(1);

}

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


// TODO: Rename method, update argument and hook method into UI event
public void onButtonPressed(Uri uri) {
    if (mListener != null) {
        mListener.onFragmentInteraction(uri);
    }
}
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad= (Activity) context;
            iComunicaFragments= (IComunicaFragments) this.actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
class VivzAdapterH extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    String[] tituloArray;
    VivzAdapterH(Context c, String[] titulos,int imagenes[])
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