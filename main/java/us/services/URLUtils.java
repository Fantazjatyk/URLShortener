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
