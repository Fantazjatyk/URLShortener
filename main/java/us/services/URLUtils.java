/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLUtils {

    private URLUtils() {

    }

    public static URL getRelativeURL(URL base, String relative) {
        URL newURL = null;
        try {
            newURL = new URL(base, relative);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newURL;
    }

    public static URL appendToBase(HttpServletRequest rq, String relative) {
        URL base = URLUtils.getBaseURL(rq);
        return appendToURL(base, relative);
    }

    public static URL getBaseURL(HttpServletRequest rq) {
        URL base = null;
        URL newBase = null;
        try {
            base = new URL(rq.getRequestURL().toString());
            try {
                StringBuilder builder = new StringBuilder();
                builder.append(base.getProtocol());
                builder.append("://");
                builder.append(base.getHost());
                if (base.getPort() != -1) {
                    builder.append(":");
                    builder.append(base.getPort());
                }
                builder.append(rq.getContextPath());
                newBase = new URL(builder.toString());
            } catch (MalformedURLException ex2) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex2);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newBase;
    }

    public static URL appendToURL(URL base, String append) {
        URL newURL = null;
        String address = base.toString() + "/" + append;
        try {
            newURL = new URL(address);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newURL;
    }


}
