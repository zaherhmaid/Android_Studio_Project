package com.example.gsoft;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

import static android.support.constraint.Constraints.TAG;
public class Modifier extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;

    int nmp,cmp;
    private boolean success = false; // boolean
    private ArrayList<ListUser> list;
    private RecyclerView.Adapter adapter;
    RecyclerView recyclerView; //RecyclerView
    RecyclerView.LayoutManager mLayoutManager;
    private ConnectionClass connectionClass;

    TextInputLayout typ;
    private TextView text;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME = "PrefsFile";
    View hView;
    String type;
    ProgressBar pbbar;
    TextInputEditText et1,et2,et3,et4,et5;
    String a,x,u,nom;
    Button mod,ann;
    String e1,e2,e3,e4,e5;
    String msg = "";
    Spinner sp, s;
    String selectedItemText,spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);


        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        connectionClass = new ConnectionClass();
        ProfileData orderData = new ProfileData ();
        orderData.execute(x);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (savedInstanceState == null) {
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.user);
        }
        typ=findViewById ( R.id.type );
        et1=findViewById(R.id.nomprenom);
        et2=findViewById(R.id.login);
        et3=findViewById(R.id.amot);
        et4=findViewById ( R.id.nmot );
        et5=findViewById ( R.id.cmot );
        Intent i = getIntent();
        x = i.getStringExtra("tvnom");
        et1.setText(x);
        nom =et1.getText().toString();

        final Spinner sp = findViewById(R.id.spinner);
        String types[] = {"Super admin","Admin","Vendeur"};
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
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
                spinner=sp.getSelectedItem ().toString ();
                if (selectedItemText.equals("Super admin")) {
                    type="0";
                }
                if (selectedItemText.equals("Admin")) {
                    type="1";
                }
                if (selectedItemText.equals("Vendeur")) {
                    type="2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        e1=et1.getText ().toString ();
        e2= et2.getText().toString();
        e3 =et3.getText().toString();
        e4=et4.getText ().toString ();
        e5=et5.getText ().toString ();
        mod=findViewById ( R.id.mod );
        mod.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                connectionClass = new ConnectionClass();

                if (et1.getText().toString().length() == 0) {
                    et1.setError("Champ vide");
                    et1.requestFocus();
                } else if (et2.getText().toString().length() == 0) {
                    et2.setError("Champ vide");
                    et2.requestFocus();
                } else if (et3.getText().toString().length() == 0) {
                    et3.setError("Champ vide");
                    et3.requestFocus();
                } /*else if(!PASSWORD_PATTERN.matcher(utimp).matches()){
                   etmp.setError("Mot de passe trop faible");
                   etmp.requestFocus();
                    }*/ else if (et4.getText().toString().length() == 0) {
                    et4.setError("Champ vide");
                    et4.requestFocus();
                }else if (et5.getText().toString().length() == 0) {
                    et5.setError("Champ vide");
                    et5.requestFocus();
                } else if (!et4.getText().toString().equals(et5.getText().toString())) {
                    et5.setError("Confirmation mot de passe incorrect");
                    et5.requestFocus();
                }
                else if(et4.getText ().toString ().length ()>= 8 && et5.getText ().toString ().length ()>=8){
                    ModifiData orderData = new ModifiData ();
                    orderData.execute(x);}
                else{
                    Toast.makeText(Modifier.this, "Votre nouveau mot de passe est trés court", Toast.LENGTH_SHORT).show();
                }

            }
        } );
        ann=findViewById ( R.id.ann );
        ann.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Modifier.this, Accueil.class);
                startActivity(i);
            }
        } );

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
            hView = navigationView.getHeaderView(0);
            text = hView.findViewById(R.id.txt);
            text.setText(u.toString());


        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        item.setChecked(true);
        mDrawerlayout.closeDrawers();
        if (id == R.id.home) {
            Intent i = new Intent(Modifier.this, Accueil.class);
            startActivity(i);
        } else if (id == R.id.user) {
            Intent i = new Intent(Modifier.this, Utilisateur.class);
            startActivity(i);
        }else if (id == R.id.adduser) {
            Intent i = new Intent(Modifier.this, AddUser.class);
            startActivity(i);
        } else if (id == R.id.config) {
            Intent i = new Intent(Modifier.this, Configuration.class);
            startActivity(i);
        } else if (id == R.id.para) {
            Intent i = new Intent(Modifier.this, Parametres.class);
            startActivity(i);
        } else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Modifier.this, R.style.DialogTheme);
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
                    Intent i = new Intent(Modifier.this, MainActivity.class);
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

    private class ProfileData extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            super.onPreExecute();
            progress = new ProgressDialog(Modifier.this);
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
                    msg = "vérifier votre connexion";
                } else {
                    // Change below query according to your own database.
                    String query = "select username,type from UsersAndroid where nom ='" +nom+ "'";
                    Statement stmt = conn.createStatement ();
                    ResultSet rs = stmt.executeQuery ( query );

                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next ()) {
                            try {
                                et2.setText ( rs.getString ( "username" ) );
                                if (rs.getString ( "type" ) == "0") {
                                    sp.setPrompt ( "Super admin" );


                                }
                                if (rs.getString ( "type" ) == "1") {
                                    sp.setPrompt ( "Admin" );

                                }
                                if (rs.getString ( "type" ) == "2") {
                                    sp.setPrompt ( "Vendeur" );

                                }
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
            progress = new ProgressDialog(Modifier.this);
            progress.setCancelable(false);
            progress.setMessage("veuillez patienter");
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
                    msg = "vérifier votre connexion";
                }
                else {
                    String query = "select password from UsersAndroid where nom ='" +nom + "'";
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
                    if(psw.toString ().equals ( et3.getText ().toString () )) {
                        String query2 = "update UsersAndroid set nom='" + et1.getText ().toString () + "',username='" + et2.getText ().toString () + "',password='" + et4.getText ().toString () + "',type='" +type.toString () +"'where nom ='" +nom + "'";
                        PreparedStatement preparedStatement = conn.prepareStatement ( query2 );
                        preparedStatement.executeUpdate ();
                        msg = "Modification avec succès";
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
                Toast.makeText(Modifier.this, "Vérifier votre ancien mot de passe", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText(Modifier.this, "Modification avec succès", Toast.LENGTH_SHORT).show();
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }

        }}}


