/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dao;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.Conf;
import us.model.Click;
import us.model.ShortURL;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
@Transactional
public class ReadRepositoryTest {

    public ReadRepositoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    String url = "http://www.google.pl";
    BigInteger urlId;

    @Before
    public void setUp() {
        urlId = write.addShortURL(url);
        mockClickOnUrl();
        mockClickOnUrl();
        mockClickOnUrl();
    }

    private void mockClickOnUrl() {
        Vector v = new Vector();
        v.add("referer");

        HttpServletRequest rq = Mockito.mock(HttpServletRequest.class);
        Mockito.when(rq.getHeaderNames()).thenReturn(v.elements());
        Mockito.when(rq.getHeader("referer")).thenReturn("http://www.google.pl");
        write.saveClickData(urlId.longValue(), rq);
    }

    @Autowired
    WriteRepository write;

    @Autowired
    ReadRepository read;

    /**
     * Test of getShortUrlOriginal method, of class ReadRepository.
     */
    @Test
    public void testGetShortUrlOriginal() {
        assertEquals(url, read.getShortUrlOriginal(urlId.longValue()));

    }

    /**
     * Test of getUrlClicks method, of class ReadRepository.
     */
    @Test
    public void testGetUrlClicks() {
        List<Click> click = read.getUrlClicks(urlId.longValue());
        assertFalse(click.isEmpty());
        assertTrue(click.stream().allMatch(el -> el != null));
        Click c = click.get(0);
        assertEquals(c.getUrl(), url);
        assertNotNull(c.getTime());
        assertNotNull(c.getId());
    }

    /**
     * Test of getTotalUrlsCount method, of class Read.
     */
    /**
     * Test of getClicksCount method, of class ReadRepository.
     */
    @Test
    public void testGetClicksCount_FALSE() {
        assertNotEquals(1, read.getClicksCount(urlId.longValue()).intValue());
    }

    @Test
    public void testGetClicksCount() {
        assertEquals(3, read.getClicksCount(urlId.longValue()).intValue());
    }

    /**
     * Test of getUniqueVisitorsCount method, of class Read.
     */
    /**
     * Test of getShortUrlId method, of class ReadRepository.
     */
    @Test
    public void testGetShortUrlId() {
        Long id = read.getShortUrlId(url);
        assertTrue(id > 0);
    }

    /**
     * Test of isURLExists method, of class ReadRepository.
     */
    @Test
    public void testIsURLExists_String() {
        assertTrue(read.isURLExists(url));
    }

    /**
     * Test of isURLExists method, of class ReadRepository.
     */
    @Test
    public void testIsURLExists_long() {
        assertTrue(read.isURLExists(urlId.longValue()));
    }

    /**
     * Test of getShortURL method, of class ReadRepository.
     */
    @Test
    public void testGetShortURL() {
        ShortURL sUrl = read.getShortURL(urlId.longValue());
        assertNotNull(sUrl);
        assertNotNull(sUrl.getCreationDate());
        assertEquals(sUrl.getOriginal(), url);
    }

    /**
     * Test of getAllURLs method, of class ReadRepository.
     */
    @Test
    public void testGetAllURLs() {
        List<ShortURL> urls = read.getAllURLs();
        assertFalse(urls.isEmpty());
        assertTrue(urls.stream().allMatch(el -> el != null));
    }

}
