/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SharedData {

    private static AtomicLong totalClicks = new AtomicLong();
    private static AtomicLong totalUrls = new AtomicLong();

    private SharedData() {

    }

    public static synchronized long getTotalClicks() {
        return totalClicks.get();
    }

    public static synchronized long getTotalUrls() {
        return totalUrls.get();
    }

    static void setTotalClicks(long value) {
        SharedData.totalClicks.set(value);
    }

    static void setTotalUrls(long value) {
        SharedData.totalUrls.set(value);
    }
}
