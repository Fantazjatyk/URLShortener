/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
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

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
@Transactional
public class ShortenerTest {

    public ShortenerTest() {
    }

    MockMvc mvc;

    @Autowired
    WebApplicationContext ctx;

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    /**
     * Test of shorten method, of class Shortener.
     */
    @Test
    public void testShorten_FAIL_REDIRECT() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shorten?url=someurl")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    public void testShorten_Fail_BadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/shorten")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testShorten() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/shorten?url=blablabla")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(org.springframework.http.MediaType.TEXT_PLAIN));
    }

}
