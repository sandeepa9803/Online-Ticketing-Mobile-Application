package com.csse.ticketing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPaymentActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    ImageView backBtn;
    AppCompatEditText name, cardNum, expiryDate, cvv, mobileNum;
    AppCompatButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_payment );

        Bundle bundle = getIntent().getExtras();

        backBtn = findViewById( R.id.back_btn_payment );
        name = findViewById( R.id.add_payment_name );
        cardNum = findViewById( R.id.add_payment_cardNumber );
        expiryDate = findViewById( R.id.add_payment_expiryDate );
        cvv = findViewById( R.id.add_payment_cvv );
        mobileNum = findViewById( R.id.add_payment_mobileNum );
        addBtn = findViewById( R.id.payment_add_btn );

        try {
            backBtn.setOnClickListener( new View.OnClickListener() {
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

        try {
            /**
             * This method will store payment details in the paymentHelperClass and store those details on the firebase database when user clicks the add button
             */
            addBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * Check whether user filled all the fields in the form
                     * & whether they are valid data to submit by calling the relevant methods
                     */
                    if ( !validateName() | !validateCardNum() | !validateExpiryDate() | !validateCvv() | !validateMobileNumber() ){
                        return;
                    } else {
                        String nameStr = Objects.requireNonNull(name.getText()).toString();
                        String cardNumStr = Objects.requireNonNull(cardNum.getText()).toString();
                        String expiryDateStr = Objects.requireNonNull(expiryDate.getText()).toString();
                        String cvvStr = Objects.requireNonNull(cvv.getText()).toString();
                        String mobileStr = Objects.requireNonNull(mobileNum.getText()).toString();

                        PaymentHelperClass newPayment = new PaymentHelperClass( nameStr, cardNumStr, expiryDateStr, cvvStr, mobileStr );

                        DatabaseReference reference = FirebaseDatabase.getInstance( Constants.DB_INSTANCE ).getReference(Constants.DB_USER_REF).child(bundle.getString(Constants.BUNDLE_USERNAME)).child(Constants.DB_PAYMENT_REF);

                        /**
                         * Send payment helper class which have the user entered data to the firebase database
                         * According to status it will notify the user
                         */
                        reference.setValue( newPayment ).addOnSuccessListener(suc -> {
                            Toast.makeText(getApplicationContext(), "Details added!", Toast.LENGTH_SHORT).show();
                            onBackPressed ();

                        }).addOnFailureListener ( err -> {
                            Toast.makeText(getApplicationContext(), "" + err.getMessage(), Toast.LENGTH_SHORT).show();
                        });
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

    private Boolean validateName () {
        String nameStr = Objects.requireNonNull( name.getText() ).toString();

        if (nameStr.isEmpty ()) {
            name.setError ( "Fields cannot be empty" );
            return false;
        } else {
            name.setError ( null );
            return true;
        }
    }

    private Boolean validateCardNum () {
        String cardNumStr = Objects.requireNonNull( cardNum.getText() ).toString();

        if (cardNumStr.isEmpty ()) {
            cardNum.setError ( "Fields cannot be empty" );
            return false;
        } else {
            cardNum.setError ( null );
            return true;
        }
    }

    private Boolean validateExpiryDate () {
        String expiryDateStr = Objects.requireNonNull( expiryDate.getText() ).toString();

        if (expiryDateStr.isEmpty ()) {
            expiryDate.setError ( "Fields cannot be empty" );
            return false;
        } else {
            expiryDate.setError ( null );
            return true;
        }
    }

    private Boolean validateCvv () {
        String cvvStr = Objects.requireNonNull( cvv.getText() ).toString();

        if (cvvStr.isEmpty ()) {
            cvv.setError ( "Fields cannot be empty" );
            return false;
        } else {
            cvv.setError ( null );
            return true;
        }
    }

    private Boolean validateMobileNumber () {
        String mobileNumStr = Objects.requireNonNull( mobileNum.getText() ).toString();

        if (mobileNumStr.isEmpty ()) {
            mobileNum.setError ( "Fields cannot be empty" );
            return false;
        } else {
            mobileNum.setError ( null );
            return true;
        }
    }
}