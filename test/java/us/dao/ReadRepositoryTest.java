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
