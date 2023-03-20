package com.csse.ticketing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class UpdateUserActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    AppCompatEditText username, fullName, nic, mobile;
    AppCompatButton updateBtn;

    DatabaseReference reference = FirebaseDatabase.getInstance( Constants.DB_INSTANCE ).getReference( Constants.DB_USER_REF );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_update_user );
        
        username = findViewById ( R.id.update_profile_username );
        fullName = findViewById ( R.id.update_profile_fullname );
        nic = findViewById ( R.id.update_profile_nic );
        mobile = findViewById ( R.id.update_profile_mobileNum );
        updateBtn = findViewById ( R.id.profile_update_btn );

        /* Get the user data extras, put by previous activity by calling the getExtras() and store them inside the bundle */
        Bundle bundle = getIntent().getExtras();
        
        username.setText( bundle.getString( Constants.BUNDLE_USERNAME ) );
        fullName.setText( bundle.getString( Constants.BUNDLE_FULL_NAME ) );
        nic.setText( bundle.getString( Constants.BUNDLE_NIC ) );
        mobile.setText( "+94 779 342 389" );

        /*
         * On click of the update button, this will read all updated data from the editText fields and put them in a map.
         * Also it will update the data in the bundle.
         *
         * Then it will update the existing details of the database by providing the updated user details.
         * If the update execute successfully it will notify the user via a toast.
         * If not, it will show the cause of the failure.
         */
        try {
            updateBtn.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    HashMap<String, Object> updatedData = new HashMap<> ();

                    updatedData.put( Constants.DB_CHILD_USERNAME, Objects.requireNonNull( username.getText() ).toString() );
                    updatedData.put( Constants.DB_CHILD_FULL_NAME , Objects.requireNonNull( fullName.getText() ).toString() );
                    updatedData.put( Constants.DB_CHILD_NIC, Objects.requireNonNull( nic.getText() ).toString() );

                    bundle.putString ( Constants.BUNDLE_USERNAME, username.getText().toString() );
                    bundle.putString ( Constants.BUNDLE_FULL_NAME, fullName.getText().toString() );
                    bundle.putString ( Constants.BUNDLE_NIC, nic.getText().toString() );

                    reference.updateChildren( updatedData ).addOnSuccessListener( suc -> {
                        Toast.makeText ( UpdateUserActivity.this , "Updated" , Toast.LENGTH_SHORT ).show();
                        Intent intent = new Intent ( getApplicationContext (), ProfileActivity.class );
                        intent.putExtras ( bundle );
                        startActivity ( intent );
                        finish ();
                    } );

                }
            } );
        } catch (NullPointerException e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            // Logging thrown exception to the logger
            log.log( Level.SEVERE, e.getMessage());
        }
    }
}