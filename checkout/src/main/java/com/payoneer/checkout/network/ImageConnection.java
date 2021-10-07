package com.payoneer.checkout.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.payoneer.checkout.core.PaymentException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for loading images from the network
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class ImageConnection extends BaseConnection {

    /**
     * Construct a new ImageConnection with the empty constructor in super
     */
    public ImageConnection() {
    }

    /**
     * Load the Bitmap from the given URL
     *
     * @param url the pointing to the language entries
     * @return Bitmap drawable
     */
    public Bitmap loadBitmap(final URL url) throws PaymentException {
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        HttpURLConnection conn = null;
        try {
            conn = createGetConnection(url);

            try (InputStream in = conn.getInputStream()) {
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            throw createPaymentException(e, true);
        } finally {
            close(conn);
        }
    }
}
