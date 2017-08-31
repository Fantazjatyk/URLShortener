/*
 * The MIT License
 *
 * Copyright 2017 Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package us.services;

import java.math.BigInteger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class UtilsTest {

    @Test
    public void testConvertNumberToBase36() {
        BigInteger number1 = BigInteger.valueOf(1226);
        BigInteger number2 = BigInteger.valueOf(36053);
        BigInteger number3 = BigInteger.valueOf(401391);
        String result1 = "y2";
        String result2 = "rth";
        String result3 = "8lpr";
        assertEquals(result1, Utils.convertNumberToBase36(number1));
        assertEquals(result2, Utils.convertNumberToBase36(number2));
        assertEquals(result3, Utils.convertNumberToBase36(number3));
    }

    /**
     * Test of convertBase36ToNumber method, of class Utils.
     */
    @Test
    public void testConvertBase36ToNumber() {
        BigInteger number1 = BigInteger.valueOf(1226);
        BigInteger number2 = BigInteger.valueOf(36053);
        BigInteger number3 = BigInteger.valueOf(401391);
        String string1 = "y2";
        String string2 = "rth";
        String string3 = "8lpr";
        assertEquals(number1.intValue(), Utils.convertBase36ToNumber(string1).intValue());
        assertEquals(number2.intValue(), Utils.convertBase36ToNumber(string2).intValue());
        assertEquals(number3.intValue(), Utils.convertBase36ToNumber(string3).intValue());
    }

}
