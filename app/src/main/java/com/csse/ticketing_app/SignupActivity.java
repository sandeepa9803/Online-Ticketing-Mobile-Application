package com.csse.ticketing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SignupActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    EditText fullName, username, nic, password;
    AppCompatButton signupBtn;
    Button alreadyAMember;

    FirebaseDatabase database = FirebaseDatabase.getInstance( Constants.DB_INSTANCE );
    DatabaseReference reference = database.getReference( Constants.DB_USER_REF );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );

        fullName = findViewById( R.id.signup_full_name );
        username = findViewById( R.id.signup_username );
        nic = findViewById( R.id.signup_nic );
        password = findViewById( R.id.signup_pw );
        signupBtn = findViewById( R.id.signup_btn );
        alreadyAMember = findViewById( R.id.have_an_account );

        try {
            alreadyAMember.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( getApplicationContext (), LoginActivity.class );
                    startActivity( intent );
                    finish();
                }
            });
        } catch (NullPointerException e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        }

        /*
         * On click of the sign up button first it will check whether user fill all the input fields correctly.
         * If all input fields are valid, Those input data fill will be sent to addUer() method
         */
        try {
            signupBtn.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!validateFullName() | !validateEmail() | !validateNic() | !validatePassword()){
                        return;
                    } else {
                        String nameStr = fullName.getText().toString();
                        String usernameStr = username.getText().toString();
                        String nicStr = nic.getText().toString();
                        String pwStr = password.getText().toString();

                        //Initialize the default balance a new user get when register into the system
                        String balanceStr = "1000.00";

                        addUser( nameStr, usernameStr, nicStr, pwStr, balanceStr );

                    }

                }
            });
        } catch (NullPointerException e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        }

    }

    private Boolean validateFullName () {
        String nameStr = fullName.getText().toString();

        if (nameStr.isEmpty ()) {
            fullName.setError ( "Fields cannot be empty" );
            return false;
        } else {
            fullName.setError ( null );
            return true;
        }
    }

    private Boolean validateEmail () {
        String emailStr = username.getText().toString();

        if (emailStr.isEmpty ()) {
            username.setError ( "Fields cannot be empty" );
            return false;
        } else {
            username.setError ( null );
            return true;
        }
    }

    private Boolean validateNic () {
        String nicStr = nic.getText().toString();

        if (nicStr.isEmpty ()) {
            nic.setError ( "Fields cannot be empty" );
            return false;
        } else {
            nic.setError ( null );
            return true;
        }
    }

    private Boolean validatePassword () {
        String pwStr = password.getText().toString();

        if (pwStr.isEmpty ()) {
            password.setError ( "Fields cannot be empty" );
            return false;
        } else {
            password.setError ( null );
            return true;
        }
    }

    public UserHelperClass getUser(String username) {
        UserHelperClass helperClass = new UserHelperClass();

        DatabaseReference reference = FirebaseDatabase.getInstance( Constants.DB_INSTANCE ).getReference( Constants.DB_USER_REF );

        Query checkUser = reference.orderByChild( Constants.DB_CHILD_USERNAME ).equalTo( username );

        checkUser.addListenerForSingleValueEvent( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists ( )) {
                    String pwFromDB = snapshot.child( username ).child ( Constants.DB_CHILD_PASSWORD ).getValue ( String.class );
                    String nameFromDB = snapshot.child( username ).child( Constants.DB_CHILD_FULL_NAME ).getValue( String.class );
                    String nicFromDB = snapshot.child( username ).child( Constants.DB_CHILD_NIC ).getValue( String.class );
                    String balanceFromDB = snapshot.child( username ).child( Constants.DB_CHILD_BALANCE ).getValue( String.class );

                    helperClass.setUserData(username, pwFromDB , nameFromDB, nicFromDB, balanceFromDB );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return helperClass;
    }

    /**
     * This method will get the user data and send those data to the firebase database to store
     * If the registration is successful it will notify the user by a Toast massage
     *
     * Else, it will show the error to the user
     *
     * @param fullName
     * @param username
     * @param nic
     * @param password
     * @param balance
     */
    public void addUser(String fullName , String username , String nic  , String password, String balance) {
        DatabaseReference keyRef = reference.child ( username );
        UserHelperClass helperClass = new UserHelperClass ( fullName, username, nic, password, balance );

        keyRef.setValue ( helperClass ).addOnSuccessListener (suc -> {
            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener ( err -> {
            Toast.makeText(getApplicationContext(), "" + err.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}
