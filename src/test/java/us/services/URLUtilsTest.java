/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLUtilsTest {

    public URLUtilsTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of getRelativeURL method, of class URLUtils.
     */
    @Test
    public void testGetRelativeURL() throws MalformedURLException {
        String url = "http://michal-szymanski.pl/index";
        String expected = "http://michal-szymanski.pl/page";
        String result = URLUtils.getRelativeURL(new URL(url), "page").toString();
        assertEquals(expected, result);
    }

    /**
     * Test of appendToBase method, of class URLUtils.
     */
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

    /**
     * Test of getBaseURL method, of class URLUtils.
     */
    /**
     * Test of appendToURL method, of class URLUtils.
     */
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
