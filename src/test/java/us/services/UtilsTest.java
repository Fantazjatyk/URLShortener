/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import java.math.BigInteger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class UtilsTest {

    public UtilsTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of convertNumberToBase36 method, of class Utils.
     */
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
