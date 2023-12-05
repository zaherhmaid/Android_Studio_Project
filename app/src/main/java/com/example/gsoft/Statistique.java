package com.example.gsoft;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Statistique extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ConnectionClass connectionClass;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME="PrefsFile";
    View hView;
    private TextView text;
     PieChart pieChart;
     String refStr;
    private boolean success = false;
    ArrayList<PieEntry> yValues =new ArrayList<>();
    TextView t1;
    String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);
        connectionClass = new ConnectionClass();
        StatData orderData = new StatData();
        orderData.execute(refStr);
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (savedInstanceState == null) {
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.stat);
        }
        bindWidget();
        getPreferencesData();

        t1=findViewById(R.id.text);
        b=t1.getText().toString();
        pieChart=(PieChart)findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues =new ArrayList<>();
        yValues.add(new PieEntry(34f,b));
        yValues.add(new PieEntry(23f,"produit2"));
        yValues.add(new PieEntry(14f,"produit3"));
        yValues.add(new PieEntry(60f,"produit4"));
        yValues.add(new PieEntry(9f, "produit5"));
        yValues.add(new PieEntry(55f, "produit6"));
        yValues.add(new PieEntry(16f, "produit7"));


        PieDataSet dataSet=new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        PieData data=new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);


        pieChart.setData(data);


    }


    private void bindWidget() {
        mUsernameEditText = findViewById(R.id.nomprenom);
    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found");
            // text.setText(u.toString());
            NavigationView navigationView = findViewById(R.id.navigationview);
            hView = navigationView.getHeaderView(0);
            text = hView.findViewById(R.id.txt);
            text.setText(u.toString());


        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        mDrawerlayout.closeDrawers();
        if (id == R.id.home) {
            Intent i = new Intent(Statistique.this, Accueil.class);

            startActivity(i);
        } else if (id == R.id.user) {
            Intent i = new Intent(Statistique.this, Utilisateur.class);
            startActivity(i);
        }
        else if (id == R.id.adduser) {
            Intent i = new Intent(Statistique.this, AddUser.class);
            startActivity(i);
        }else if (id == R.id.config) {
            Intent i = new Intent(Statistique.this, Configuration.class);

            startActivity(i);}
        else if (id == R.id.cmd) {
            Intent i = new Intent(Statistique.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.stat) {
            Intent i = new Intent(Statistique.this, Statistique.class);
            startActivity(i);

        } else if (id == R.id.para) {
            Intent i = new Intent(Statistique.this, Parametres.class);

            startActivity(i);
        } else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Statistique.this,R.style.DialogTheme);
            builder.setMessage("Etes-vous sûr de vouloir vous déconnecter ?");
            builder.setCancelable(true);

            builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Statistique.this, MainActivity.class);
                    startActivity(i);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            return true;
        }
        DrawerLayout mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private class StatData extends AsyncTask<String, String, String> {
        String msg = "Aucun article trouvé";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show ( Statistique.this , "" , "Veuillez patienter" , true );
        }

        @Override
        protected String doInBackground(String... params)  // Connect to the database, write query and add items to array list
        {

            try {
                Connection conn = connectionClass.CONN (); //Connection Object
                if (conn == null) {
                    msg = "vérifier votre connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "select distinct desig from Article";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next ()) {
                            try {

                                     t1.setText(rs.getString("desig"));

                            } catch (Exception ex) {
                                ex.printStackTrace ();
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
                e.printStackTrace ();
                Writer writer = new StringWriter();
                e.printStackTrace ( new PrintWriter( writer ) );
                success = false;
            }

            return msg;

        }

        @Override
        protected void onPostExecute(String s) // disimissing progress dialoge, showing error and setting up my listview
        {
            super.onPostExecute(s);
            Log.d(TAG,"On Post running");
            progress.dismiss();

        }

    }
}