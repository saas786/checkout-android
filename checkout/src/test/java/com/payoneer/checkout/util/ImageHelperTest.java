package com.payoneer.checkout.util;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class ImageHelperTest {
    @Test
    public void getInstance() {
        assertNotNull(ImageHelper.getInstance());
    }
}