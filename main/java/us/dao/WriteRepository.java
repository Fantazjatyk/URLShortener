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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import us.model.ShortURL;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class WriteRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    DataSource ds;

    @Autowired
    ReadRepository read;

    @Autowired
    ObjectMapper mapper;

    private SimpleJdbcInsert insertClickDetails;
    private SimpleJdbcInsert insertHttpHeaders;
    private SimpleJdbcInsert insertShortUrl;

    @PostConstruct
    public void setUp() {
        insertClickDetails = new SimpleJdbcInsert(ds);
        insertHttpHeaders = new SimpleJdbcInsert(ds);
        insertShortUrl = new SimpleJdbcInsert(ds);

        insertHttpHeaders.withTableName("http_requests");
        insertHttpHeaders.usingColumns("headers");
        insertHttpHeaders.usingGeneratedKeyColumns("id");
        insertHttpHeaders.compile();

        insertClickDetails.withTableName("clicks_details");
        insertClickDetails.usingColumns("time", "http_requestsId", "ip");
        insertClickDetails.usingGeneratedKeyColumns("id");
        insertClickDetails.compile();

        insertShortUrl.withTableName("urls");
        insertShortUrl.usingColumns("url");
        insertShortUrl.usingGeneratedKeyColumns("id");
        insertShortUrl.compile();
    }

    public int saveClickData(Long urlId, HttpServletRequest rq) {

        String url = read.getShortUrlOriginal(urlId);
        String st = "insert into clicks(url, detailsId) values (:url, :detailsId)";
        BigInteger detailsId = insertClickDetailsAndReturnId(rq);
        HashMap params = new HashMap();
        params.put("url", url);
        params.put("detailsId", detailsId);
        return template.update(st, params);
    }

    private String getClientIP(HttpServletRequest rq) {
        String ip;
        ip = rq.getHeader("X-FORWARDED-FOR"); // in case if there is something like redirection, etc.

        if (ip == null || ip.isEmpty()) {
            ip = rq.getRemoteAddr();
        }
        return ip;
    }

    private BigInteger insertClickDetailsAndReturnId(HttpServletRequest rq) {
        Timestamp tp = new Timestamp(System.currentTimeMillis());
        BigInteger httpRequestsId = insertHttpHeadersAndReturnKey(rq);

        HashMap params = new HashMap();
        params.put("time", tp);
        params.put("ip", getClientIP(rq));
        params.put("http_requestsId", httpRequestsId);

        return (BigInteger) insertClickDetails.executeAndReturnKey(params);
    }

    private BigInteger insertHttpHeadersAndReturnKey(HttpServletRequest rq) {
        return (BigInteger) insertHttpHeaders.executeAndReturnKey(new HashMap() {
            {
                put("headers", getSerializedHttpHeaders(rq));
            }
        });
    }

    private static List<String> acceptableHeaders = Arrays.asList(new String[]{
        HttpHeaders.ACCEPT.toLowerCase(),
        HttpHeaders.ACCEPT_ENCODING.toLowerCase(),
        HttpHeaders.ACCEPT_LANGUAGE.toLowerCase(),
        HttpHeaders.ACCEPT_CHARSET.toLowerCase(),
        HttpHeaders.REFERER.toLowerCase(),
        HttpHeaders.USER_AGENT.toLowerCase(),
        "X-Forwarded-For".toLowerCase()});

    private String getSerializedHttpHeaders(HttpServletRequest rq) {
        String result = null;
        Map<String, String> map = mapHttpHeaders(rq);
        try {
            result = mapper.writeValueAsString(map);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(WriteRepository.class.getName()).warning(ex.getMessage());
        }
        return result;
    }

    private Map<String, String> mapHttpHeaders(HttpServletRequest rq) {
        Map map = new HashMap();

        Enumeration< String> h = rq.getHeaderNames();
        while (h.hasMoreElements()) {
            String header = h.nextElement();
            String headerContent = rq.getHeader(header);
            if (acceptableHeaders.contains(header.toLowerCase()) && headerContent.length() < 500) {
                map.put(header, headerContent);
            }
        }
        return map;
    }

    public BigInteger addShortURL(String url) {
        MapSqlParameterSource sr = new MapSqlParameterSource();

        sr.addValue("url", url);
        return (BigInteger) insertShortUrl.executeAndReturnKey(sr);
    }

}
