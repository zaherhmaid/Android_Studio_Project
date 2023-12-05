package com.example.gsoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.steelkiwi.library.IncrementProductView;
import com.steelkiwi.library.listener.OnStateListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifierCommande extends AppCompatActivity {
    TextView amount;
    IncrementProductView productView;
    private String valeur,b;
    DataBaseHelper db;
    private int selectedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_commande);
        amount=findViewById(R.id.amount);
        productView=findViewById(R.id.productview);
        Intent receivedIntent = getIntent();
        //now get the itemID we passed as an extra
       // selectedID = receivedIntent.getIntExtra("id",-1);
        //now get the name we passed as an extra
        valeur= receivedIntent.getStringExtra("desc");
        //b=receivedIntent.getStringExtra("desig");
        //set the text to show the current selected name
        amount.setText(valeur);
        productView.setOnStateListener(new OnStateListener() {
            @Override
            public void onCountChange(int count) {
                amount.setText(""+count*1);
            }

            @Override
            public void onConfirm(int count) {
              //  String v=amount.getText().toString();
              if( amount.length()!=0){

               /*   SharedPreferences sharedPreferences = getSharedPreferences("Details", 0);
                  //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString("userNameKey", v);
                  editor.apply();*/

                  String item = amount.getText().toString();
                /*  if(!item.equals("")){
                      toastMessage("bnj");
                     //db.updateName(item,valeur);
                  }else{
                      toastMessage("You must enter a name");
                  }
                  amount.setText("");*/

                  Intent intent = new Intent(ModifierCommande.this,Commande.class);
                  intent.putExtra("item", item);
                  startActivity(intent);




              }

                //Toast.makeText(ModifierCommande.this,"you want to buy"+count+"product",Toast.LENGTH_LONG).show();
            }
       /* public void AddData(String v){


                Toast.makeText(ModifierCommande.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                Intent i=new Intent(ModifierCommande.this,Commande.class);
                startActivity(i);
               }
*/

            @Override
            public void onClose() {

            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    }

