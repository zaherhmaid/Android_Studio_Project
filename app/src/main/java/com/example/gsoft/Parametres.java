package com.example.gsoft;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static android.support.constraint.Constraints.TAG;

public class Parametres extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;

    private boolean success = false;
    private TextView text;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME="PrefsFile";
    View hView;
    private ConnectionClass connectionClass;
    TextView mod1,mod2,mod3;
    TextInputEditText et1,et2,et3,et4,et5;
    String a,x,u;
    Button mod,ann;
    String e1,e2,e3,e4,e5;
    String msg = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);

        connectionClass = new ConnectionClass();
        ProfileData orderData = new ProfileData ();
        orderData.execute(x);
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        et1=findViewById(R.id.nomprenom);
        et2=findViewById(R.id.login);
        et3=findViewById(R.id.amot);
        et4=findViewById(R.id.nmot );
        et5=findViewById(R.id.cmot );
        mod=findViewById(R.id.mod );
        mod.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                connectionClass = new ConnectionClass();
                e1=et1.getText().toString ();
                e2= et2.getText().toString();
                e3 = et3.getText().toString();
                e4=et4.getText().toString ();
                e5=et5.getText().toString ();
                if(e4.equals ( e5 ) && e4.toString ().length ()>= 8 && e5.toString ().length ()>=8){
                    ModifiData orderData = new ModifiData ();
                    orderData.execute(x);}
                else{
                    Toast.makeText(Parametres.this, "verifier votre nouveau mot de passe", Toast.LENGTH_SHORT).show();
                }
            }
        } );
        ann=findViewById(R.id.ann);
        ann.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Parametres.this, Accueil.class);
                startActivity(i);
            }
        } );

        Intent i = getIntent();
        x = i.getStringExtra("login");


        // String z = i.getStringExtra("password");

        // et3.setText(z);
        // a=et2.getText().toString();

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(savedInstanceState==null){
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.para);
        }
        bindWidget();
        getPreferencesData();
    }
    private void bindWidget() {
        mUsernameEditText = findViewById(R.id.username);
    }
    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            u = sp.getString("pref_name", "not found");
            // text.setText(u.toString());
            NavigationView navigationView = findViewById(R.id.navigationview);
            hView=navigationView.getHeaderView(0);
            text=hView.findViewById(R.id.txt);
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
            Intent i = new Intent(Parametres.this, Accueil.class);

            startActivity(i);
        }
        else if (id == R.id.user) {
            Intent i = new Intent(Parametres.this, Utilisateur.class);

            startActivity(i);
        }
        else if (id == R.id.adduser) {
            Intent i = new Intent(Parametres.this, AddUser.class);
            startActivity(i);
        }
        else if (id == R.id.config) {
            Intent i = new Intent(Parametres.this, Configuration.class);

            startActivity(i);
        }
        else if (id == R.id.cmd) {
            Intent i = new Intent(Parametres.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.stat) {
            Intent i = new Intent(Parametres.this, Statistique.class);
            startActivity(i);

        }
        else if (id == R.id.para) {
            Intent i = new Intent(Parametres.this, Parametres.class);

            startActivity(i);
        }
        else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Parametres.this,R.style.DialogTheme);
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
                    Intent i = new Intent(Parametres.this, MainActivity.class);
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


    private class ProfileData extends AsyncTask<String, String, String> {

        ProgressDialog progress;


        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            super.onPreExecute();
            progress = new ProgressDialog(Parametres.this);
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
                    String query = "select username,nom,password from UsersAndroid where username ='" + u.toString () + "'";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );

                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next ()) {
                            try {
                                et1.setText ( rs.getString ( "nom" ) );
                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                            try {
                                et2.setText ( rs.getString ( "username" ) );
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


        }
    }

    private class ModifiData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;


        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            super.onPreExecute();
            progress = new ProgressDialog(Parametres.this);
            progress.setCancelable(false);
            progress.setTitle("Loading...");
            progress.show();
            /* progress = ProgressDialog.show(getContext(), "Synchronising", "RecyclerView Loading! Please Wait...", true);*/
        }

        @Override
        protected String doInBackground(String... strings) {
            String psw = null;
            String u1=e1;
            if(u1.trim ().equals ( "" )|| e2.trim ().equals ( "" ) || e3.trim ().equals ( "" ) ||e4.trim ().equals ( "" )||e5.trim ().equals ( "" ))
                msg="verifier votre données";

            try {
                Connection conn = connectionClass.CONN (); //Connection Object
                if (conn == null) {
                    msg = "verifier connexion";
                }
                else {
                    String query = "select password from UsersAndroid where username ='" + u.toString () + "'";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                psw=rs.getString ( "password" );}

                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }}
                    if(psw.equals ( e5 )) {
                        String query2 = "update UsersAndroid set nom='" + et1.getText ().toString () + "',username='" + et2.getText ().toString () + "',password='" + et3.getText ().toString () + "' where username ='" + u.toString () + "'";
                        PreparedStatement preparedStatement = conn.prepareStatement ( query2 );
                        preparedStatement.executeUpdate ();
                        msg = "Modification avec succée";
                        success = true;


                    }
                    else{
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
        protected void onPostExecute(String s){



            super.onPostExecute(s);
            Log.d(TAG,"On Post running");
            progress.dismiss();
            if (!success) {
                Toast.makeText(Parametres.this, "Vérifier votre ancien mot de passe", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText(Parametres.this, "Modification avec succée", Toast.LENGTH_SHORT).show();
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }

        }}}


