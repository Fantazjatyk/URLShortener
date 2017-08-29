/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dao;

import java.math.BigInteger;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Component
public class GlobalRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    public long getTotalUrlsCount() {
        String st = "select count(id) from urls";
        BigInteger result = template.queryForObject(st, new HashMap(), BigInteger.class);
        return result.longValue();
    }

    public long getTotalClicksCount() {
        String st = "select count(id) from clicks";
        BigInteger result = template.queryForObject(st, new HashMap(), BigInteger.class);
        return result.longValue();
    }
}
