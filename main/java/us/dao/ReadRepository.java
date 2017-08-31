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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import us.dao.mapping.ClickMapper;
import us.dao.mapping.ShortURLMapper;
import us.model.Click;
import us.model.ShortURL;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Repository
public class ReadRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    ClickMapper clickMapper;

    @Autowired
    ShortURLMapper shortUrlMapper;

    public String getShortUrlOriginal(Long l) {
        String st = "select url from urls where id=" + l;
        String result = null;
        try {
            result = template.queryForObject(st, new HashMap(), String.class);
        } catch (EmptyResultDataAccessException e) {
            Logger.getGlobal().warning("FAILED REDIRECTION!!!\n" + e.getMessage());
            return null;
        }
        return result;
    }

    public List<Click> getUrlClicks(Long id) {
        String url = getShortUrlOriginal(id);
        String st = "select * from clicks "
                + "inner join clicks_details "
                + "inner join http_requests "
                + "on clicks.url = :url and clicks_details.id = clicks.detailsId and http_requests.id = clicks_details.http_requestsId";

        return template.query(st, new HashMap() {
            {
                put("url", url);
            }
        }, clickMapper);
    }

    public BigInteger getClicksCount(Long id) {
        String s = this.getShortUrlOriginal(id);
        String st = "select COUNT(*) from clicks where url = '" + s + "'";
        return template.queryForObject(st, new HashMap(), BigInteger.class);
    }

    public BigInteger getUniqueVisitorsCount(Long id) {
        String s = this.getShortUrlOriginal(id);
        String st = "select count(distinct ip) from clicks join clicks_details where clicks.url = '" + s + "'"
                + "and clicks.detailsId = clicks_details.id";
        return template.queryForObject(st, new HashMap(), BigInteger.class);
    }

    public Long getShortUrlId(String url) {
        String st = "select id from urls where url='" + url + "'";
        Long id;
        try {
            id = template.queryForObject(st, new HashMap(), Long.class);
        } catch (EmptyResultDataAccessException ex) {
            id = (long) 0;
        }
        return id;
    }

    public boolean isURLExists(String url) {
        Long count = template.queryForObject("select count(id) from urls where url ='" + url + "'", new HashMap(), Long.class);
        return count > 0;
    }

    public boolean isURLExists(long id) {
        Long count = template.queryForObject("select count(id) from urls where id =" + id + "", new HashMap(), Long.class);
        return count > 0;
    }

    public ShortURL getShortURL(long id) {
        if (!isURLExists(id)) {
            return null;
        }

        String statement = "select * from urls where id = :id";

        return template.queryForObject(statement, new HashMap() {
            {
                put("id", id);
            }
        }, shortUrlMapper);
    }

    public List<ShortURL> getAllURLs() {

        String statement = "select * from urls";

        return template.query(statement, new HashMap(), shortUrlMapper);
    }

}
