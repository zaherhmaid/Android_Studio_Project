package com.example.gsoft;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Commande extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private static final String TAG = "ListDataActivity";
    private ListView lv;
   // ArrayList liste<String>();
    private boolean success = false;
    private ConnectionClass connectionClass;
    TextView p;
   CheckBox ch,ch1;
    TextView tot;
    DataBaseHelper db;
    TextView e1;
    TextView desTextView,prixTextView,prixhTextView,quantTextView;
    String z = "";
    Button btnv;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME="PrefsFile";
    View hView;
    private TextView text;
    String u;
    CheckBox chk;
    /*RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    List<listComm> cart = new ArrayList<>();
    CommandeAdapter adapter;*/
    SparseBooleanArray sparseBooleanArray ;
    String desigination,prixh,quantite,t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        connectionClass = new ConnectionClass();
        ch1=findViewById ( R.id.selectall);
        connectionClass = new ConnectionClass();
        // ck = findViewById(R.id.checkBox);
        tot = findViewById(R.id.total);
        db = new DataBaseHelper(this);
       /* SharedPreferences sharedPreferences = getSharedPreferences("Details", 0);
        String name = sharedPreferences.getString("userNameKey", "Data not found");*/
        btnv=findViewById(R.id.btnvalider);
        final ArrayList<HashMap<String, String>> userList = db.GetUsers();
        lv = (ListView) findViewById(R.id.user_list);
        lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
        // customsadapter adapter1 = new customsadapter();
        //lv.setAdapter(adapter1);
        final BaseAdapter adapter = new SimpleAdapter(Commande.this, userList, R.layout.listecommande, new String[]{"code", "designation", "quantite", "pricet"}, new int[]{R.id.code, R.id.desig, R.id.quantite, R.id.prixt});
        lv.setAdapter(adapter );

        Intent i = getIntent();
        String a=i.getStringExtra("item");


        Button back = (Button) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Commande.this, ListearticleActivity.class);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                desTextView=(TextView)view.findViewById(R.id.desig) ;
                prixTextView = (TextView) view.findViewById(R.id.prix);
                prixhTextView = (TextView) view.findViewById(R.id.prixt);
                quantTextView = (TextView) view.findViewById(R.id.quantite);

                desigination=desTextView.getText ().toString ();
                prixh=prixhTextView.getText ().toString ();
                quantite=quantTextView.getText ().toString ();
                t=tot.getText ().toString ();
                String desc = quantTextView.getText().toString();
                Intent modify_intent = new Intent(Commande.this, ModifierCommande.class);
                modify_intent.putExtra("desc", desc);
                startActivity(modify_intent);




            }
        });
        ch1.setOnClickListener(new View.OnClickListener () {

            public void onClick(View v) {



                // TODO Auto-generated method stub
                for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                  View view= lv.getChildAt ( i );

                   chk = (CheckBox) view.findViewById(R.id.checkBox );
                    chk.setChecked ( true );


                }
            }
        });
        btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                    View view = lv.getChildAt ( i );

                    CheckBox chk = (CheckBox) view.findViewById ( R.id.checkBox );
                    desTextView=(TextView)view.findViewById(R.id.desig) ;
                    prixTextView = (TextView) view.findViewById(R.id.prix);
                    prixhTextView = (TextView) view.findViewById(R.id.prixt);
                    quantTextView = (TextView) view.findViewById(R.id.quantite);

                    desigination=desTextView.getText ().toString ();
                    prixh=prixhTextView.getText ().toString ();
                    quantite=quantTextView.getText ().toString ();
                    t=tot.getText ().toString ();

                    if(chk.isChecked ()){
                        try {
                            Connection con = connectionClass.CONN ();
                            if (con == null) {
                                z = "Vérifiez votre connexion Internet";
                            } else {

                                String query = "insert into Commande(desig,quantite,prixunitaire,total)values(?,?,?,?) ";
                                PreparedStatement preparedStatement = con.prepareStatement ( query );
                                preparedStatement.setString ( 1, desigination);
                                preparedStatement.setString ( 2, quantite );
                                preparedStatement.setString (  3, prixh);
                                preparedStatement.setString ( 4, t);
                                preparedStatement.executeUpdate ();
                                Toast.makeText(Commande.this, "Ajout avec succée", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            z = "Exception";
                        }


                    }

                }
            }

        });

        double sum = db.getTotalPrice();
        DecimalFormat df=new DecimalFormat("0.000");
        tot.setText("" + df.format(sum)+"DT");

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(savedInstanceState==null){
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem(R.id.cmd);
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

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        mDrawerlayout.closeDrawers();
        if (id == R.id.home) {
            Intent i = new Intent(Commande.this, Accueil.class);

            startActivity(i);
        }
        else if (id == R.id.user) {
            Intent i = new Intent(Commande.this, Utilisateur.class);

            startActivity(i);
        }
        else if (id == R.id.adduser) {
            Intent i = new Intent(Commande.this, AddUser.class);
            startActivity(i);
        }
        else if (id == R.id.config) {
            Intent i = new Intent(Commande.this, Configuration.class);

            startActivity(i);
        }
        else if (id == R.id.cmd) {
            Intent i = new Intent(Commande.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.stat) {
            Intent i = new Intent(Commande.this, Statistique.class);
            startActivity(i);

        }
        else if (id == R.id.para) {
            Intent i = new Intent(Commande.this, Parametres.class);

            startActivity(i);
        }
        else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Commande.this,R.style.DialogTheme);
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
                    Intent i = new Intent(Commande.this, MainActivity.class);
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




}










