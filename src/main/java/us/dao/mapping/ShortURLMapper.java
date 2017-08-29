/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
