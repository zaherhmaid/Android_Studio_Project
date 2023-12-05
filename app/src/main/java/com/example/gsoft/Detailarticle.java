package com.example.gsoft;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabWidget;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.app.DatePickerDialog;


import org.w3c.dom.Text;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import static android.support.constraint.Constraints.TAG;
public class Detailarticle extends AppCompatActivity {

    private static final String TAG = "DetailArticle";
    private ConnectionClass connectionclass;
    private boolean success = false;
    TabHost tabHost;
    ImageButton btn_back;
    ImageButton btn_close;
    RecyclerView recyclerView;
    TextView displayDateDebut;
    TextView displayDateFin;
    TextView tvcode, tvdesig, tvprix;
    DatePickerDialog.OnDateSetListener DateListener;
    DatePickerDialog.OnDateSetListener DateListener2;
    String codeStr, text, StartDate;
    TextView stockTotal;
    TextView stockEmp;
    TextView NumPrixA;
    TextView NumPrixHt;
    TextView NumPrixTtc;
    TextView pourc_remiseClient;
    TextView tv;
    TextView NumMarge;
    TextView Num_remiseclient;
    EditText sim_remise;
    TextView Num_sim, stock_Nab, stock_Tun, marge_sim;
    Button btn_Marge, BtnD,dateok;
    TextView btn_PA;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    checkCode asyncTask1;
    TextView e1, e2;
    String startD;
    String textCode, textDesig, textPrix;
    FloatingActionButton btn;
    public TextView t1, t2, textstock;
    String codee, c;
    String qte;
    Spinner spinner;
    private ConnectionClass connexion;
    String text1, t;
    ProgressBar progress;
    ArrayAdapter adapter;
    String total1, total2;
    String msg="";
    TextView date1,date2,vente,achat;
    ImageView calendar1, calendar2;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    ImageView plus,plus2,groupe1207,imageview;
    private ArrayList<Listestock> itemArrayList1,itemArrayList2;  //List items Array
    private ActiviteFragment.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView1,recyclerView2; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    String sdate1,sdate2;
    SimpleDateFormat simpleDateFormat;
    Date dated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailarticle);
        //getIncomingIntent();
        btn_PA = findViewById ( R.id.btn_pa );
        btn_Marge = (Button) findViewById ( R.id.btn_Marge );
        Num_sim = (TextView) findViewById ( R.id.simul_remise_client );
        sim_remise = (EditText) findViewById ( R.id.sim_n );
        Num_remiseclient = (TextView) findViewById ( R.id.number_remiseclient );
        NumMarge = (TextView) findViewById ( R.id.number_marge );
        pourc_remiseClient = (TextView) findViewById ( R.id.pourc_remiseClient );
        NumPrixHt = (TextView) findViewById ( R.id.number_HTC );
        NumPrixTtc = (TextView) findViewById ( R.id.number_TTC );
        NumPrixA = (TextView) findViewById ( R.id.number_prixA );
        btn_back = (ImageButton) findViewById ( R.id.back );
        btn_close = (ImageButton) findViewById ( R.id.close );
        displayDateDebut = (TextView) findViewById ( R.id.DateDebut );
        displayDateFin = (TextView) findViewById ( R.id.DateFin );
        tvdesig = (TextView) findViewById ( R.id.desig );
        tvcode = (TextView) findViewById ( R.id.code );
        tvprix = (TextView) findViewById ( R.id.prix );
        dateok = (Button) findViewById ( R.id.DateButton);
        Intent intent = getIntent ();
        textCode = intent.getStringExtra ( "textCode" );
        textDesig = intent.getStringExtra ( "textDesig" );
        textPrix = intent.getStringExtra ( "textPrix" );
        tvcode.setText ( textCode );
        tvdesig.setText ( textDesig );
        tvprix.setText ( textPrix );
        btn = findViewById ( R.id.ajout );
        recyclerView1 =(RecyclerView)findViewById(R.id.recyclerView1); //Recylcerview Declaration
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager (Detailarticle.this);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setAdapter ( myAppAdapter );

        recyclerView2 =(RecyclerView) findViewById(R.id.recyclerView2); //Recylcerview Declaration
        recyclerView2.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager (Detailarticle.this);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setAdapter ( myAppAdapter );
        vente = (TextView) findViewById(R.id.vente);
        achat= (TextView) findViewById(R.id.achat);
        plus=(ImageView) findViewById ( R.id.plus );
        plus.setOnClickListener ( new View.OnClickListener () {
            boolean visible;
            @Override
            public void onClick(View v) {
                visible = !visible;
                recyclerView1.setVisibility(visible ? View.VISIBLE : View.GONE);
                plus.setImageResource (R.drawable.groupe1207);
            }

        } );

        plus2=(ImageView)findViewById ( R.id.plus2 );

        plus2.setOnClickListener ( new View.OnClickListener () {
            boolean visible;
            @Override
            public void onClick(View v) {

                visible = !visible;
                recyclerView2.setVisibility(visible ? View.VISIBLE : View.GONE);
                plus2.setImageResource (R.drawable.groupe1207);
            }

        } );




        // sim_remise.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        t1 = (TextView) findViewById ( R.id.t1 );
        t2 = (TextView) findViewById ( R.id.t2 );
        textstock = findViewById ( R.id.textstock );
        progress = (ProgressBar) findViewById ( R.id.progressBar );
        spinner = (Spinner) findViewById ( R.id.spinner );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource ( Detailarticle.this ,R.array.numbers , android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter ( adapter );
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                text1 = spinner.getSelectedItem ().toString ();
                connexion = new ConnectionClass ();
                // checkCode check_Code = new checkCode ();
                textstock.setText (text1.toString ());
                // check_Code.execute ( codeStr );
                try {
                    Connection conn = connexion.CONN (); //Connection Object
                    if (conn == null) {
                        msg = "verifier connexion";
                    } else {

                        String query1 = "select Depot,Nabeul,Tunis from ListeArticleAndroid where code= '" + codeStr.toString () +"'";
                        Statement stmt1 = conn.createStatement ();
                        ResultSet rs1 = stmt1.executeQuery ( query1 );
                        if (rs1.next()) {

                            if (text1.equals ( "Depot" )) {

                                total1 = rs1.getString ( "Depot" );
                                t1.setText ( total1 );
                            }
                            if (text1.equals ( "Nabeul" )) {

                                total1 = rs1.getString ( "Nabeul" );
                                t1.setText ( total1 );
                            }
                            if (text1.equals ( "Tunis" )) {

                                total1 = rs1.getString ( "Tunis" );
                                t1.setText ( total1 );
                            }

                        }

                        success = true;


                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter ();
                    e.printStackTrace(new PrintWriter (writer));
                    msg = writer.toString();
                    success = false;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


        connectionclass = new ConnectionClass ();
        codeStr = tvcode.getText ().toString ();
        //StartDate = displayDateDebut.getText().toString();
        checkCode check_Code = new checkCode ();
        check_Code.execute ( codeStr );


        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder ( Detailarticle.this );
                View mView = getLayoutInflater ().inflate ( R.layout.ajout_commande , null );
                final NumberPicker picker = (NumberPicker) mView.findViewById ( R.id.numberPicker1 );
                picker.setMinValue ( 1 );
                picker.setMaxValue ( 100 );
                mBuilder.setPositiveButton ( "VALIDER" , new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog , int id) {
                        int p = picker.getValue ();
                        String code = tvcode.getText ().toString ();
                        String designation = tvdesig.getText ().toString ();
                        String quantite = Integer.toString ( p );
                        String price = tvprix.getText ().toString ();

                        DataBaseHelper dbHandler = new DataBaseHelper ( Detailarticle.this );
                        dbHandler.insertUserDetails ( code , designation , quantite , price );
                        Toast.makeText ( Detailarticle.this , "Article ajouté au panier avec succès" , Toast.LENGTH_SHORT ).show ();

                    }
                } );

                mBuilder.setView ( mView );
                AlertDialog dialog = mBuilder.create ();
                dialog.show ();

               /* @Override
                public void onAddProduct() {
                    cart_count++;
                    invalidateOptionsMenu();
                    Snackbar.make((CoordinatorLayout)findViewById(R.id.parentlayout), "Added to cart !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }*/
             /* ListearticleActivity fcat=new ListearticleActivity();
                fcat.onAddProduct();*/

            }


        } );

        dateok.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                try {
                    Connection conn =connectionclass.CONN (); //Connection Object
                    if (conn == null) {
                        msg = "verifier connexion";
                    } else {
                        String query1 = "select SUM(qte) as s from HistoriqueArticleAndroid where typeo='Achat' and article ='" + codeStr.toString () + "'and datep >='" + sdate1 + "'and datep <='" +sdate2+"'";
                        Statement stmt1 = conn.createStatement ();
                        ResultSet rs1 = stmt1.executeQuery ( query1 );

                        if (rs1 .next ())// if resultset not null, I add items to itemArraylist using class created
                        {

                            String totala = rs1.getString ( "s" );
                            achat.setText ( totala.toString () );

                        }

                        // Change below query according to your own database.
                        String query3 = "select SUM(qte) as s1 from HistoriqueArticleAndroid where  typeo='Vente' and article ='" + codeStr.toString () + "'and datep>='" + sdate1 + "'and datep<='"+sdate2+"'";
                        Statement stmt3 = conn.createStatement ();
                        ResultSet rs3 = stmt3.executeQuery ( query3 );

                        if (rs3 .next ())// if resultset not null, I add items to itemArraylist using class created
                        {
                            String total = rs3.getString ( "s1" );
                            vente.setText ( total.toString () );

                        }

                        String query2 = "select distinct datep,qte,p,remise from HistoriqueArticleAndroid where typeo='Achat' and article ='" + codeStr.toString () + "'and datep>='" + sdate1 + "'and datep<='"+sdate2+"'";
                        Statement stmt2 = conn.createStatement ();
                        ResultSet rs2 = stmt2.executeQuery ( query2 );
                        if (rs2 .next ())// if resultset not null, I add items to itemArraylist using class created
                        {
                            itemArrayList1 = new ArrayList<Listestock> ();
                            itemArrayList1.add ( new Listestock ( rs2.getString ( "datep" ) , rs2.getString ( "qte" ) , rs2.getString ( "p" ) , rs2.getString ( "remise" ) ) );

                        }

                        String query4 = "select distinct datep,qte,p,remise from HistoriqueArticleAndroid where typeo='Vente' and article ='" + codeStr.toString () + "'and datep>='" + sdate1 + "'and datep='"+sdate2+"'";
                        Statement stmt4 = conn.createStatement ();
                        ResultSet rs4 = stmt4.executeQuery ( query4 );
                        if (rs4.next ())// if resultset not null, I add items to itemArraylist using class created
                        {
                            itemArrayList2 = new ArrayList<Listestock> ();
                            itemArrayList2.add ( new Listestock ( rs4.getString ( "datep" ) , rs4.getString ( "qte" ) , rs4.getString ( "p" ) , rs4.getString ( "remise" ) ) );


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                    Writer writer = new StringWriter ();
                    e.printStackTrace ( new PrintWriter ( writer ) );
                    msg = writer.toString ();
                    success = false;
                }
            }

        } );
        listView = (ExpandableListView) findViewById ( R.id.HistList );
        // initData();
        // listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter ( listAdapter );
        sim_remise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String pourc_sim=sim_remise.getText().toString();
                final String getttc=NumPrixTtc.getText().toString();
                final String getPA=NumPrixA.getText().toString();
                final String getMarge=NumMarge.getText().toString();
                final String getOldHT=NumPrixHt.getText().toString();


                if (pourc_sim.matches("")){
                    sim_remise.setText("0");
                }else{
                    Double sim_pourc=Double.valueOf(pourc_sim);
                    Double value_ttc=Double.valueOf(getttc);
                    Double value_pa=Double.valueOf(getPA);

                    double sim3=((100-sim_pourc)*value_ttc)/100;
                    Num_sim.setText(String.format(Locale.getDefault(),"%.3f", sim3));



                    double value_OldHT=Double.valueOf(getOldHT);
                    double NewHT=((100-sim_pourc)*value_OldHT)/100;

                    //    double N_marge=((NewHT-value_pa)/value_pa)*100;
                    //  int i = (int) Math.round(N_marge);
                    //String marge=Integer.toString(i);
                    //marge_sim.setText(marge+"%");


                }
            }
        });


       /* btn_PA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumPrixA.getCurrentTextColor()==getResources ().getColor ( R.color.colorPrimaryDark )){
                    NumPrixA.setTextColor(getResources ().getColor ( R.color.White ));
                }else{
                    NumPrixA.setTextColor(getResources ().getColor ( R.color.colorPrimaryDark));
                }
            }
        });

        btn_Marge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumMarge.getCurrentTextColor()==getResources ().getColor ( R.color.colorPrimaryDark )){
                    NumMarge.setTextColor(getResources ().getColor ( R.color.White ));
                }else{
                    NumMarge.setTextColor(getResources ().getColor ( R.color.White ));
                }
            }
        });*/


        btn_back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );
        btn_close.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(Fichearticle.this, screen2.class);
                startActivity(intent);*/
            }
        } );

        tabHost = (TabHost) findViewById ( R.id.onglet );
        tabHost.setup ();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec ( "Activité" );
        spec.setContent ( R.id.tab1 );
        spec.setIndicator ( "Historique" );
        tabHost.addTab ( spec );
        tabHost.setCurrentTab ( 1 );
        tabHost.getTabWidget ().getChildAt ( tabHost.getCurrentTab () ).getBackground ().setColorFilter ( Color.parseColor ( "#5887F9" ) , PorterDuff.Mode.MULTIPLY );// selected
        TextView tv = (TextView) tabHost.getCurrentTabView ().findViewById ( android.R.id.title ); //for Selected Tab
        tv.setTextColor ( Color.parseColor ( "#5887F9" ) );


        //Tab 2
        spec = tabHost.newTabSpec ( "Stock" );
        spec.setContent ( R.id.tab2 );
        spec.setIndicator ( "Stock" );
        tabHost.addTab ( spec );


        //Tab 3
        spec = tabHost.newTabSpec ( "Prix" );
        spec.setContent ( R.id.tab3 );
        spec.setIndicator ( "Prix" );
        tabHost.addTab ( spec );
        tabHost.setOnTabChangedListener ( new OnTabChangeListener () {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabHost.getTabWidget ().getChildCount (); i++) {
                    tabHost.getTabWidget ().getChildAt ( tabHost.getCurrentTab () ).getBackground ().setColorFilter ( Color.parseColor ( "#5887F9" ) , PorterDuff.Mode.MULTIPLY );
                    TextView tv = (TextView) tabHost.getTabWidget ().getChildAt ( i ).findViewById ( android.R.id.title ); //Unselected Tabs
                    tv.setTextColor ( Color.parseColor ( "#AA000000" ) );
                }

                tabHost.getTabWidget ().getChildAt ( tabHost.getCurrentTab () ).getBackground ().setColorFilter ( Color.parseColor ( "#5887F9" ) , PorterDuff.Mode.MULTIPLY );// selected
                TextView tv = (TextView) tabHost.getCurrentTabView ().findViewById ( android.R.id.title ); //for Selected Tab
                tv.setTextColor ( Color.parseColor ( "#5887F9" ) );



            }

        } );


        //Date Picker
        //Date de début

        Date cal = (Date) Calendar.getInstance ().getTime ();
        SimpleDateFormat dfin = new SimpleDateFormat ( "01/01/2019" );
        // SimpleDateFormat dfin = new SimpleDateFormat("01/01/yyyy");
        String formattedDateFin = dfin.format ( cal );
        displayDateDebut.setText ( formattedDateFin.toString () );

        SimpleDateFormat df = new SimpleDateFormat ( "dd/MM/yyyy" );
        String formattedDate = df.format ( cal );
        displayDateFin.setText ( formattedDate.toString () );
        displayDateDebut.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance ();
                int year = cal.get ( Calendar.YEAR );
                int month = cal.get ( Calendar.MONTH );
                int day = cal.get ( Calendar.DAY_OF_MONTH );
                DatePickerDialog dialog = new DatePickerDialog ( Detailarticle.this , android.R.style.Theme_DeviceDefault_Dialog_MinWidth , DateListener , year , month , day );
                dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );
                dialog.show ();
            }
        } );


        DateListener = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view , int year , int month , int dayOfMonth) {
                month = month + 1;
                Log.d ( TAG , "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year );
                String Date = dayOfMonth + "/" + month + "/" + year;
                displayDateDebut.setText ( Date );
                sdate1=displayDateDebut.getText ().toString ();
            }
        };
//Date de fin
        displayDateFin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance ();
                int year = cal.get ( Calendar.YEAR );
                int month = cal.get ( Calendar.MONTH );
                int day = cal.get ( Calendar.DAY_OF_MONTH );
                DatePickerDialog dialog = new DatePickerDialog ( Detailarticle.this , android.R.style.Theme_DeviceDefault_Dialog_MinWidth , DateListener2 , year , month , day );
                dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );
                dialog.show ();
            }
        } );
        DateListener2 = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view , int year , int month , int dayOfMonth) {
                month = month + 1;
                Log.d ( TAG , "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year );
                String Date = dayOfMonth + "/" + month + "/" + year;
                displayDateFin.setText ( Date );
                sdate2=displayDateFin.getText ().toString ();
            }
        };


    }


    public class InputFilterMinMax implements InputFilter {
        private int min, max;

        public InputFilterMinMax(int min , int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min , String max) {
            this.min = Integer.parseInt ( min );
            this.max = Integer.parseInt ( max );
        }

        @Override
        public CharSequence filter(CharSequence source , int start , int end , Spanned dest , int dstart , int dend) {
            try {
                int input = Integer.parseInt ( dest.toString () + source.toString () );
                if (isInRange ( min , max , input )) return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a , int b , int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    //get parameters code

    /*private void getIncomingIntent(){
        if(getIntent().hasExtra("code")){
            String code=getIntent().getStringExtra("code");
            setCode(code);
        }
    }*/

    private void setCode(String code) {
        TextView codetext = findViewById ( R.id.code );
        codetext.setText ( code );
    }


    public class checkCode extends AsyncTask<String, String, String> {
        String ConnectionResult = "";
        Boolean isSuccess = false;


        @Override
        protected String doInBackground(String... params) {

            String usernam = codeStr;
            TextView st = (TextView) findViewById ( R.id.DateDebut );
            StartDate = st.getText ().toString ();
            String st1 = StartDate;


            try {
                Connection conn = connectionclass.CONN ();

                // Connect to database
                if (conn == null) {
                    ConnectionResult = "Vérifiez votre connection Internet!";
                } else {

                    String query = "select depot+nabeul+tunis as stocktotal, depot, nabeul, tunis, prixa, marge, prixht, prixttc, remise, prixnet " + "from ListeArticleAndroid where code= '" + usernam.toString () +"'";
                    Statement stmt = conn.createStatement ();

                    ResultSet rs = stmt.executeQuery ( query );

                    if (rs.next ()) {
                        isSuccess = true;

                       /* String des = rs.getString("desig");
                        tvdesig.setText(des);

                        String st_T = rs.getString("stocktotal");
                        stockTotal.setText(st_T);
                        String stock_tunis = rs.getString("tunis");
                        stock_Tun.setText(stock_tunis);
                        String stock_nabeul = rs.getString("nabeul");
                        stock_Nab.setText(stock_nabeul);
                        String stock_depot = rs.getString("depot");
                        stockEmp.setText(stock_depot);*/
                        total2 = rs.getString ( "stocktotal" );
                        t2.setText ( total2 );

                        String p_a = rs.getString ( "prixa" );
                        NumPrixA.setText ( p_a );
                        String marge = rs.getString ( "marge" );
                        NumMarge.setText ( marge );
                        String ht = rs.getString ( "prixht" );
                        NumPrixHt.setText ( ht );
                        String ttc = rs.getString ( "prixttc" );
                        NumPrixTtc.setText ( ttc );
                        String rem_client = rs.getString ( "remise" );
                        pourc_remiseClient.setText ( rem_client + '%' );
                        String num_prixnet = rs.getString ( "prixnet" );
                        Num_remiseclient.setText ( num_prixnet );
                        Num_sim.setText ( ttc );
                        marge_sim.setText ( marge );
                    }




                    /*    String query1 = "select isnull((select sum(qte) from historiquearticleandroid where article ='" + usernam.toString() +"'and datep >= convert(datetime,'01/01/2018', 103) and datep <= convert(datetime,'25/12/2018', 103)"+
                                "and typeo='Achat'), 0) as achat," +
                                "isnull((select sum(qte) from historiquearticleandroid where article ='" + usernam.toString() + "' and typeo='Vente'), 0) as vente," +
                                "isnull((select sum(marg) from historiquearticleandroid where article ='" + usernam.toString() + "' and typeo='Vente'), 0) as marge";
                        Statement stmt1 = conn.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);


                        if (rs1.next())
                        {
                            String totala = rs1.getString("achat");


                            listDataHeader.add("Achat = " + totala);

                            List<String> achat = new ArrayList<>();
                            achat.add("Expandable ListView");
                            achat.add("Google Map");
                            achat.add("Chat Application");
                            achat.add("Firebase ");

                            listHash.put(listDataHeader.get(0), achat);

                            String totalv = rs1.getString("vente");
                            listDataHeader.add("Vente = " + totalv);


                            String query2 = "select qte from historiquearticleandroid where article ='" +usernam.toString()+
                                    "'and typeo='Vente' and datep >= convert(datetime, '"+st1.toString()+"',103) and datep <= convert(datetime, '31/12/2018',103)";

                            Statement stmt2 = conn.createStatement();
                            ResultSet rs2 = stmt2.executeQuery(query2);

                            List<String> Vente = new ArrayList<>();
                            if (!rs2.next()) {
                                Vente.add("Vide");
                            }
                            else {

                                do {
                                    Vente.add(rs2.getString("qte"));
                                } while (rs2.next());
                            }



                            listHash.put(listDataHeader.get(1), Vente);

                            String totalm = rs1.getString("marge");
                            listDataHeader.add("Marge dégagée = " + totalm);
                            List<String> Marge = new ArrayList<>();
                            Marge.add("Expandable ListView");
                            Marge.add("Google Map");
                            Marge.add("Chat Application");
                            Marge.add("Firebase ");
                            listHash.put(listDataHeader.get(2), Marge);

                            listDataHeader.add("Analyse = ");
                            List<String> analyse = new ArrayList<>();
                            analyse.add("Expandable ListView");
                            analyse.add("Google Map");
                            analyse.add("Chat Application");
                            analyse.add("Firebase ");
                            listHash.put(listDataHeader.get(3), analyse);




                        }else {
                            isSuccess = false;
                        }*/

                    conn.close ();


                }

            } catch (Exception ex) {
                isSuccess = false;
                ConnectionResult = ex.getMessage ();
            }


            return ConnectionResult;

        }

   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB)
         private void StartAsyncTaskInParallel(checkCode task) {
             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                 task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
             else
                 task.execute();
         }

        private void initData() {
            listDataHeader = new ArrayList<> ();
            listHash = new HashMap<> ();


        }*/


    }
}