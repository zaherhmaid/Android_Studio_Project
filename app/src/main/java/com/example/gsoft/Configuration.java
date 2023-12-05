package com.example.gsoft;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Configuration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;


    EditText et_port;
    EditText et_bd;
    EditText et_user;
    EditText et_pass;
    SharedPreferences sharedPreferences;
    SharedPreferences prefs;
    private ConnectionClass connectionClass;
    private Button boutton;
    String st1,st2,st3,st4;
    static  String p,b,u,pa;
    private TextView text;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME="PrefsFile";
    View hView;
    public static String iport,base,user,passwor;
    String ip = "192.168.1.10:1433";
    String db = "GSoft5";
    String un = "sa";
    String password ="";

    Connection conn = null;
    String ConnURL = null;

    public EditText getEt_port() {
        return et_port;
    }

    public EditText getEt_bd() {
        return et_bd;
    }

    public EditText getEt_user() {
        return et_user;
    }

    public EditText getEt_pass() {
        return et_pass;
    }

    public String getP() {
        return p;
    }

    public String getB() {
        return b;
    }

    public String getU() {
        return u;
    }

    public String getPa() {
        return pa;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);
        connectionClass = new ConnectionClass();
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (savedInstanceState == null) {
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.config);
        }
        et_port = findViewById(R.id.ip);
        et_bd = findViewById(R.id.nbd);
        et_user = findViewById(R.id.user);
        et_pass = findViewById(R.id.password);
        boutton = findViewById(R.id.btn_enre);
        read();
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
              /*  connectionClass=new ConnectionClass();
                connectionClass.setIp(getP());
                connectionClass.setDb(getB());
                connectionClass.setUn(getU());
                connectionClass.setPassword(getPa());*/

            }
        });
        bindWidget();
        getPreferencesData();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences ( this );
        iport = prefs.getString ( "portKey" , et_port.getText ().toString () );
        base = prefs.getString ( "baseKey" , et_bd.getText ().toString () );
        user = prefs.getString ( "userKey" , et_user.getText ().toString () );
        passwor = prefs.getString ( "passKey" , et_pass.getText ().toString () );



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
            hView=navigationView.getHeaderView(0);
            text=hView.findViewById(R.id.txt);
            text.setText(u.toString());


        }

    }
    public void save(){
        p = et_port.getText().toString();
        b = et_bd.getText().toString();
        u = et_user.getText().toString();
        pa = et_pass.getText().toString();
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        sharedPreferences.edit().putString("portKey",p).apply();
        sharedPreferences.edit().putString("baseKey", b).apply();
        sharedPreferences.edit().putString("userKey", u).apply();
        sharedPreferences.edit().putString("passKey", pa).apply();
        Toast.makeText(Configuration.this, "Enregistrer", Toast.LENGTH_SHORT).show();

    }

    public void read() {
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        st1=  sharedPreferences.getString("portKey","");
        et_port.setText(st1);
        st2=  sharedPreferences.getString("baseKey","");
        et_bd.setText(st2);
        st3=  sharedPreferences.getString("userKey","");
        et_user.setText(st3);
        st4=  sharedPreferences.getString("passKey","");
        et_pass.setText(st4);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        mDrawerlayout.closeDrawers();
        if (id == R.id.home) {
            Intent i = new Intent(Configuration.this, Accueil.class);

            startActivity(i);
        } else if (id == R.id.user) {
            Intent i = new Intent(Configuration.this, Utilisateur.class);
            startActivity(i);
        }
        else if (id == R.id.adduser) {
            Intent i = new Intent(Configuration.this, AddUser.class);
            startActivity(i);
        }else if (id == R.id.config) {
            Intent i = new Intent(Configuration.this, Configuration.class);

            startActivity(i);}
        else if (id == R.id.cmd) {
            Intent i = new Intent(Configuration.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.stat) {
                Intent i = new Intent(Configuration.this, Statistique.class);
                startActivity(i);

        } else if (id == R.id.para) {
            Intent i = new Intent(Configuration.this, Parametres.class);

            startActivity(i);
        } else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Configuration.this,R.style.DialogTheme);
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
                    Intent i = new Intent(Configuration.this, MainActivity.class);
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




}
