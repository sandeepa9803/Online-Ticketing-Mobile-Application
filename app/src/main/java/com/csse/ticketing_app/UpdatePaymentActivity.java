package com.csse.ticketing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdatePaymentActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    ImageView backBtn;
    AppCompatEditText name, cardNum, expiryDate, cvv, mobileNum;
    AppCompatButton updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_update_payment );

        /* Get the user data extras, put by previous activity by calling the getExtras() and store them inside the bundle */
        Bundle bundle = getIntent().getExtras();

        backBtn = findViewById( R.id.back_btn_updatePayment );
        name = findViewById( R.id.update_payment_name );
        cardNum = findViewById( R.id.update_payment_cardNumber );
        expiryDate = findViewById( R.id.update_payment_expiryDate );
        cvv = findViewById( R.id.update_payment_cvv );
        mobileNum = findViewById( R.id.update_payment_mobileNum );
        updateBtn = findViewById( R.id.payment_update_btn );

        DatabaseReference reference = FirebaseDatabase.getInstance( Constants.DB_INSTANCE ).getReference( Constants.DB_USER_REF );

        Query checkUser = reference.child( bundle.getString( Constants.BUNDLE_USERNAME )).child( Constants.DB_PAYMENT_REF );

        /*
         * This will get the payment details of the currently logged in user if the user already have a payment in the system.
         * and display those details in their respective editText
         *
         * If they do not have a payment they will redirect to Dashboard activity
         */
        checkUser.addListenerForSingleValueEvent( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists ( )) {

                    String nameFromDB = snapshot.child ( Constants.DB_CHILD_CARD_HOLDER ).getValue( String.class );
                    String cardNumFromDB = snapshot.child ( Constants.DB_CHILD_CARD_NUMBER ).getValue( String.class );
                    String expiryDateFromDB = snapshot.child ( Constants.DB_CHILD_EXPIRY_DATE ).getValue( String.class );
                    String cvvFromDB = snapshot.child ( Constants.DB_CHILD_CVV ).getValue( String.class );
                    String mobileNumFromDB = snapshot.child ( Constants.DB_CHILD_MOBILE ).getValue( String.class );

                    name.setText(nameFromDB);
                    cardNum.setText( cardNumFromDB );
                    expiryDate.setText( expiryDateFromDB );
                    cvv.setText( cvvFromDB );
                    mobileNum.setText( mobileNumFromDB );

                } else {
                    Intent intent = new Intent( getApplicationContext(), DashboardActivity.class );
                    startActivity( intent );
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        /*
         * On click of the update button, this will read all updated data from the editText fields and put them in a map.
         *
         * Then it will update the existing details of the database by providing the updated payment details.
         * If the update execute successfully it will notify the user via a toast.
         * If not, it will show the cause of the failure.
         */
        try {
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> map = new HashMap<> ();

                    map.put ( Constants.DB_CHILD_CARD_HOLDER, Objects.requireNonNull(name.getText()).toString() );
                    map.put ( Constants.DB_CHILD_CARD_NUMBER, Objects.requireNonNull(cardNum.getText()).toString() );
                    map.put ( Constants.DB_CHILD_EXPIRY_DATE, Objects.requireNonNull(expiryDate.getText()).toString() );
                    map.put ( Constants.DB_CHILD_CVV, Objects.requireNonNull(cvv.getText()).toString() );
                    map.put ( Constants.DB_CHILD_MOBILE, Objects.requireNonNull(mobileNum.getText()).toString() );

                    reference.child(bundle.getString( Constants.BUNDLE_USERNAME )).child( Constants.DB_PAYMENT_REF ).updateChildren(map).addOnSuccessListener(suc -> {

                        Toast.makeText( UpdatePaymentActivity.this , "Details Updated" , Toast.LENGTH_SHORT ).show();

                        Intent intent = new Intent(getApplicationContext(), DisplayPaymentActivity.class);
                        startActivity( intent );
                        finish();

                    }).addOnFailureListener(er -> {

                        Toast.makeText( UpdatePaymentActivity.this , "" + er.getMessage(), Toast.LENGTH_SHORT).show();

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

        try {
            backBtn.setOnClickListener(new View.OnClickListener() {
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

    }
}