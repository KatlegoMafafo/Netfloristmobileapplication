package mafafo.com.netfloristmobileapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mafafo.com.netfloristmobileapplication.Common.Common;
import mafafo.com.netfloristmobileapplication.Model.User;

public class LoginActivity extends AppCompatActivity{

    //Declarations
    private Button buttonSign;
    private EditText editTextEmail, editPhone;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inialize objects
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTexePassword);
        buttonSign = (Button) findViewById(R.id.buttonSignin);
        editPhone = (EditText) findViewById(R.id.editPhone);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(this);

        //intialze database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_user = database.getReference("User");

                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please Waiting");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //checks if user doesn't exist in datatbase
                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {

                            //getUser Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editTextPassword.getText().toString())) {
                                Intent homeIntent = new Intent(LoginActivity.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            }
                            else
                                {
                                Toast.makeText(LoginActivity.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "User doesn't Exist in database !", Toast.LENGTH_SHORT).show();
                            }
                        }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
