/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class Utils {

    public static String convertNumberToBase36(BigInteger n) {
        return n.toString(Character.MAX_RADIX);
    }

    public static Long convertBase36ToNumber(String s) {
        return Long.parseLong(s, Character.MAX_RADIX);
    }

    private Utils() {

    }
}
