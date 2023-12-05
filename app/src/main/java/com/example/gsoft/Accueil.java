package com.example.gsoft;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Accueil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private CardView card1;
    private CardView card2;
    private CardView card3;
String t;

     private TextView text;
     View hView;
     private EditText mUsernameEditText;
     private static final String PREFS_NAME="PrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         text=findViewById(R.id.txt);
         mDrawerlayout = findViewById(R.id.drawer);




        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);



        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        text=findViewById(R.id.txt);


        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.home);
        }

        card1 = findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, ScannerActivity.class);
                startActivity(i);
            }
        });


        card2 = findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, ListearticleActivity.class);
                startActivity(i);
            }
        });


        card3=findViewById(R.id.card3);

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Accueil.this,Caisse.class);
                startActivity(i);
            }
        });
        bindWidget();
        getPreferencesData();
    }
     private void bindWidget() { mUsernameEditText = findViewById(R.id.username);
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
        if (sp.contains(("pref_type"))) {
            String t=sp.getString("pref_type","not found");
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        mDrawerlayout.closeDrawers();

        if (id == R.id.home) {
            Intent i = new Intent(Accueil.this, Accueil.class);
            startActivity(i);
        } else if (id == R.id.user) {
            Intent i = new Intent(Accueil.this, Utilisateur.class);
            startActivity(i);
        }
        if((t=="0")||(t=="1")) {
            if (id == R.id.adduser) {
                Intent i = new Intent(Accueil.this, AddUser.class);
                startActivity(i);
            }
            if (id == R.id.config) {
                Intent i = new Intent(Accueil.this, Configuration.class);
                startActivity(i);
            }
            if (id == R.id.cmd) {
                Intent i = new Intent(Accueil.this, Commande.class);
                startActivity(i);
            }
            if (id == R.id.stat) {
                Intent i = new Intent(Accueil.this, Statistique.class);
                startActivity(i);
            }
            if (id == R.id.para) {
                Intent i = new Intent(Accueil.this, Parametres.class);
                startActivity(i);
            }
        }
         if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this,R.style.DialogTheme);
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
                    Intent i = new Intent(Accueil.this, MainActivity.class);
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





