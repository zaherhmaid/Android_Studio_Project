package com.example.gsoft;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

import java.util.regex.Pattern;

public class AddUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;


    private TextView text;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME = "PrefsFile";
    View hView;

    ConnectionClass connectionClass;
    EditText etnp, etlog, etmp, etconmp;
    String utinom,utilog,utimp,uticon;
    Button btnAjt, btnAnnuler;
    ListView lstuser;
    String proid;
    Spinner sp;
    ProgressBar pbbar;
String type;
    String z = "";
 //   private AwesomeValidation awesomeValidation;
 private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
                 //"(?=.*[0-9])" +         //at least 1 digit
                 //"(?=.*[a-z])" +         //at least 1 lower case letter
                 //"(?=.*[A-Z])" +         //at least 1 upper case letter
                 "(?=.*[a-zA-Z])" +      //any letter
                // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                 "(?=\\S+$)" +           //no white spaces
                 ".{8,}" +               //at least 4 characters
                 "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);


        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (savedInstanceState == null) {
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.adduser);
        }
        bindWidget();
        getPreferencesData();

        connectionClass = new ConnectionClass();
       // awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
       // awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        etnp = findViewById(R.id.nomprenom);
        etlog = findViewById(R.id.login);
        etmp = findViewById(R.id.motdepasse);
        etconmp = findViewById(R.id.confirme);
        utinom=etnp.getText().toString();
        utilog=etlog.getText().toString();
        utimp=etmp.getText().toString();
        uticon=etconmp.getText().toString();
       // ettype=findViewById(R.id.type);
        btnAjt = findViewById(R.id.button2);
        btnAnnuler = findViewById(R.id.button);
        lstuser = findViewById(R.id.recyclerView);
        proid = "";
        pbbar = (ProgressBar) findViewById(R.id.progressBar);
        pbbar.setVisibility(View.GONE);


        Spinner sp = findViewById(R.id.spinner);
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
                String selectedItemText = (String) parent.getItemAtPosition(position);
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
       // addValidationToViews();


        btnAjt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               if (etnp.getText().toString().length() == 0) {
                        etnp.setError("Champ requis");
                        etnp.requestFocus();
                    } else if (etlog.getText().toString().length() == 0) {
                        etlog.setError("Champ requis");
                        etlog.requestFocus();
                    } else if (etmp.getText().toString().length() == 0) {
                   etmp.setError("Champ requis");
                   etmp.requestFocus();
               } /*else if(!PASSWORD_PATTERN.matcher(utimp).matches()){
                   etmp.setError("Mot de passe trop faible");
                   etmp.requestFocus();
                    }*/ else if (etconmp.getText().toString().length() == 0) {
                        etconmp.setError("Champ requis");
                        etconmp.requestFocus();
                    } else if (!etmp.getText().toString().equals(etconmp.getText().toString())) {
                        etconmp.setError("Mot de passe ne correspond pas");
                        etconmp.requestFocus();
                    }

             else {

                       AddUtilisateur addutilisateur = new AddUtilisateur();
                       addutilisateur.execute("");
                   }


              /*  etnp.setText("");
                etlog.setText("");
                etmp.setText("");
                etconmp.setText("");*/
              // ettype.setText("");

            }
        });


        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddUser.this, Utilisateur.class);
                startActivity(intent);
            }
        });
    }
   /* private boolean validatenom(){
        String n=etnp.getText().toString().trim();
        if(n.isEmpty()){
            etnp.setError("Champ requis");
            return false;
        }else{
            etnp.setError(null);
            return true;
        }
    }
    private boolean validatelogin(){
        String l=etlog.getText().toString().trim();
        if(l.isEmpty()){
            etlog.setError("Champ requis");
            return false;
        }else{
            etlog.setError(null);
            return true;
        }
    }
    private boolean validatepass(){
        String p=etmp.getText().toString().trim();
        if(p.isEmpty()){
            etmp.setError("Champ requis");
            return false;
        }else{
            etmp.setError(null);
            return true;
        }
    }
    private boolean validateconpass(){
        String c=etconmp.getText().toString().trim();
        if(c.isEmpty()){
            etconmp.setError("Champ requis");
            return false;
        }else{
            etconmp.setError(null);
            return true;
        }
    }*/
   /* private void addValidationToViews(){

        awesomeValidation.addValidation(this, R.id.nomprenom, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.login, RegexTemplate.NOT_EMPTY, R.string.invalid_login);


        awesomeValidation.addValidation(this, R.id.motdepasse, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.confirme, R.id.motdepasse, R.string.invalid_confirm_password);


    }*/
    private void bindWidget() {
        mUsernameEditText = findViewById(R.id.username);
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
            Intent i = new Intent(AddUser.this, Accueil.class);

            startActivity(i);
        } else if (id == R.id.user) {
            Intent i = new Intent(AddUser.this, Utilisateur.class);

            startActivity(i);
        } else if (id == R.id.adduser) {
            Intent i = new Intent(AddUser.this, AddUser.class);
            startActivity(i);
        } else if (id == R.id.config) {
            Intent i = new Intent(AddUser.this, Configuration.class);

            startActivity(i);
        }else if (id == R.id.stat) {
            Intent i = new Intent(AddUser.this, Statistique.class);
            startActivity(i);

        }
        else if (id == R.id.cmd) {
            Intent i = new Intent(AddUser.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.para) {
            Intent i = new Intent(AddUser.this, Parametres.class);

            startActivity(i);
        } else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this, R.style.DialogTheme);
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
                    Intent i = new Intent(AddUser.this, MainActivity.class);
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




    public class AddUtilisateur extends AsyncTask<String, String, String> {

            Boolean isSuccess = false;

            String utinom = etnp.getText().toString().trim();
            String utilog = etlog.getText().toString().trim();
            String utimp = etmp.getText().toString().trim();
            String uticon = etmp.getText().toString().trim();
            @Override
            protected void onPreExecute() {
                pbbar.setVisibility(View.VISIBLE);
            }

          @Override
          protected void onPostExecute(String r) {
              pbbar.setVisibility(View.GONE);
              Toast.makeText(AddUser.this, r, Toast.LENGTH_SHORT).show();

              if(isSuccess) {
                  Toast.makeText(AddUser.this, r, Toast.LENGTH_SHORT).show();
                  finish();
                  Intent intent= new Intent(AddUser.this, Utilisateur.class);
                  startActivity(intent);
              }

            }

            @Override
            protected String doInBackground(String... params) {
                if (utinom.trim().equals("") || utilog.trim().equals("") || utimp.trim().equals("") || uticon.trim().equals(""))
                    z = "Veuillez saisir toutes  les informations";
                else {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Vérifiez votre connexion Internet";
                        } else {
                           String query ="insert into UsersAndroid(username,password,nom,type)values('"+utilog+"','"+utimp+"','"+utinom+"','"+type+"')";

                            PreparedStatement preparedStatement = con.prepareStatement(query);
                            preparedStatement.executeUpdate();
                            z = "Ajout effectué avec succés";
                            isSuccess = true;
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = "Exceptions";
                    }
                }
                return z;
            }
        }

}
