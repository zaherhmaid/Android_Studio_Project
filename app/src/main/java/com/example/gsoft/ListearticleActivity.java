package com.example.gsoft;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.io.File;

import jxl.format.Font;

public class ListearticleActivity extends AppCompatActivity  {
    private static final String FILE_NAME = "example.txt";
    private static final int STORAGE_CODE = 1000;
    FloatingActionButton btn;
    private RelativeLayout llPdf;
    private Bitmap bitmap;
    ImageView retour, filter;
    EditText code;
    String refStr;
    String y;
    private ArrayList<Listearticle> itemArrayList;
    private ArrayList<Listearticle> i1;
    ArrayAdapter<String> adapter1, adapter2;
    private MyAppAdapter myAppAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false;
    private ConnectionClass connexion;
    ImageButton search;
    ImageView panier;
    String sup, inf;
    EditText superieur, inferieur;
    Spinner s1, s2;
    ArrayList<String> data1 = new ArrayList<String> ();
    ArrayList<String> data2 = new ArrayList<String> ();
    String t1, t2;
    Context context;
    private String textAnswer;
    private String path;
    private File dir;
    private File file;
    ImageView icont, tel;
    FloatingActionButton tele;
    static String d;
    String c;
    String p;
    String q;
    ResultSet rs;
    static String TAG = "ExelLog";
    private static String FILE = "c:/temp/FirstPdf.pdf";
  /* private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);*/

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listearticle);
     /*   llPdf = findViewById ( R.id.llPdf );
        tele = findViewById ( R.id.download );
        retour = findViewById ( R.id.retour );
        search = (ImageButton) findViewById ( R.id.search );
        filter = findViewById ( R.id.filter );
        code = (EditText) findViewById ( R.id.code );*/


        recyclerView = findViewById ( R.id.recyclerView ); //Recylcerview Declaration
        recyclerView.setHasFixedSize ( true );
        mLayoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( mLayoutManager );
        // use a linear layout manager
        connexion = new ConnectionClass (); // Connection Class Initialization
//         num1= Integer.parseInt(sup);
        // btn=findViewById ( R.id.fab);
// Connection Class Initialization
        search.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                refStr = code.getText ().toString ();
                itemArrayList = new ArrayList<Listearticle> (); // Arraylist Initialization
                SyncData orderData = new SyncData ();
                orderData.execute ( refStr );
            }
        } );

        code.setOnEditorActionListener ( new TextView.OnEditorActionListener () {
            @Override
            public boolean onEditorAction(TextView v , int actionId , KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    refStr = code.getText ().toString ();
                    itemArrayList = new ArrayList<Listearticle> (); // Arraylist Initialization
                    SyncData orderData = new SyncData ();
                    orderData.execute ( refStr );
                    return true;
                }
                return false;
            }
        } );

        retour.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getApplicationContext () , Accueil.class );
                startActivity ( intent );
            }
        } );

       /* tele.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Document document = new Document();
                String targetPdf = "/sdcard/pdf.pdf";
                File filePath;
                filePath = new File ( targetPdf );
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));

                    document.open();
                    document.addTitle ( "Liste des articles" );
                    PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 , 1 });
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell("Désigniation");
                    table.addCell("Code");
                    table.addCell("Quantité");
                    table.addCell("Prix");
                    table.setHeaderRows(1);
                    PdfPCell[] cells = table.getRow(0).getCells();
                    for (int j=0;j<cells.length;j++){
                        cells[j].setBackgroundColor(BaseColor.GRAY);
                    }
                    try {
                        Connection conn = connexion.CONN (); //Connection Object
                        if (conn == null) {
                            String  msg = "vérifier votre connexion";
                        } else {
                            // Change below query according to your own database.
                            String query = "select distinct code,desig,prixht,sum(qte) as sum from Article,Stock where Article.code=Stock.article group by desig,code,prixht order by code ASC;";
                            Statement stmt = conn.createStatement ();
                            rs = stmt.executeQuery ( query );
                            if (rs != null) {
                                while (rs.next ()) {
                                    try {
                                        c=rs.getString ( "code" );
                                        d=rs.getString ( "desig" );
                                        q=rs.getString ( "sum" );
                                        p=rs.getString ( "prixht" );

                                        table.addCell(d.toString ());
                                        table.addCell(c.toString ());
                                        table.addCell(q.toString ());
                                        table.addCell(p.toString ());

                                    } catch (Exception ex) {
                                        ex.printStackTrace ();
                                    }
                                }

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

                   document.add(addTitle ());
                    document.add(table);
                    document.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText ( ListearticleActivity.this , "PDF is created!!!" , Toast.LENGTH_SHORT ).show ();
                openGeneratedPDF ();

            }


        } );*/
        panier=findViewById(R.id.icon);
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ListearticleActivity.this,Commande.class);
                startActivity(i);
            }
        });



        filter.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder ( ListearticleActivity.this );
                final View mView = getLayoutInflater ().inflate ( R.layout.filtrage , null );
                s1 = (Spinner) mView.findViewById ( R.id.s1 );
                s2 = (Spinner) mView.findViewById ( R.id.s2 );
                superieur = (EditText) mView.findViewById ( R.id.superieur );
                inferieur = (EditText) mView.findViewById ( R.id.inferieur );
                familleData orderData = new familleData ();
                orderData.execute ();
                mBuilder.setPositiveButton ( "Valider" , new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog , int id) {
                        inf = inferieur.getText ().toString ();
                        sup = superieur.getText ().toString ();
                        itemArrayList = new ArrayList<Listearticle> (); // Arraylist Initialization
                        filterData orderData = new filterData ();
                        orderData.execute ( sup , inf );
                        dialog.cancel ();
                    }
                } );

                mBuilder.setNegativeButton ( "Cancel" , new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog , int id) {
                        dialog.cancel ();
                    }
                } );


                adapter1 = new ArrayAdapter<> ( ListearticleActivity.this , android.R.layout.simple_spinner_item , data1 );
                adapter1.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                s1.setAdapter ( adapter1 );
                adapter2 = new ArrayAdapter<> ( ListearticleActivity.this , android.R.layout.simple_spinner_item , data2 );
                adapter2.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                s2.setAdapter ( adapter2 );
                s1.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                    @Override
                    public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                        t1 = s1.getSelectedItem ().toString ();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                } );
                s2.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                    @Override
                    public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                        t2 = s2.getSelectedItem ().toString ();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                } );


                mBuilder.setView ( mView );
                AlertDialog dialog = mBuilder.create ();
                dialog.show ();
            }
        } );


    }
    /*public static Paragraph addTitle(){
        Font fontbold = FontFactory.getFont("Times-Roman", 40, Font.BOLD);
        Paragraph p = new Paragraph("Liste articles", fontbold);
        p.setSpacingAfter(20);
        p.setAlignment(1); // Center
        return p;
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }*/




    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Aucun article trouvé";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( ListearticleActivity.this , "" , "Veuillez patienter" , true );
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
                    String query = "select distinct code,desig,prixht,sum(qte) as sum from Article,Stock where Article.code=Stock.article and code like '%" + refStr.toString () + "%' or desig like '%" + code.toString () + "%' group by desig,code,prixht order by code ASC;";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next ()) {
                            try {
                               /* c=rs.getString ( "code" );
                                d=rs.getString ( "desig" );
                                q=rs.getString ( "sum" );
                                p=rs.getString ( "Prix" );*/
                                itemArrayList.add ( new Listearticle ( rs.getString ( "code" ) , rs.getString ( "desig" ) , rs.getString ( "sum" ) , rs.getString ( "prixht" ) ) );

                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }
                        int i = itemArrayList.size ();
                        String s = Integer.toString ( i );
                        msg = s + " article trouvé";
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
            Toast t = Toast.makeText ( ListearticleActivity.this , msg + "" , Toast.LENGTH_SHORT );
            t.show ();
            if (!success) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter ( itemArrayList , ListearticleActivity.this );
                    recyclerView.setAdapter ( myAppAdapter );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }

    }

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<Listearticle> values;
        private List<Listearticle> exampleListeFull;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textCode;
            public TextView textDesig;
            public TextView textStock;
            public TextView textPrix;
            public CardView card;
            public ViewHolder(View v) {
                super ( v );
                textDesig = (TextView) v.findViewById ( R.id.text1 );
                textCode = (TextView) v.findViewById ( R.id.text2 );
                textStock = (TextView) v.findViewById ( R.id.text3 );
                textPrix = (TextView) v.findViewById ( R.id.text4 );
                card = (CardView) v.findViewById ( R.id.card );
                card.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (ListearticleActivity.this ,Detailarticle.class );
                        intent.putExtra ( "textCode" , textCode.getText ().toString () );
                        intent.putExtra ( "textDesig" , textDesig.getText ().toString () );
                        intent.putExtra ( "textPrix" , textPrix.getText ().toString () );
                        startActivity ( intent );
                    }
                } );

            }
        }

        // Constructor
        public MyAppAdapter(List<Listearticle> myDataset , Context context) {
            values = myDataset;
            exampleListeFull = new ArrayList<> ( values );
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
            View v = inflater.inflate ( R.layout.listearticle , parent , false );
            ViewHolder vh = new ViewHolder ( v );
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder , final int position) {
            final Listearticle listearticle = values.get ( position );
            holder.textDesig.setText ( listearticle.getDesig () );
            holder.textCode.setText ( listearticle.getCode () );
            holder.textStock.setText ( listearticle.getStockable () );
            holder.textPrix.setText ( listearticle.getPrix () );

        /*     holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext (), FichearticleActivity.class);
                        intent.putExtra("textCode", tex );
                        startActivity(intent);
                    }
                });*/
        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size ();
        }



   /*     public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Listearticle> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.clear();
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Listearticle item : exampleListeFull) {
                        if (item.getCode ().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                values.clear();
                values.addAll((List) results.values);
                notifyDataSetChanged();
            }


        };*/

        //Search Menu

    }

    private class filterData extends AsyncTask<String, String, String> {
        String msg;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( ListearticleActivity.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... params)  // Connect to the database, write query and add items to array list
        {

            try {
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier votre connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "select distinct famille,code,desig,qte,prixht from Article,Stock where Article.code=Stock.article and qte >='" + sup.toString () + "'and qte <='" + inf.toString () + "'and famille='" + t1 + "'and sfamille='" + t2 + "'";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next ()) {
                            try {
                                itemArrayList.add ( new Listearticle ( rs.getString ( "code" ) , rs.getString ( "desig" ) , rs.getString ( "qte" ) , rs.getString ( "prixht" ) ) );

                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }
                        int i = itemArrayList.size ();
                        String s = Integer.toString ( i );
                        msg = s + " article trouvé";
                        success = true;

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
            Toast t = Toast.makeText ( ListearticleActivity.this , msg + "" , Toast.LENGTH_LONG );
            t.show ();
            if (!success) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter ( itemArrayList , ListearticleActivity.this );
                    recyclerView.setAdapter ( myAppAdapter );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }

    }


    private class familleData extends AsyncTask<String, String, String> {
        String msg;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( ListearticleActivity.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... params)  // Connect to the database, write query and add items to array list
        {

            try {
                Connection conn = connexion.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier votre connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "select DISTINCT famille,sfamille from Article order by sfamille asc";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next ()) {
                            try {
                                adapter1.add ( rs.getString ( "famille" ) );
                                adapter2.add ( rs.getString ( "sfamille" ) );
                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }


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
            Toast t = Toast.makeText ( ListearticleActivity.this , msg + "" , Toast.LENGTH_LONG );
            t.show ();
            if (!success) {
            } else {
                try {

                    s1.setAdapter ( adapter1 );
                    s2.setAdapter ( adapter2 );
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }

    }



    private void openGeneratedPDF() {
        File file = new File ( "/sdcard/pdf.pdf" );
        if (file.exists ()) {
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            Uri uri = Uri.fromFile ( file );
            intent.setDataAndType ( uri , "application/pdf" );
            intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );

            try {
                startActivity ( intent );
            } catch (ActivityNotFoundException e) {
                Toast.makeText ( ListearticleActivity.this , "No Application available to view pdf" , Toast.LENGTH_LONG ).show ();
            }
        }
    }




}