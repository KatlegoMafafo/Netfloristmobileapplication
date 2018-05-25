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

import mafafo.com.netfloristmobileapplication.Model.User;

public class RegistrationActivity extends AppCompatActivity{
    //Declarations
    private Button buttonRegister;
    private EditText editTextEmail, editPhone;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTexeEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editTextPassword = (EditText) findViewById(R.id.editTexePassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //intialze database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  final FirebaseDatabase database = FirebaseDatabase.getInstance();
               // final DatabaseReference table_user = database.getReference("User");

                final ProgressDialog mDialog = new ProgressDialog(RegistrationActivity.this);
                mDialog.setMessage("Please Waiting");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if already user
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Phone Number already registered", Toast.LENGTH_LONG).show();
                        }
                        else{
                            mDialog.dismiss();
                            User user = new User(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(RegistrationActivity.this, "registration Successful", Toast.LENGTH_LONG).show();
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