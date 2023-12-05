package com.example.gsoft;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Handler;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrixFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrixFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrixFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextView e1;
    public TextView e2;
    public TextView e3;
    public TextView e4;
    public TextView e5;
    String codee;

    private ConnectionClass connexion;
    private boolean success = false;


    public PrixFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrixFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrixFragment newInstance(String param1, String param2) {
        PrixFragment fragment = new PrixFragment();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connexion = new ConnectionClass();
        SyncData orderData = new SyncData ();
        orderData.execute(codee);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_prix, container, false);
        codee = getArguments().getString("code");
        e1 = (TextView) rootView.findViewById(R.id.e1);
        e2 = (TextView) rootView.findViewById(R.id.e2);
        e3 = (TextView) rootView.findViewById(R.id.e3);
        e4 = (TextView) rootView.findViewById(R.id.e4);
        e5 = (TextView) rootView.findViewById(R.id.e5);
        return rootView;

    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;


        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            /* progress = ProgressDialog.show(getContext(), "Synchronising", "RecyclerView Loading! Please Wait...", true);*/
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection conn =connexion.CONN();
               //Connection Object
                if (conn == null) {
                    msg="verifier connexion";
                } else {
                    // Change below query according to your own database.

                    String query="select prixa,marge,prixht,remise,prixttc from Article where code ='" + codee.toString () + "'"  ;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                e1.setText(rs.getString("prixa") );
                                e2.setText(rs.getString("marge"));
                                e3.setText(rs.getString("prixht"));
                                e4.setText(rs.getString("remise"));
                                e5.setText(rs.getString("prixttc"));


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

    /*    @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            Thread timer = new Thread() {
                @Override
                public void run() {
            getActivity ().runOnUiThread(new Runnable() {
                public void run(){

                    Toast t = Toast.makeText ( getContext () , "" , Toast.LENGTH_LONG );
                    t.show ();

                }
            });


                }
            };
            timer.start();}*/}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

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

 private void sendData(){
    Intent i=new Intent(getActivity().getApplicationContext(),ListearticleActivity.class);
    i.putExtra("sender_KEY","MyFragment");
    i.putExtra("prix_key",e3.getText().toString());
   getActivity().startActivity(i);
   }


}
