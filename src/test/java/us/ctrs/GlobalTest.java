/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import us.Conf;
import us.dao.ReadRepository;
import us.model.ShortURL;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@Transactional
@WebAppConfiguration
public class GlobalTest {

    public GlobalTest() {
    }

    MockMvc mvc;

    @AfterClass
    public static void tearDownClass() {
    }

    @Autowired
    WebApplicationContext ctx;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    /**
     * Test of getTotalClicks method, of class Global.
     */
    @Test
    public void testGetTotalClicks() throws Exception {

        MvcResult result = mvc.perform(get("/global/clicks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andReturn();
        String strResult = result.getResponse().getContentAsString();
        assertNotNull(strResult);
        long longResult = Long.parseLong(strResult);
        assertTrue(longResult > 0);
        System.out.println(strResult);

    }

    /**
     * Test of getTotalUrls method, of class Global.
     */
    @Test
    public void testGetTotalUrls() throws Exception {

        MvcResult result = mvc.perform(get("/global/urls"))
                .andExpect(status().isOk())
                .andReturn();
        String strResult = result.getResponse().getContentAsString();
        long longResult = Long.parseLong(strResult);
        assertTrue(longResult > 0);
    }

    /**
     * Test of getAllUrls method, of class Global.
     */
    @Autowired
    ObjectMapper mapper;

    @Autowired
    ReadRepository read;

    @Test
    public void testGetAllUrls() throws Exception {
        MvcResult result = mvc.perform(get("/global/list").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String strResult = result.getResponse().getContentAsString();
        List< ShortURL> list = mapper.readValue(strResult, List.class);

        assumeTrue(!read.getAllURLs().isEmpty());
        assertFalse(list.isEmpty());

    }

    /**
     *
     */
}
