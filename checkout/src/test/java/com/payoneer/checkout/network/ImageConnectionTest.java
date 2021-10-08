package com.payoneer.checkout.network;

import com.payoneer.checkout.core.PaymentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Containing ImageConnection tests
 */
@RunWith(RobolectricTestRunner.class)
public class ImageConnectionTest {

    /**
     * Create ImageConnection and loadBitmap with invalid url
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadBitmap_invalidUrl_IllegalArgumentException() throws PaymentException {
        ImageConnection conn = new ImageConnection();
        conn.loadBitmap(null);
    }
}