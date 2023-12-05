package com.example.gsoft;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SearchView;


import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;


public class Utilisateur extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    ImageView ajouter;
    Button t;
    ImageView ims;
    private boolean success = false; // boolean
    private ArrayList<ListUser> list;
    private ArrayList<ListUser> values;
    private RecyclerView.Adapter adapter;
    RecyclerView recyclerView; //RecyclerView
    RecyclerView.LayoutManager mLayoutManager;
    private ConnectionClass connectionClass;
    private TextView text,tvnom,supadmin,ad,vend;
    private EditText mUsernameEditText;
    private static final String PREFS_NAME = "PrefsFile";
    View hView;
    String type;
    String nom;
    String u,x;
    private UtilisateursDataAdapter mAdapter;
    public CardView card;
    ProgressBar pbbar;
    FloatingActionButton btn;
    EditText etnp,etlog,etconmp,etcommp2;
    EditText etmp,e;
    // private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_utilisateur );
        toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        mDrawerlayout = findViewById ( R.id.drawer );
        bindWidget ();
        getPreferencesData ();
        NavigationView navigationView = findViewById ( R.id.navigationview );
        navigationView.bringToFront ();
        navigationView.setNavigationItemSelectedListener ( this );
        tvnom = (TextView) findViewById ( R.id.tvnom );
        mToggle = new ActionBarDrawerToggle ( this , mDrawerlayout , toolbar , R.string.open , R.string.close );
        mDrawerlayout.addDrawerListener ( mToggle );
        mToggle.syncState ();
        if (savedInstanceState == null) {
            /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new utilisateur()).commit();*/
            navigationView.setCheckedItem ( R.id.user );
        }
       /* recyclerView = (RecyclerView) findViewById ( R.id.recyclerView );
        recyclerView.setHasFixedSize ( true );
        mLayoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( mLayoutManager );*/
        connectionClass = new ConnectionClass ();
        list = new ArrayList<ListUser> ();
        // SyncData orderData = new SyncData ();
        //orderData.execute ( "" );
        setupRecyclerView ();
        btn = findViewById ( R.id.fab );
        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( Utilisateur.this , AddUser.class );
                startActivity ( i );
            }
        } );
        Intent i=getIntent ();
        nom=i.getStringExtra ( "tvnom" );
        supadmin=findViewById(R.id.supadmin);
        ad=findViewById(R.id.admin);
        vend=findViewById(R.id.vendeur);
        final superadmin superadmin=new superadmin ();
        final admin admin=new admin ();
        final vendeur vendeur=new vendeur ();
        final FragmentManager fragmentManager=
                getSupportFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();

        transaction.add(R.id.container,superadmin);
        transaction.commit();
        supadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supadmin.setTextColor ( getResources ().getColor (R.color.DarkGrey ));
                ad.setTextColor ( getResources ().getColor (R.color.clearWhite ));
                vend.setTextColor ( getResources ().getColor (R.color.clearWhite));
                FragmentTransaction transaction= fragmentManager.beginTransaction();

                transaction.replace(R.id.container,superadmin);
                transaction.commit();
            }
        });
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.setTextColor ( getResources ().getColor (R.color.DarkGrey ));
                supadmin.setTextColor ( getResources ().getColor (R.color.clearWhite ));
                vend.setTextColor ( getResources ().getColor (R.color.clearWhite ));
                FragmentTransaction transaction= fragmentManager.beginTransaction();

                transaction.replace(R.id.container,admin);
                transaction.commit();
            }
        });
        vend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vend.setTextColor ( getResources ().getColor (R.color.DarkGrey ));
                supadmin.setTextColor ( getResources ().getColor (R.color.clearWhite ));
                ad.setTextColor ( getResources ().getColor (R.color.clearWhite ));
                FragmentTransaction transaction= fragmentManager.beginTransaction();

                transaction.replace(R.id.container,vendeur);
                transaction.commit();
            }
        });

     /*   mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);*/
    }

    private void setupRecyclerView() {

        SwipeController swipeController = new SwipeController ( new SwipeControllerActions () {
            @Override
            public void onRightClicked(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Utilisateur.this , R.style.DialogTheme );
                builder.setMessage ( "Etes-vous sûr de vouloir Supprimer ce utilisateur?" );
                builder.setCancelable ( true );
                builder.setNegativeButton ( "NON" , new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {
                        Intent i = new Intent ( Utilisateur.this , Utilisateur.class );
                        startActivity ( i );
                    }
                } );
                builder.setPositiveButton ( "OUI" , new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {

                       /* connectionClass = new ConnectionClass();
                        DeleteData orderData = new DeleteData ();
                        orderData.execute(nom);*/

                    }
                } );

                AlertDialog alertDialog = builder.create ();
                alertDialog.show ();

            }

           /*   mAdapter.user.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());*/


        } );
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper ( swipeController );
        itemTouchhelper.attachToRecyclerView ( recyclerView );

  /* recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            swipeController.onDraw(c);
        }
    });*/
    }

    private void bindWidget() {
        mUsernameEditText = findViewById ( R.id.username );
    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences ( PREFS_NAME , MODE_PRIVATE );
        if (sp.contains ( "pref_name" )) {
            u = sp.getString ( "pref_name" , "not found" );
            // text.setText(u.toString());
            NavigationView navigationView = findViewById ( R.id.navigationview );
            hView = navigationView.getHeaderView ( 0 );
            text = hView.findViewById ( R.id.txt );
            text.setText ( u.toString () );

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId ();
        item.setChecked ( true );
        mDrawerlayout.closeDrawers ();
        if (id == R.id.home) {
            Intent i = new Intent ( Utilisateur.this , Accueil.class );

            startActivity ( i );
        } else if (id == R.id.user) {
            Intent i = new Intent ( Utilisateur.this , Utilisateur.class );

            startActivity ( i );
        } else if (id == R.id.adduser) {
            Intent i = new Intent ( Utilisateur.this , AddUser.class );
            startActivity ( i );
        } else if (id == R.id.config) {
            Intent i = new Intent ( Utilisateur.this , Configuration.class );

            startActivity ( i );
        } else if (id == R.id.cmd) {
            Intent i = new Intent(Utilisateur.this, Commande.class);
            startActivity(i);
        }else if (id == R.id.stat) {
                Intent i = new Intent(Utilisateur.this, Statistique.class);
                startActivity(i);


        } else if (id == R.id.para) {
            Intent i = new Intent ( Utilisateur.this , Parametres.class );

            startActivity ( i );
        } else if (id == R.id.dec) {
            AlertDialog.Builder builder = new AlertDialog.Builder ( Utilisateur.this , R.style.DialogTheme );
            builder.setMessage ( "Etes-vous sûr de vouloir vous déconnecter ?" );
            builder.setCancelable ( true );

            builder.setNegativeButton ( "NON" , new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog , int which) {
                    dialog.cancel ();
                }
            } );
            builder.setPositiveButton ( "OUI" , new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog , int which) {
                    Intent i = new Intent ( Utilisateur.this , MainActivity.class );
                    startActivity ( i );
                }
            } );

            AlertDialog alertDialog = builder.create ();
            alertDialog.show ();

            return true;
        }
        DrawerLayout mDrawerlayout = (DrawerLayout) findViewById ( R.id.drawer );
        mDrawerlayout.closeDrawer ( GravityCompat.START );
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer );
        if (drawer.isDrawerOpen ( GravityCompat.START )) {
            drawer.closeDrawer ( GravityCompat.START );
        } else {
            super.onBackPressed ();
        }
    }

    public class SyncData extends AsyncTask<String, String, String> {
        String z = "Vérifiez votre connexion Internet";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
                /* String var="";
                 var=Integer.toString(type);*/
            try {
                Connection con = connectionClass.CONN ();
                if (con == null) {
                    success = false;
                } else {
                    String q = "SELECT nom,type FROM UsersAndroid";
                    Statement s = con.createStatement ();
                    ResultSet r = s.executeQuery ( q );
                    if (r != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (r.next ()) {
                            try {
                                if (r.getString ( "type" ).equals ( "0" )) {
                                    type = "Super admin";
                                }
                                if (r.getString ( "type" ).equals ( "1" )) {
                                    type = "Admin";
                                }
                                if (r.getString ( "type" ).equals ( "2" )) {
                                    type = "Vendeur";
                                }
                                list.add ( new ListUser ( r.getString ( "nom" ) , type ) );

                            } catch (Exception ex) {
                                ex.printStackTrace ();
                            }
                        }


                    } else {
                        z = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ();
                Writer writer = new StringWriter ();
                e.printStackTrace ( new PrintWriter ( writer ) );
                z = writer.toString ();
                success = false;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            Toast.makeText ( Utilisateur.this , z + "" , Toast.LENGTH_SHORT ).show ();
            if (success == false) {
            } else {
                try {
                    adapter = new Adapter ( list , Utilisateur.this );
                    recyclerView.setAdapter ( adapter );
                } catch (Exception ex) {

                }

            }

        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<ListUser> values;
        public Context context;
        public void setFilter(ArrayList<ListUser> newList) {
            values = new ArrayList<> ();
            values.addAll ( newList );
            notifyDataSetChanged ();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvnom;
            public TextView tvtype;
            public View layout;
            CardView c;

            public ViewHolder(View v) {
                super ( v );
                layout = v;
                tvnom = (TextView) v.findViewById ( R.id.tvnom );
                tvtype = (TextView) v.findViewById ( R.id.tvtype );
                c = (CardView) v.findViewById ( R.id.cardd );
                c.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Utilisateur.this, Modifier.class);
                        i.putExtra("tvnom", tvnom.getText().toString());
                        startActivity(i);
                        finish();
                    }
                } );
                c.setOnLongClickListener ( new View.OnLongClickListener () {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder ( Utilisateur.this , R.style.DialogTheme );
                        builder.setMessage ( "Etes-vous sûr de vouloir Supprimer ce utilisateur?" );
                        builder.setCancelable ( true );
                        builder.setNegativeButton ( "NON" , new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog , int which) {
                                Intent i = new Intent ( Utilisateur.this , Utilisateur.class );
                                startActivity ( i );
                            }
                        } );
                        builder.setPositiveButton ( "OUI" , new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog , int which) {

                                nom=tvnom.getText ().toString ();
                                connectionClass = new ConnectionClass();
                                DeleteData orderData = new DeleteData ();
                                orderData.execute(nom);

                            }
                        } );

                        AlertDialog alertDialog = builder.create ();
                        alertDialog.show ();



                        return false;
                    }
                } );


            }}

        public Adapter(List<ListUser> myDataset , Context context) {
            values = myDataset;
            this.context = context;
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );
            View v = inflater.inflate ( R.layout.listeutilisateur , parent , false );
            ViewHolder vh = new ViewHolder ( v );
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder , final int position) {
            final ListUser Listuser = values.get ( position );
            holder.tvtype.setText ( Listuser.getType () );
            holder.tvnom.setText ( Listuser.getNom () );

        }
        @Override
        public int getItemCount() {
            return list.size ();
        }
    }
    private class DeleteData extends AsyncTask<String, String, String> {
        String msg = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            super.onPreExecute();
            progress = new ProgressDialog(Utilisateur.this);
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
                    msg = "Vérifiez votre connexion Internet";
                } else {
                    // Change below query according to your own database.
                    String query = "Delete from UsersAndroid where nom='"+nom.toString ()+"'";
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    msg= "Suppression avec succès";
                    success = true;

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
            if (!success) {
                Toast.makeText(Utilisateur.this, "Error de suppression", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText ( Utilisateur.this , "Suppression avec succès" , Toast.LENGTH_SHORT ).show ();
                    Intent intent= new Intent(Utilisateur.this, Utilisateur.class);
                    startActivity(intent);
                    // itemArrayList.clear();
                } catch (Exception ex) {

                }

            }
        }}

 /*   public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new Superadmin ();
                    break;
                case 1:
                    fragment = new Admin ();
                    break;
                case 2:
                    fragment = new Vendeur ();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Super_admin";
                case 1:
                    return "Admin";
                case 2:
                    return "Vendeur";
            }
            return null;
        }*/
}



