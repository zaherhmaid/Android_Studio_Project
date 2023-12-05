package com.example.gsoft;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class MainActivity extends AppCompatActivity  {



    ConnectionClass connectionClass;
    ProgressBar pbbar;
    EditText usernam,passwordd;
    String user,pass;
    //String type;
    CheckBox mCheckBox;
    private boolean success = false;

   private CheckBox savelogincheckbox;
   private SharedPreferences mPrefs;
   private static final String PREFS_NAME="PrefsFile";
   private EditText mUsernameEditText;
   private EditText mPasswordEditText;
   private Button mButtonLogin;
 //   TextView text = findViewById(R.id.txt);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btn = findViewById(R.id.button);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        connectionClass = new ConnectionClass();//the class file
        usernam = (EditText) findViewById(R.id.username);
        passwordd = (EditText) findViewById(R.id.password);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        pbbar = (ProgressBar) findViewById(R.id.progressBar);
        pbbar.setVisibility(View.GONE);
        //text=findViewById(R.id.txt);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernam.setText(usernam.getText().toString());
                passwordd.setText(passwordd.getText().toString());
                user = usernam.getText().toString().trim();
                pass = passwordd.getText().toString().trim();

                if (user.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, Accueil.class);
                    startActivity(intent);
                } else {
                    user = usernam.getText().toString();
                    pass = passwordd.getText().toString();
                   // typ=typee.getText().toString();
                    DoLogin doLogin = new DoLogin(); // this is the Asynctask
                    doLogin.execute(user, pass);
                  connectionClass=new ConnectionClass();
                 Intent i = new Intent(MainActivity.this,Parametres.class);
                i.putExtra("login",usernam.getText().toString());
               // i.putExtra("password",passwordd.getText().toString());
                }
             //   Intent i = new Intent(MainActivity.this,Parametres.class);
              //  i.putExtra("login",mUsernameEditText.getText().toString());
             //   i.putExtra("password",mPasswordEditText.getText().toString());
            }
        });

        //loadData();
            final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
            final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
            usernameWrapper.setHint("Login");
            passwordWrapper.setHint("Mot de passe");

            //resize iconuser
            final ConstraintLayout layout = findViewById(R.id.login);
            final TextView login = findViewById(R.id.username);
            final float density = getResources().getDisplayMetrics().density;
            final Drawable drawable_iconuser = getResources().getDrawable(R.drawable.user);


            final int width = Math.round(15 * density);
            final int height = Math.round(15 * density);
            drawable_iconuser.setBounds(0, 0, width, height);
            login.setCompoundDrawables(drawable_iconuser, null, null, null);

            //resize iconpassword
            final ConstraintLayout lay = findViewById(R.id.login);
            final TextView password = findViewById(R.id.password);
            final float dens = getResources().getDisplayMetrics().density;
            final Drawable drawable_iconpassword = getResources().getDrawable(R.drawable.lock);


            final int widtth = Math.round(15 * dens);
            final int heightt = Math.round(15 * dens);
            drawable_iconpassword.setBounds(0, 0, widtth, heightt);
            password.setCompoundDrawables(drawable_iconpassword, null, null, null);
            mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            bindWidget();
            getPreferencesData();

        }


    private void getPreferencesData() {
        SharedPreferences sp=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")) {
            String u=sp.getString("pref_name","not found");
            mUsernameEditText.setText(u.toString());
        }
        if(sp.contains("pref_pass")) {
            String p=sp.getString("pref_pass","not found");
            mPasswordEditText.setText(p.toString());
        }

        if(sp.contains("pref_check")) {
           Boolean b=sp.getBoolean("pref_check",false);
            savelogincheckbox.setChecked(b);
        }
    }


    private void bindWidget() {
        mUsernameEditText=findViewById(R.id.username);
        mPasswordEditText=findViewById(R.id.password);

        mButtonLogin=findViewById(R.id.button);
       // text=findViewById(R.id.txt);
        savelogincheckbox=findViewById(R.id.checkBox);
    }

    public class DoLogin extends AsyncTask<String, String, String> {


        String message = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            if(isSuccess) {
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
                finish();
            }


        }
        @Override
        protected String doInBackground(String... params) {
            String user_Str = user;
            String pass_Str =pass;
            if (user_Str.trim().equals("") || pass_Str.trim().equals(""))
                message = "Veuillez saisir votre login et mot de passe ";
            else {
                try {
                    Connection con =connectionClass.CONN();
                    if (con == null) {
                        message = "Vérifiez votre connexion Internet";
                    } else {
                        String query = "select username,password,type from UsersAndroid where username='" + user_Str.toString() + "'and password='" + pass_Str.toString()  + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if( savelogincheckbox.isChecked()){
                         boolean boolIsChecked=savelogincheckbox.isChecked();
                         SharedPreferences.Editor editor=mPrefs.edit();
                         editor.putString("pref_name",mUsernameEditText.getText().toString());
                         editor.putString("pref_pass",mPasswordEditText.getText().toString());
                         //editor.putString("pref_type",mType.getText().toString());
                         editor.putBoolean("pref_check",boolIsChecked);
                         editor.apply();

                         }else{
                            mPrefs.edit().clear().apply();}

                        if (rs.next()) {
                            message = "Connexion réussie " + user;
                            isSuccess = true;
                            con.close();
                          //  if(rs.getString("type").equals("0")){
                                Intent intent = new Intent(MainActivity.this, Accueil.class);
                                startActivity(intent);
                           // }
                           // if(rs.getString("type").equals("1")){
                              // Intent intent=new Intent(MainActivity.this,AccueilDirecteur.class);
                              // startActivity(intent);

                          //  }
                           /* if(rs.getString("type").equals("2")){

                            }*/
                        }
                        else {
                            message = "login et mot de passe incorrect";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex) {
                    isSuccess = false;
                    message = "Exceptions";
                }
            }
            return message;
        }
    }


}
