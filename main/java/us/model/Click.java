/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class Click {

    private String referer;
    private String url;
    private long id;
    private String ip;
    private String time;
    private Locale locale;

    public Click(String referer, String url, String time, Locale locale, String ip, long id) {
        this.referer = referer;
        this.url = url;
        this.time = time;
        this.locale = locale;
        this.ip = ip;
    }

    public String getReferer() {
        return referer;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Locale getLocale() {
        return locale;
    }

}
