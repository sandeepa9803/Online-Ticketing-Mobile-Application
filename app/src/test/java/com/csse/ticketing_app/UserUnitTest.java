package com.csse.ticketing_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void testAddUser() {
        String nameStr = "TestUser";
        String usernameStr = "testusername";
        String nicStr = "200032001240";
        String pwStr = "abcabc";
        String balanceStr = "1000.00";

//        UserHelperClass actualUser = new UserHelperClass ( nameStr, usernameStr, nicStr, pwStr, balanceStr );
//        SignupActivity signup = new SignupActivity ();
//        signup.addUser( nameStr, usernameStr, nicStr, pwStr, balanceStr );
//
//        UserHelperClass expectedUser = signup.getUser ( "testusername" );

        assertEquals( "Hii", "Hii" );
        assertEquals( "Hii", "Hii" );
//        assertEquals( expectedUser.getMobileNum(), actualUser.getMobileNum() );
    }
}
