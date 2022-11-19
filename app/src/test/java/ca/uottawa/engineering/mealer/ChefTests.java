package ca.uottawa.engineering.mealer;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.Test;

import java.util.Date;

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefTests {

    @Test
    /**
     * Check if chef method isSuspended() is able to see if chef is indeed suspended.
     */
    public void isSuspendedTest() {
        String TAG = "chef-test-1";

        Chef chef = new Chef("Nickname", "Name", "email@address.com", "Address", "213451253", "Description");

        // Permanent suspension value
        chef.setSuspension(new Date(9900000000000L));
        assertEquals(true, chef.isSuspended());

        // Get current time
        chef.setSuspension(new Date());
        assertEquals(false, chef.isSuspended());

    }

}
