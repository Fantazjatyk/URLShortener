/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dao;

import java.math.BigInteger;
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
