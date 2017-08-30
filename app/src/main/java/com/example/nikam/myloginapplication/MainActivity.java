package com.example.nikam.myloginapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText username,password;
    Button btnLogin,btnRegister;

    SQLiteDatabase db1=null;

    String user,pass,un,pw;
    private  static  String database_name="Login_Database.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        username=(EditText)findViewById(R.id.edtxt_username);
        password=(EditText)findViewById(R.id.edtxt_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnRegister=(Button)findViewById(R.id.btn_register);

        db1=openOrCreateDatabase(database_name, Context.MODE_PRIVATE,null);

        db1.execSQL("create table if not exists Login_Table(username varchar,password varchar);");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }*/

                user=username.getText().toString();
                pass=password.getText().toString();

                Cursor cursor=db1.rawQuery("select * from Login_Table",null);

                if(cursor!=null)
                {
                    if(cursor.moveToFirst())
                    {
                        int i=0;
                        do{
                            un=cursor.getString(cursor.getColumnIndex("user"));
                            pw=cursor.getString(cursor.getColumnIndex("pass"));

                            if(un.equalsIgnoreCase(user) && pw.equalsIgnoreCase(pass))
                            {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }

                        }while (cursor.moveToNext());
                    }
                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=username.getText().toString();
                pass=password.getText().toString();

                db1.execSQL("insert into Login_Table values("+user+" ',' "+pass+");");
                username.setText("");
                password.setText("");

                Toast.makeText(MainActivity.this, "You Have Registered Successful", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
