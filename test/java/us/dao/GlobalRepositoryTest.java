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
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.Conf;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
@Transactional
public class GlobalRepositoryTest {

    public GlobalRepositoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Autowired
    WriteRepository write;

    BigInteger url1Id;
    BigInteger url2Id;

    @Autowired
    GlobalRepository global;

    @Before
    public void setUp() {
        url1Id = write.addShortURL("http://www.google.pl");
        url2Id = write.addShortURL("http://www.google.pl");
        mockClick(url1Id);
        mockClick(url1Id);
        mockClick(url2Id);
        mockClick(url2Id);
        mockClick(url2Id);
    }

    private void mockClick(BigInteger id) {
        HttpServletRequest rq = Mockito.mock(HttpServletRequest.class);
        Mockito.when(rq.getHeaderNames()).thenReturn(new Vector().elements());
        write.saveClickData(id.longValue(), rq);
    }

    /**
     * Test of getTotalUrlsCount method, of class GlobalRepository.
     */
    @Test
    public void testGetTotalUrlsCount() {
        assertTrue(global.getTotalClicksCount() > 5);
    }

    /**
     * Test of getTotalClicksCount method, of class GlobalRepository.
     */
    @Test
    public void testGetTotalClicksCount() {
        assertTrue(global.getTotalUrlsCount() > 2);
    }

}
