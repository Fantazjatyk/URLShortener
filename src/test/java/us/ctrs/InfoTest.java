/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import java.math.BigInteger;
import javax.transaction.Transactional;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import us.Conf;
import us.dao.ReadRepository;
import us.dao.WriteRepository;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
@Transactional
public class InfoTest {

    public InfoTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Autowired
    WriteRepository write;

    BigInteger urlId;

    MockMvc mvc;

    @Autowired
    WebApplicationContext ctx;

    String url = "http://www.google.pl/" + System.currentTimeMillis();
    String shortenUrl;

    @Before
    public void setUp() {

        urlId = write.addShortURL(url);
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();

    }

    /**
     * Test of analyze method, of class Info.
     */
    @Test
    public void testInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/analyze/" + Utils.convertNumberToBase36(urlId) + "/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("urlinfo"));
    }

    /**
     * Test of history method, of class Info.
     */
    @Test
    public void testHistory() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/analyze/" + Utils.convertNumberToBase36(urlId) + "/history"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("history"));
    }

    /**
     * Test of appendShortUrlId method, of class Info.
     */
    /**
     * Test of overview method, of class Info.
     */
    @Test
    public void testOverview() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/analyze/" + Utils.convertNumberToBase36(urlId) + "/overview"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("overview"));
    }

}
