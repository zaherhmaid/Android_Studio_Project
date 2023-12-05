package com.example.gsoft;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;



public class superadmin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<ListUser> list;
    private ArrayList<ListUser> values;
    private RecyclerView.Adapter adapter;
    RecyclerView recyclerView; //RecyclerView
    RecyclerView.LayoutManager mLayoutManager;
    private ConnectionClass connectionClass;
    private boolean success = false;
    String type,nom;

    public superadmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Superadmin.
     */
    // TODO: Rename and change types and number of parameters
    public static superadmin newInstance(String param1 , String param2) {
        superadmin fragment = new superadmin ();
        Bundle args = new Bundle ();
        args.putString ( ARG_PARAM1 , param1 );
        args.putString ( ARG_PARAM2 , param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );


    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate ( R.layout.fragment_superadmin , container , false );
        recyclerView = (RecyclerView) rootView.findViewById (R.id.recyclerView );
        recyclerView.setHasFixedSize ( true );
        mLayoutManager = new LinearLayoutManager ( getActivity () );
        recyclerView.setLayoutManager ( mLayoutManager );
        connectionClass = new ConnectionClass ();
        list = new ArrayList<ListUser> ();
        SyncData orderData = new SyncData ();
        orderData.execute ( "" );
        return rootView;

    }
    public class SyncData extends AsyncTask<String, String, String> {
        String z = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
                /* String var="";
                 var=Integer.toString(type);*/
            try {
                Connection con = connectionClass.CONN ();
                if (con == null) {
                    z = "Vérifiez votre connexion Internet";
                } else {
                    String q = "SELECT nom,type FROM UsersAndroid where type =0";
                    Statement s = con.createStatement ();
                    ResultSet r = s.executeQuery ( q );
                    if (r != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (r.next ()) {
                            try {
                                if (r.getString ( "type" ).equals ( "0" )) {
                                    type = "Super admin";
                                }
                                if (r.getString ( "type" ).equals ( "1" )) {
                                    type = "Admin";
                                }
                                if (r.getString ( "type" ).equals ( "2" )) {
                                    type = "Vendeur";
                                }
                                list.add ( new ListUser ( r.getString ( "nom" ) , type ) );

                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }
                        int i = list.size();
                        String sum = Integer.toString(i);
                        z= sum + " utilisateur trouvé";
                        success = true;
                    } else {
                        z = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ();
                Writer writer = new StringWriter ();
                e.printStackTrace ( new PrintWriter ( writer ) );
                z = writer.toString ();
                success = false;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            Toast.makeText ( getActivity (), z + "" , Toast.LENGTH_SHORT ).show ();
            if (success == false) {
            } else {
                try {
                    adapter = new Adapter ( list , getActivity () );
                    recyclerView.setAdapter ( adapter );
                } catch (Exception ex) {

                }

            }

        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<ListUser> values;
        public Context context;
        public void setFilter(ArrayList<ListUser> newList) {
            values = new ArrayList<> ();
            values.addAll ( newList );
            notifyDataSetChanged ();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvnom;
            public TextView tvtype;
            public View layout;
            CardView c;

            public ViewHolder(View v) {
                super ( v );
                layout = v;
                tvnom = (TextView) v.findViewById ( R.id.tvnom );
                tvtype = (TextView) v.findViewById ( R.id.tvtype );
                c = (CardView) v.findViewById ( R.id.cardd );
                c.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity (), Modifier.class);
                        i.putExtra("tvnom", tvnom.getText().toString());
                        startActivity(i);

                    }
                } );
                c.setOnLongClickListener ( new View.OnLongClickListener () {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity (), R.style.DialogTheme );
                        builder.setMessage ( "Etes-vous sûr de vouloir Supprimer ce utilisateur?" );
                        builder.setCancelable ( true );
                        builder.setNegativeButton ( "NON" , new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog , int which) {
                                Intent i = new Intent ( getActivity () , Utilisateur.class );
                                startActivity ( i );
                            }
                        } );
                        builder.setPositiveButton ( "OUI" , new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog , int which) {

                                nom=tvnom.getText ().toString ();
                                connectionClass = new ConnectionClass();
                                DeleteData orderData = new DeleteData ();
                                orderData.execute(nom);

                            }
                        } );

                        AlertDialog alertDialog = builder.create ();
                        alertDialog.show ();



                        return false;
                    }
                } );


            }}

        public Adapter(List<ListUser> myDataset , Context context) {
            values = myDataset;
            this.context = context;
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
            View v = inflater.inflate ( R.layout.listeutilisateur , parent , false );
            ViewHolder vh = new ViewHolder ( v );
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder , int position) {
            final ListUser Listuser = values.get ( position );
            holder.tvtype.setText ( Listuser.getType () );
            holder.tvnom.setText ( Listuser.getNom () );
        }

        @Override
        public int getItemCount() {
            return list.size ();
        }
    }
    private class DeleteData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            super.onPreExecute();
            progress = new ProgressDialog (getActivity ());
            progress.setCancelable(false);
            progress.setTitle("Loading...");
            progress.show();
            /* progress = ProgressDialog.show(getContext(), "Synchronising", "RecyclerView Loading! Please Wait...", true);*/
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    msg = "verifier connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "Delete from UsersAndroid where nom='"+nom.toString ()+"'";
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    msg= "Suppression avec succée";
                    success = true;

                }
            } catch (Exception e) {
                e.printStackTrace ();
                Writer writer = new StringWriter ();
                e.printStackTrace ( new PrintWriter ( writer ) );
                msg = writer.toString ();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG,"On Post running");
            progress.dismiss();
            if (!success) {
                Toast.makeText(getActivity (), "Error de suppression", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText ( getActivity () , "Suppression avec succée" , Toast.LENGTH_SHORT ).show ();
                    Intent intent= new Intent(getActivity (), Utilisateur.class);
                    startActivity(intent);
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }}
    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach ( context );

    }

    @Override
    public void onDetach() {
        super.onDetach ();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
