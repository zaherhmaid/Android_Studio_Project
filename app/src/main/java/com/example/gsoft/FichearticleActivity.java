package com.example.gsoft;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class FichearticleActivity extends ListearticleActivity {


    ImageView  arrow;
    TextView code,desig,prix;
    String textCode,textDesig,textPrix;
    FloatingActionButton btn;
    ImageView add;
    TextView tvcode,tvdesig,tvprice;
    Context mContext;
    private int count=0;
    NotificationBadge mBadge;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState );
        setContentView (R.layout.activity_fichearticle );
        mBadge=findViewById(R.id.badge);
        tvcode=findViewById(R.id.code);
        tvdesig=findViewById(R.id.desig);
        tvprice=findViewById(R.id.prix);
          Intent i = getIntent();
          final String valeur = i.getStringExtra("message");



      //  d=findViewById ( R.id.d );

       /* Intent intent1=getIntent ();
        code1=intent1.getStringExtra ( "code" );
        desig1=intent1.getStringExtra ( "desig" );*/
      //  prix1=intent1.getStringExtra("prxht");
        code =(TextView)findViewById (R.id.code);
        //  c=findViewById ( R.id.c );
        desig=(TextView)findViewById(R.id.desig);
        prix=(TextView)findViewById(R.id.prix);
        Intent intent = getIntent ();
        textCode = intent.getStringExtra ( "textCode" );
        textDesig= intent.getStringExtra ( "textDesig" );
        textPrix=  intent.getStringExtra ("textPrix");

        code.setText ( textCode ) ;
        desig.setText ( textDesig );
        prix.setText( textPrix );
        arrow = findViewById ( R.id.arrow );
        arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (FichearticleActivity.this, ListearticleActivity.class );
                startActivity ( intent );
            }
        } );
        btn=findViewById(R.id.ajout);
        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FichearticleActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.ajout_commande, null);
                final NumberPicker picker = (NumberPicker) mView.findViewById(R.id.numberPicker1);
                picker.setMinValue(1);
                picker.setMaxValue(100);
                mBuilder.setPositiveButton("VALIDER",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int p=picker.getValue();
                     String code=tvcode.getText().toString();
                     String designation=tvdesig.getText().toString();
                     String quantite=Integer.toString(p);
                     String price=tvprice.getText().toString();

                        DataBaseHelper dbHandler = new DataBaseHelper(FichearticleActivity.this);
                        dbHandler.insertUserDetails(code,designation,quantite,price);
                        mBadge.setNumber(++count);
                        Intent intent = new Intent(FichearticleActivity.this,ListearticleActivity.class);
                        intent.putExtra("badge",count);
                        startActivity(intent);
                        Toast.makeText(FichearticleActivity.this, "Article ajouté au panier avec succès",Toast.LENGTH_SHORT).show();

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

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
        final TabLayout tabLayout = findViewById ( R.id.tablayout );
        tabLayout.addTab ( tabLayout.newTab ().setText ( "Activite" ) );
        tabLayout.addTab ( tabLayout.newTab ().setText ( "Stock" ) );
        tabLayout.addTab ( tabLayout.newTab ().setText ( "Prix" ) );
        tabLayout.setTabGravity ( TabLayout.GRAVITY_FILL );
/*tabLayout.setOnClickListener ( new View.OnClickListener () {
    @Override
    public void onClick(View v) {
        int position = 0;
        switch (position) {
            case 0:
                FragmentTransaction transaction= fragmentManager.beginTransaction();
                transaction.replace(R.id.container,activiteFragment);
                transaction.commit();

            case 1:
                FragmentTransaction transaction1= fragmentManager.beginTransaction();
                transaction1.replace(R.id.container,stockFragment);
                transaction1.commit();
            case 2:

                FragmentTransaction transaction2= fragmentManager.beginTransaction();
                transaction2.replace(R.id.container,prixFragment);
                transaction2.commit();
        }
    }
 });*/
        final ViewPager viewPager = findViewById ( R.id.pager );
        final PagerAdapter adapter = new PagerAdapter ( getSupportFragmentManager (), tabLayout.getTabCount () );
        viewPager.setAdapter ( adapter );

        viewPager.setOnPageChangeListener ( new TabLayout.TabLayoutOnPageChangeListener ( tabLayout ) );
        tabLayout.setOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem ( tab.getPosition () );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }
  /*  public void AddData(String b) {
        boolean insertData = data.addData(b);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    saveToTxtFile(de,cd,q);
                }
                else{
Toast.makeText(this,"storage required",Toast.LENGTH_LONG).show();
                }
            }

        }
    }*/

 /*  private void saveToTxtFile(String de,String cd,String q) {
String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
try{
File path=Environment.getExternalStorageDirectory();
File dir=new File(path+"/My Files/");
dir.mkdirs();
String fileName="MYFile"+ timeStamp +".txt";
File file=new File(dir,fileName);
  FileWriter fw=new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw=new BufferedWriter(fw);


   bw.write(de+'\n');
   bw.write(cd+'\n');
   bw.write(q+'\n');
   bw.close();
Toast.makeText(this,fileName+"is save to\n"+dir,Toast.LENGTH_LONG).show();
}
catch (Exception e){
Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
}
    }*/

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNoOfTabs;

        public PagerAdapter(FragmentManager fm,int NumberOfTabs) {
            super(fm);
            this.mNoOfTabs = NumberOfTabs;
        }
        @Override
        public Fragment getItem(int position) {
            Bundle bundle= new Bundle();//create bundle instance
            bundle.putString("code", code.getText ().toString ());

            switch (position) {
                case 0:
                    ActiviteFragment activiteFragment = new ActiviteFragment ();
                    activiteFragment.setArguments ( bundle );
                    return activiteFragment;


                case 1:
                    StockFragment stockFragment = new StockFragment ();
                    stockFragment.setArguments ( bundle );
                    return stockFragment;
                case 2:
                    PrixFragment prixFragment = new PrixFragment ();
                    prixFragment.setArguments ( bundle );
                    return prixFragment;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNoOfTabs;
        }
    }
}