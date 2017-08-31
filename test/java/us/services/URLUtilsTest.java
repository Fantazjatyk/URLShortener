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
import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLUtilsTest {

    @Test
    public void testGetRelativeURL() throws MalformedURLException {
        String url = "http://michal-szymanski.pl/index";
        String expected = "http://michal-szymanski.pl/page";
        String result = URLUtils.getRelativeURL(new URL(url), "page").toString();
        assertEquals(expected, result);
    }

    @Test
    public void testAppendToBase() {
        HttpServletRequest rq = Mockito.mock(HttpServletRequest.class);
        String expected = "http://michal-szymanski.pl/page";
        StringBuffer mockReturn = new StringBuffer();
        String url = "http://michal-szymanski.pl/index";
        mockReturn.append(url);
        Mockito.when(rq.getContextPath()).thenReturn("");
        Mockito.when(rq.getRequestURL()).thenReturn(mockReturn);
        String result = URLUtils.appendToBase(rq, "page").toString();
        assertEquals(expected, result);
    }

    @Test
    public void testAppendToURL() throws MalformedURLException {
        String url = "http://michal-szymanski.pl/index";
        String expected = "http://michal-szymanski.pl/index/page";
        String result = URLUtils.appendToURL(new URL(url), "page").toString();
        assertEquals(expected, result);
    }

    @Test
    public void testAppendToURL_DuplicateSlashes() throws MalformedURLException {
        String url = "http://michal-szymanski.pl/index";
        String expected = "http://michal-szymanski.pl/index/page";

        String result = URLUtils.appendToURL(new URL(url), "/page").toString();
        assertNotEquals(expected, result);
    }

    @Test
    public void testGetBaseURL() {
        HttpServletRequest rq = Mockito.mock(HttpServletRequest.class);
        String url = "http://michal-szymanski.pl/index";
        String expectedUrl = "http://michal-szymanski.pl";
        StringBuffer mockReturn = new StringBuffer();
        mockReturn.append(url);
        Mockito.when(rq.getContextPath()).thenReturn("");
        Mockito.when(rq.getRequestURL()).thenReturn(mockReturn);

        URL baseURL = URLUtils.getBaseURL(rq);
        assertEquals(expectedUrl, baseURL.toString());
    }
}
