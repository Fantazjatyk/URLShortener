/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.Conf;
import us.conf.CommonBeans;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
@Transactional
public class WriteRepositoryTest {

    public WriteRepositoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of setUp method, of class WriteRepository.
     */
    @Test
    public void testSetUp() {
    }

    /**
     * Test of saveClickData method, of class WriteRepository.
     */
    @Test
    public void testSaveClickData() {
    }

    /**
     * Test of addShortURL method, of class WriteRepository.
     */
    @Autowired
    WriteRepository write;

    @Test
    public void testAddShortURL() {

        BigInteger id = write.addShortURL("https://stackoverflow.com");
        assertTrue(id.intValue() > 0);
    }

    @Test
    public void testAddShortURLWithDetails() {

        BigInteger id = write.addShortURL("https://stackoverflow.com");
        Vector v = new Vector();
        v.add("referer");
        HttpServletRequest rq = Mockito.mock(HttpServletRequest.class);
        Mockito.when(rq.getHeaderNames()).thenReturn(v.elements());
        Mockito.when(rq.getHeader("referer")).thenReturn("http://www.google.pl");
        int rowsAffected = write.saveClickData(id.longValue(), rq);

        assertTrue(id.intValue() > 0);
        assertTrue(rowsAffected > 0);
    }

}
