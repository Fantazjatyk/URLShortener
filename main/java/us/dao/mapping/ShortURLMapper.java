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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.model.ShortURL;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Component
public class ShortURLMapper implements RowMapper<ShortURL> {

    Optional<Function<ResultSet, String>> shortenedResolver = Optional.empty();

    @Override
    public ShortURL mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShortURL url = new ShortURL(rs.getString("url"));

        String shorten;
        if (this.shortenedResolver.isPresent()) {
            shorten = shortenedResolver.get().apply(rs);
        } else {
            shorten = Utils.convertNumberToBase36(BigInteger.valueOf(rs.getLong("id")));
        }
        url.setShorten(shorten);
        url.setCreationDate(rs.getTimestamp("creation_date"));
        return url;
    }

    public void setShortenedResolver(Function<ResultSet, String> func) {
        this.shortenedResolver = Optional.of(func);
    }

}
