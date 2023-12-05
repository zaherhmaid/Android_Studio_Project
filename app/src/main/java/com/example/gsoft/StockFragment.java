package com.example.gsoft;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextView t1;
    public TextView t2;
    String codee;
    String qte;
    Spinner spinner;
    String t;
    private ConnectionClass connexion;
    private boolean success = false;

    String text;


    public StockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockFragment newInstance(String param1, String param2) {
        StockFragment fragment = new StockFragment();
        Bundle args = new Bundle();
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

        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        t1 = (TextView) root.findViewById(R.id.t1);
        t2 = (TextView) root.findViewById(R.id.t2);
        codee = getArguments().getString("code");
        spinner = (Spinner) root.findViewById ( R.id.spinner );
        String types[] = {"Depot" , "Nabeul" , "Tunis"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                text = (String) parent.getItemAtPosition ( position );
                if(text.equals ( "Nabeul" ))
                {
                    t="Nabeul";
                }
                if(text.equals ( "Tunis" ))
                {
                    t="Tunis";
                }
                else
                {
                    t="Depot";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        return root;

    }

    public class SyncData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;


        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            /*  progress = ProgressDialog.show(getContext(), "Synchronising", "RecyclerView Loading! Please Wait...", true);*/
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection conn = connexion.CONN(); //Connection Object
                if (conn == null) {
                    msg="verifier connexion";
                } else {
                    // Change below query according to your own database.

                    String query="select Nabeul,Tunis from ListeArticleAndroid where code ='" + codee + "'" ;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next ()) try {

                           /* String total = rs.getString ( "s" );
                            t2.setText ( total.toString () );*/

                            if (t.equals ( "Nabeul" )) {
                                t2.setText ( rs.getString ( "Nabeul" ) );

                            }
                            if (t.equals ( "Tunis" )) {
                                t2.setText ( rs.getString ( "Tunis" ) );
                            }/* else {
                                String total = rs.getString ( "s" );
                                t1.setText ( total.toString () );
                                t2.setText ( total.toString () );
                            }*/

                        } catch (Exception ex) {
                            ex.printStackTrace ();
                        }
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


    }



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

}
