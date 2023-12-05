package com.example.gsoft;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActiviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActiviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiviteFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private
    TextView date1,date2,act1,act2,vente,achat;
    ImageView calendar1, calendar2;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    String codee;
    ImageView plus,plus2,groupe1207,imageview;
    private ConnectionClass connexion;
    private boolean success = false;

    private DatePicker datePicker;

    private TextView dateView;
    private int year, month, day;

    private ArrayList<Listestock> itemArrayList1,itemArrayList2;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView1,recyclerView2; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;


    public ActiviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiviteFragment newInstance(String param1, String param2) {
        ActiviteFragment fragment = new ActiviteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemArrayList1 = new ArrayList<Listestock>();
        itemArrayList2 = new ArrayList<Listestock>();
        connexion = new ConnectionClass();
        SyncData orderData = new SyncData ();
        orderData.execute(codee);

    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_activite, container, false);
        codee = getArguments().getString("code");

        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(""));
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        date1 = (TextView) rootView.findViewById(R.id.date1);
        calendar1 = (ImageView) rootView.findViewById(R.id.calendar1);
        calendar1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext (),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        } );
        date2 = (TextView) rootView.findViewById(R.id.date2);
        calendar2 = (ImageView) rootView.findViewById(R.id.calendar2);
        calendar2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext (),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        } );

        recyclerView1 =(RecyclerView) rootView.findViewById(R.id.recyclerView1); //Recylcerview Declaration
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager (this.getActivity ());
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setAdapter ( myAppAdapter );

        recyclerView2 =(RecyclerView) rootView.findViewById(R.id.recyclerView2); //Recylcerview Declaration
        recyclerView2.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager (this.getActivity ());
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setAdapter ( myAppAdapter );






        vente = (TextView) rootView.findViewById(R.id.vente);
        achat= (TextView) rootView.findViewById(R.id.achat);
        plus=(ImageView) rootView.findViewById ( R.id.plus );
        plus.setOnClickListener ( new View.OnClickListener () {
            boolean visible;
            @Override
            public void onClick(View v) {
                visible = !visible;
                recyclerView1.setVisibility(visible ? View.VISIBLE : View.GONE);
                plus.setImageResource (R.drawable.groupe1207);
            }

        } );

        plus2=(ImageView) rootView.findViewById ( R.id.plus2 );
        groupe1207=(ImageView) rootView.findViewById ( R.drawable.groupe1207 ) ;
        plus2.setOnClickListener ( new View.OnClickListener () {
            boolean visible;
            @Override
            public void onClick(View v) {

                visible = !visible;
                recyclerView2.setVisibility(visible ? View.VISIBLE : View.GONE);
                plus2.setImageResource (R.drawable.groupe1207);
            }

        } );
        return rootView;
    }
    /*@SuppressWarnings("deprecation")
    public void setDate(View view) {

        Toast.makeText(getContext (), "ca", Toast.LENGTH_SHORT).show();
    }

    protected Dialog onCreateDialog(int id){
        if (id == 999) {
            return new DatePickerDialog(getContext (),
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date1.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }*/



    private class SyncData extends AsyncTask<String, String, String> {
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
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier connexion";
                } else {
                    String query1 = "select SUM(qte) as s1 from HistoriqueArticleAndroid where  typeo='Achat' and article ='" + codee.toString () + "'";
                    Statement stmt1 = conn.createStatement ();
                    ResultSet rs1 = stmt1.executeQuery ( query1 );
                    if (rs1 != null)// if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs1.next ()) {
                            try {

                                String totala=rs1.getString ( "s1" );
                                achat.setText (totala.toString ());
                            }
                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }

                        msg = "Found";
                        success = true;
                    }


                    String query2 = "select distinct datep,qte,p,remise from HistoriqueArticleAndroid where typeo='Achat' and article ='" + codee.toString () + "'";
                    Statement stmt2 = conn.createStatement ();
                    ResultSet rs2 = stmt2.executeQuery ( query2 );
                    if (rs2 != null)// if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs2.next ()) {
                            try {
                                itemArrayList1.add(new Listestock (rs2.getString ( "datep" ),rs2.getString("qte"), rs2.getString("p"), rs2.getString("remise")));
                            }
                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }
                        msg = "Found";
                        success = true;
                    }



                    else {
                        msg = "No Data found!";
                        success = false;
                    }
                    // Change below query according to your own database.
                    String query3 = "select SUM(qte) as s from HistoriqueArticleAndroid where datep between'" +date1+ "'and'" +date2+ "'and typeo='Vente' and article ='" + codee.toString () + "'";
                    /*String query3= "select SUM(qte) as s1 from HistoriqueArticleAndroid where  typeo='Vente' and article ='" + codee.toString () +"'";*/
                    Statement stmt3 = conn.createStatement ();
                    ResultSet rs3 = stmt3.executeQuery ( query3 );

                    if (rs3 != null)// if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs3.next ()) {
                            try {
                                String total=rs3.getString ( "s1" );
                                vente.setText (total.toString ());


                            }

                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }
                        msg = "Found";
                        success = true;
                    }

                    String query4 = "select distinct datep,qte,p,remise from HistoriqueArticleAndroid where typeo='Vente' and article ='" + codee.toString () + "'";
                    Statement stmt4 = conn.createStatement ();
                    ResultSet rs4 = stmt4.executeQuery ( query4 );
                    if (rs4 != null)// if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs4.next ()) {
                            try {
                                itemArrayList2.add(new Listestock (rs4.getString ( "datep" ),rs4.getString("qte"), rs4.getString("p"), rs4.getString("remise")));
                            }
                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }
                        msg = "Found";
                        success = true;
                    }



                    else {
                        msg = "No Data found!";
                        success = false;
                    }
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
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            if (success == false)
            {
            }
            else {
                try
                {   myAppAdapter = new MyAppAdapter(itemArrayList1 , getContext ());
                    recyclerView1.setAdapter(myAppAdapter);
                    myAppAdapter = new MyAppAdapter(itemArrayList2 , getContext ());
                    recyclerView2.setAdapter(myAppAdapter);

                } catch (Exception ex)
                {

                }

            }
        }

    }

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<Listestock> values;
        private List<Listestock> exampleListeFull;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textDatep;
            public TextView textQte;
            public TextView textPrix;
            public TextView textRemise;
            public ViewHolder(View v) {
                super ( v );
                textDatep =(TextView)v.findViewById ( R.id.text1 );
                textQte =  (TextView)v.findViewById ( R.id.text2 );
                textPrix = (TextView)v.findViewById ( R.id.text3 );
                textRemise =(TextView)v.findViewById ( R.id.text4 );


            }
        }

        // Constructor
        public MyAppAdapter(List<Listestock> myDataset , Context context) {
            values = myDataset;
            exampleListeFull = new ArrayList<> ( values );
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
            View v = inflater.inflate ( R.layout.liste_stock , parent , false );
            ViewHolder vh = new ViewHolder ( v );
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder , final int position) {
            final Listestock listestock = values.get ( position );

            holder.textDatep.setText ( listestock.getDatep ());
            holder.textQte.setText ( listestock.getQte ());
            holder.textPrix.setText ( listestock.getP () );
            holder.textRemise.setText ( listestock.getRemise () );

        }

        @Override
        public int getItemCount() {
            return values.size ();
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
