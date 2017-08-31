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
package us.dao.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.RowMapper;
import sun.util.locale.LocaleUtils;
import us.model.Click;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Component
public class ClickMapper implements RowMapper<Click> {

    @Override
    public Click mapRow(ResultSet rs, int rowNum) throws SQLException {
        String url = rs.getString("url");

        Map<String, String> map = getHttpHeaders(rs);

        long id = rs.getLong("id");
        String acceptLanguage = map.get(HttpHeaders.ACCEPT_LANGUAGE);
        if (acceptLanguage == null) {
            acceptLanguage = map.get(HttpHeaders.ACCEPT_LANGUAGE.toLowerCase());
        }
        Locale l = getLocale(acceptLanguage);
        String time = getClickTime(rs);
        String ip = rs.getString("ip");
        String referer = getReferer(map);

        return new Click(referer, url, time, l, ip, id);
    }

    private String getReferer(Map<String, String> map) {
        String referer = null;
        referer = map.get(HttpHeaders.REFERER);

        if (referer == null || referer.isEmpty()) {
            referer = map.get(HttpHeaders.REFERER.toLowerCase());
        }
        return referer;
    }

    private String getClickTime(ResultSet rs) {

        String result = null;
        Timestamp ts = null;
        try {
            ts = rs.getTimestamp("time");
            ZoneId zone = ZoneId.systemDefault();
            Instant savedInstant = ts.toInstant();
            ZoneOffset offset = OffsetDateTime.now().getOffset();
            ZonedDateTime fixed = savedInstant.atOffset(offset).toZonedDateTime();

            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss").withZone(zone);
            result = f.format(fixed);
        } catch (SQLException ex) {
            Logger.getLogger(ClickMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Locale getLocale(String serialized) {
        if (serialized == null || serialized.isEmpty()) {
            return null;
        }
        String[] languages = serialized.split(";");
        String firstLanguage = languages[0];
        String[] variations;
        Locale result = null;

        if (firstLanguage.contains(",")) {
            variations = firstLanguage.split(",");

            for (int i = 0; result == null && i < variations.length; i++) {
                try {
                    result = org.apache.commons.lang.LocaleUtils.toLocale(variations[i]);
                } catch (IllegalArgumentException ex) {
                    result = null;
                }
            }

        } else {
            result = org.apache.commons.lang.LocaleUtils.toLocale(firstLanguage);
        }
        return result;

    }

    @Autowired
    ObjectMapper mapper;

    public Map getHttpHeaders(ResultSet s) {
        String serialized = null;
        try {
            serialized = s.getString("headers");
        } catch (SQLException ex) {
            Logger.getLogger(ClickMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        HashMap result = null;
        try {
            result = mapper.readValue(serialized, HashMap.class);
        } catch (IOException ex) {
            Logger.getLogger(ClickMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
