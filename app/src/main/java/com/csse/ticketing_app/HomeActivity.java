package com.csse.ticketing_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeActivity extends AppCompatActivity {

    /** Initialize logger */
    public static final Logger log = Logger.getLogger( DisplayPaymentActivity.class.getName() );

    AppCompatButton dashboardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home );

        /* Get the user data extras, put by previous activity by calling the getExtras() and store them inside the bundle */
        Bundle bundle = getIntent().getExtras();

        dashboardBtn = findViewById( R.id.dashboard_btn );

        try {
            dashboardBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent ( getApplicationContext (), DashboardActivity.class );
                    intent.putExtras( bundle );
                    startActivity( intent );
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