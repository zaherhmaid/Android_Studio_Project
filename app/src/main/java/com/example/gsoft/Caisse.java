package com.example.gsoft;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Caisse extends AppCompatActivity {
    private static final String TAG = "Caisse";
    ImageView retour;
    private ArrayList<Listecaisse> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ArrayList<Listecaisse> itemArrayList1;
    private MyAppAdapter myAppAdapter1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false;
    private ConnectionClass connexion;
    Spinner s1;
    Context context;
    String text;
    TextView t1;
    ImageView calendar1;
    DatePickerDialog datePickerDialog ;
    TextView date1,cal;
    String startDateString;
    SimpleDateFormat simpleDateFormat;
    Date dated;
    Button calculer;
    TextView displayDateDebut;
    TextView displayDateFin;
    DatePickerDialog.OnDateSetListener DateListener;
    DatePickerDialog.OnDateSetListener DateListener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse);
     //   date1=findViewById ( R.id.date1 );
        cal=findViewById ( R.id.cal );
        calculer=findViewById ( R.id.calculer );
        retour = findViewById ( R.id.retour );
        recyclerView = findViewById ( R.id.recyclerView ); //Recylcerview Declaration
        recyclerView.setHasFixedSize ( true );
        mLayoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( mLayoutManager );
        displayDateDebut=(TextView) findViewById(R.id.DateDebut) ;
        displayDateFin=(TextView) findViewById(R.id.DateFin) ;
        // use a linear layout manager
        connexion = new ConnectionClass ();
        itemArrayList = new ArrayList<Listecaisse> (); // Connection Class Initialization
        new SyncData ().execute (  );


      /*  calendar1 = (ImageView) findViewById(R.id.calendar1);
        calendar1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog (Caisse.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                startDateString = date1.getText().toString() ;

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        } );*/

        retour.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getApplicationContext () , Accueil.class );
                startActivity ( intent );
            }
        } );
        calculer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                new DateData ().execute (  );

            }
        } );

        s1 = findViewById(R.id.spinner);
        String types[] = {"Tous","Nabeul","Tunis"};
        final List<String>userList = new ArrayList<>(Arrays.asList(types));
        final  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinneritem,userList){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#AFAFAF"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#F7F7F7"));
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.spinneritem);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                text=s1.getSelectedItem ().toString ();
                connexion = new ConnectionClass ();
                itemArrayList = new ArrayList<Listecaisse> ();
                new TotalData ().execute (  );
                if(s1.getSelectedItem ().toString () .equals ( "Tous" )){
                    connexion = new ConnectionClass ();
                    itemArrayList = new ArrayList<Listecaisse> ();
                    new SyncData ().execute (  );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Date Picker
        //Date de début

        Date cal = (Date) Calendar.getInstance().getTime();
        SimpleDateFormat dfin = new SimpleDateFormat("01/01/2019");
        // SimpleDateFormat dfin = new SimpleDateFormat("01/01/yyyy");
        String formattedDateFin = dfin.format(cal);
        displayDateDebut.setText(formattedDateFin.toString());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(cal);
        displayDateFin.setText(formattedDate.toString());
        displayDateDebut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Caisse.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,DateListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        DateListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG, "onDateSet: dd/mm/yy: "+dayOfMonth+"/"+month+"/"+year);
                String Date=dayOfMonth+"/"+month+"/"+year;
                displayDateDebut.setText(Date);
            }
        };
        //Date de fin
        displayDateFin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Caisse.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,DateListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DateListener2= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG, "onDateSet: dd/mm/yy: "+dayOfMonth+"/"+month+"/"+year);
                String Date=dayOfMonth+"/"+month+"/"+year;
                displayDateFin.setText(Date);
            }
        };

    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Aucun caisse trouvé";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( Caisse.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... params)  // Connect to the database, write query and add items to array list
        {

            try {
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "vérifier votre connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "select distinct nom,recette,emplacement,daterec from caisse order by nom ASC";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next ()) {
                            try {
                                itemArrayList.add ( new Listecaisse (  rs.getString ( "nom" ) , rs.getString ( "recette" ) ,rs.getString ( "emplacement" ),rs.getDate ( "daterec" ) ));
                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }
                        int i = itemArrayList.size ();
                        String s = Integer.toString ( i );
                        msg = s + " caisse trouvé";
                        success = true;

                    } else {
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ();
                Writer writer = new StringWriter ();
                e.printStackTrace ( new PrintWriter ( writer ) );
                success = false;
            }
            return msg;

        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss ();
            Toast t = Toast.makeText ( getApplicationContext () , msg + "" , Toast.LENGTH_SHORT );
            t.show ();
            if (!success) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter ( itemArrayList , getApplicationContext ());
                    recyclerView.setAdapter ( myAppAdapter );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }

    }

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<Listecaisse> values;
        private List<Listecaisse> exampleListeFull;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textNom;
            public TextView textEmplacement;
            public TextView textRecette;
            public TextView textDate;
            public CardView card;

            public ViewHolder(View v) {
                super ( v );
                textNom = (TextView) v.findViewById ( R.id.text1 );
                textEmplacement = (TextView) v.findViewById ( R.id.text2 );
                textRecette = (TextView) v.findViewById ( R.id.text3 );
                textDate=(TextView) v.findViewById ( R.id.text4 ) ;
                card = (CardView) v.findViewById ( R.id.card );



            }
        }

        // Constructor
        public MyAppAdapter(List<Listecaisse> myDataset , Context context) {
            values = myDataset;
            exampleListeFull = new ArrayList<> ( values );
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
            View v = inflater.inflate ( R.layout.listecaisse, parent , false );
            ViewHolder vh = new ViewHolder ( v );
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder , final int position) {

            final Listecaisse listecaisse = values.get ( position );
            holder.textNom.setText ( listecaisse.getNom () );
            holder.textEmplacement.setText ( "("+listecaisse.getEmplacement () +")");
            DecimalFormat df1=new DecimalFormat("0.000");
            holder.textRecette.setText(String.valueOf(df1.format(Double.parseDouble(listecaisse.getRecette () ))+"DT"));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(listecaisse.getDaterec ());
            holder.textDate.setText(formattedDate);

        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size ();
        }



    }

    public class TotalData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( Caisse.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier connexion";
                } else {

                    String query1 = "select nom,recette,emplacement,daterec from caisse where emplacement='" + text+ "'";
                    Statement stmt1 = conn.createStatement ();
                    ResultSet rs1 = stmt1.executeQuery ( query1 );
                    if (rs1 != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs1.next()) {
                            try{
                                itemArrayList.add ( new Listecaisse (  rs1.getString ( "nom" ) , rs1.getString ( "recette" ) ,rs1.getString ( "emplacement" ),rs1.getDate ( "daterec" ) ));

                            }
                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }
                        int i = itemArrayList.size ();
                        String s = Integer.toString ( i );
                        msg = s + " caisse trouvé";
                        success = true;

                    }

                }

            }
            catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter ();
                e.printStackTrace(new PrintWriter (writer));
                msg = writer.toString();
                success = false;
            }


            return msg;
        }


        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss ();
            Toast t = Toast.makeText ( getApplicationContext () , msg + "" , Toast.LENGTH_SHORT );
            t.show ();
            if (!success) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter ( itemArrayList , getApplicationContext ());
                    recyclerView.setAdapter ( myAppAdapter );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }


    }


    public class DateData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( Caisse.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier connexion";
                } else {

                    String query1 = "select sum(recette) as s from caisse where emplacement='" + text+ "'and daterec='"+startDateString+ "'";
                    Statement stmt1 = conn.createStatement ();
                    ResultSet rs1 = stmt1.executeQuery ( query1 );
                    if (rs1 != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs1.next()) {
                            try{
                                String t=rs1.getString ( "s" );
                                cal.setText (t +"DT");
                            }
                            catch (Exception ex) {
                                ex.printStackTrace ();
                            }

                        }
                        int i = itemArrayList.size ();
                        String s = Integer.toString ( i );
                        msg = s + " caisse trouvé";
                        success = true;

                    }

                }

            }
            catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter ();
                e.printStackTrace(new PrintWriter (writer));
                msg = writer.toString();
                success = false;
            }


            return msg;
        }


        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss ();
            Toast t = Toast.makeText ( getApplicationContext () , msg + "" , Toast.LENGTH_SHORT );
            t.show ();
            if (!success) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter ( itemArrayList , getApplicationContext ());
                    recyclerView.setAdapter ( myAppAdapter );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }


    }
}











