package com.csse.ticketing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    ImageView back;
    TextView fullName, username, nic, nameHeading, usernameHeading, mobile, balance;
    AppCompatButton deleteBtn, updateBtn;

    DatabaseReference reference = FirebaseDatabase.getInstance(Constants.DB_INSTANCE).getReference(Constants.DB_USER_REF );

    /**
     * onCreate this method will extract the user details from the bundle and display in the respective TextView by using setText() method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );

        /* Get the user data extras, put by previous activity by calling the getExtras() and store them inside the bundle */
        Bundle bundle = getIntent().getExtras();

        back = findViewById( R.id.back_btn_profile );
        fullName = findViewById( R.id.profile_full_name );
        username = findViewById( R.id.profile_username );
        nic = findViewById( R.id.profile_nic );
        nameHeading = findViewById( R.id.full_name_heading );
        usernameHeading = findViewById( R.id.username_heading );
        mobile = findViewById( R.id.profile_mobile );
        balance = findViewById( R.id.profile_balance );
        deleteBtn = findViewById( R.id.delete_btn_profile );
        updateBtn = findViewById( R.id.update_btn_profile );

        if (bundle == null ) {
            return;
        } else {
            String nameFromDB = bundle.getString( Constants.BUNDLE_FULL_NAME );
            String usernameFromDb = bundle.getString( Constants.BUNDLE_USERNAME );
            String nicFromDB = bundle.getString( Constants.BUNDLE_NIC );
            String balanceFromDb = bundle.getString( Constants.BUNDLE_BALANCE );

            nameHeading.setText( nameFromDB );
            usernameHeading.setText( usernameFromDb );
            fullName.setText( nameFromDB );
            username.setText( usernameFromDb );
            nic.setText( nicFromDB );
            balance.setText( balanceFromDb );
        }

        try {
            back.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } catch (NullPointerException e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        }

        /* Redirect user to the update page on click of the button. */
        try {
            updateBtn.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent ( getApplicationContext (), UpdateUserActivity.class );
                    intent.putExtras ( bundle );
                    startActivity ( intent );
                    finish ();
                }
            } );
        } catch (NullPointerException e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        }

        /* Delete the user profile from the DB and redirect the user to the login page. */
        try {
            deleteBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference.child(bundle.getString( Constants.BUNDLE_USERNAME )).removeValue().addOnSuccessListener( suc -> {
                        Toast.makeText ( ProfileActivity.this , "Account Deleted" , Toast.LENGTH_SHORT ).show ( );
                        Intent intent = new Intent ( getApplicationContext (), LoginActivity.class );
                        startActivity ( intent );
                        finish ();
                    });
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
}