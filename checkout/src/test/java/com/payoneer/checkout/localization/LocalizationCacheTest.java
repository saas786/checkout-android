/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.localization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LocalizationCacheTest {

    @Test
    public void setCacheId() {
        LocalizationCache cache = new LocalizationCache();
        String cacheId = "cacheId";
        cache.setCacheId(cacheId);
        assertEquals(cacheId, cache.getCacheId());
    }

    @Test
    public void clear() {
        LocalizationCache cache = new LocalizationCache();
        String cacheId = "cacheId";
        cache.setCacheId(cacheId);

        LocalizationHolder holder = LocalizationTest.createMapLocalizationHolder("key", "value", 5);
        String holderKey = "holder";
        cache.put(holderKey, holder);

        assertEquals(cacheId, cache.getCacheId());
        assertNotNull(cache.get(holderKey));

        cache.clear();
        assertNull(cache.getCacheId());
        assertNull(cache.get(holderKey));
    }
}
