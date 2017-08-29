/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.model;

import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ShortURL {

    private String original;
    private String shorten;
    private List<Click> clicks;
    private Date creationDate;

    public ShortURL(String url) {
        this.original = url;
    }

    public void setShorten(String shorten) {
        this.shorten = shorten;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationTimestamp) {
        this.creationDate = Date.from(creationTimestamp.toInstant());
    }

    public String getOriginal() {
        return original;
    }

    public String getShorten() {
        return shorten;
    }

    public List<Click> getClicks() {
        return clicks;
    }

    public void setClicks(List<Click> clicks) {
        this.clicks = clicks;
    }

}
