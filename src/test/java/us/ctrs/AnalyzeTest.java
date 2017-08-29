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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import us.Conf;
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
public class AnalyzeTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    WriteRepository write;

    BigInteger urlId;

    String url = "http://www.google.pl/" + System.currentTimeMillis();

    public AnalyzeTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        urlId = urlId = write.addShortURL(url);
    }

    /**
     * Test of analyze method, of class Analyze.
     */
    @Test
    public void testAnalyze_BadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/global/goto_analyze"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testAnalyze() throws Exception {
        String request = "/global/goto_analyze?url=" + System.currentTimeMillis();
        mvc.perform(MockMvcRequestBuilders.post(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAnalyze_FAIL() throws Exception {
        String base36 = Utils.convertNumberToBase36(urlId);
        String request = "/global/goto_analyze?url=" + base36;
        mvc.perform(MockMvcRequestBuilders.post(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
