/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.validation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class HolderNameValidatorTest {

    private static final String HOLDER_NAME_DATA_PROVIDER = "holder-name-data-provider";

    private static final String GUINESS_LONGEST_NAME =
        "Adolph Blaine Charles David Earl Frederick Gerald Hubert Irvin John Kenneth Lloyd Martin Nero Oliver Paul Quincy Randolph Sherman Thomas Uncas Victor William Xerxes Yancy Zeus Wolfeschlegelsteinhausenbergerdorffwelchevoralternwarengewissenhaftschaferswessenschafewarenwohlgepflegeundsorgfaltigkeitbeschutzenvonangreifendurchihrraubgierigfeindewelchevoralternzwolftausendjahresvorandieerscheinenvanderersteerdemenschderraumschiffgebrauchlichtalsseinursprungvonkraftgestartseinlangefahrthinzwischensternartigraumaufdersuchenachdiesternwelchegehabtbewohnbarplanetenkreisedrehensichundwohinderneurassevonverstandigmenschlichkeitkonntefortpflanzenundsicherfreuenanlebenslanglichfreudeundruhemitnichteinfurchtvorangreifenvonandererintelligentgeschopfsvonhinzwischensternartigraum.";

    private static final String VERY_LONG_VALID_HOLDER_NAME_INPUT =
        "         ---- Aasdfal345-345.4th 3wrsdjf 123 Oijajsdf908 Asdf 12345 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 12.345 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 123-45 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1-2345 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234-5 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234 Aäsdfölkëdjf 478123 Oijajsdf908 Asdfgjwopi23456789            ";

    private static final String VERY_LONG_INVALID_HOLDER_NAME_INPUT =
        "Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234 5678 9012 34 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234567890 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 12345678 Aäsdfölkëdjf 1231234123412342134 Oijajsdf908 Asdf 123456780 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234567890 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234567890 Aäsdfölkëdjf 123 Oijajsdf908 Asdf 1234567890";

    @Test
    @UseDataProvider("holderNameDataProvider")
    public void isValidHolderName(final String input, final boolean expectedValid) {
        boolean result = HolderNameValidator.isValidHolderName(input);
        assertEquals(result, expectedValid);
    }

    @DataProvider
    public static Object[][] holderNameDataProvider() {
        return new Object[][] {
            // test input, is the test input valid
            { null, false },
            { "", false },
            { "1", false },
            { "12", false },
            { "FirstName LastName", true },
            { "FirstName MiddleName LastName", true },
            { "FirstName III MiddleName LastName", true },
            { "FirstName 3rd MiddleName LastName", true },
            { "FirstName SecondName ThirdName FourthName von LastName", true },
            { GUINESS_LONGEST_NAME, true },
            { "John the 8th------------", true },
            { "John the -.- -.-8th", true },
            { "1          a", true },
            { "1..........a", true },
            { "1----------a", true },
            { "1-2-3-4-5-6a", true },
            { "1-2-3-4-5-6-7-8-9-0", true },
            { "1-2-3-4-5-6-7-8-9-0 ", true },
            { " 1-2-3-4-5-6-7-8-9-0", true },
            { " 1234-5678-90 1", false },
            { " 1234-5678-90    1", false },
            { "123", true },
            { "1234", true },
            { "123456 ", true },
            { "123456.", true },
            { "123456-", true },
            { "1234567", true },
            { "123-4567", true },
            { "1234 567", true },
            { "12345.67", true },
            { "123456789", true },
            { "1234567890", true },
            { "12345678901", false },
            { "123456789012", false },
            { "1234567890123", false },
            { "12345678901234", false },
            { "123456789012345", false },
            { "1234567890123456", false },
            { "12345678901234567", false },
            { "123456789012345678", false },
            { "1234567890123456789", false },
            { "12345678901234567890", false },
            { "123456789012345678901", false },
            { "12345678901a", false },
            { "12345678901aäsdfa", false },
            { "a12345678901a", false },
            { "asdfasdf12345678901", false },
            { "aäfasdf12345678901aäfasdfas", false },
            { "aäfasdf 1234567890 aäfasdfas", true },
            { "aäfasdf 12345678901 aäfasdfas", false },
            { "aäfasdf 1234 5678 901 aäfasdfas", false },
            { "aäfasdf a1234 5678 9014 1235x öasdfüsdfas", false },
            { "aäfasdf 1234 5678 9014 1235 öasdfüsdfas", false },
            { "aäfasdf 1234-5678-9014-1235 öasdfüsdfas", false },
            { "aäfasdf 1234.5678.9014.1235 öasdfüsdfas", false },
            { "aäfasdff1234567890141235 öasdfüsdfas", false },
            { "aäfasdf1234 5678 9014 1235 öasdfüsdfas", false },
            { "aäfasdf1234-5678-9014-1235 öasdfüsdfas", false },
            { "aäfasdf1234.5678.9014.1235 öasdfüsdfas", false },
            { "aäfasdf 1234567890141235öasdfüsdfas", false },
            { "aäfasdf 1234 5678 9014 1235öasdfüsdfas", false },
            { "aäfasdf 1234-5678-9014-1235öasdfüsdfas", false },
            { "aäfasdf 1234.5678.9014.1235öasdfüsdfas", false },
            { VERY_LONG_VALID_HOLDER_NAME_INPUT, true },
            { VERY_LONG_INVALID_HOLDER_NAME_INPUT, false },
        };
    }
}
