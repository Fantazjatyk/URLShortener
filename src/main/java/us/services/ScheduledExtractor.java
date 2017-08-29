/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.dao.GlobalRepository;
import us.dao.ReadRepository;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Component
public class ScheduledExtractor {

    @Autowired
    GlobalRepository global;

    @Scheduled(fixedRate = 15 * 1000)
    public void updateCounters() {
        long clicks = global.getTotalClicksCount();
        long urls = global.getTotalUrlsCount();

        SharedData.setTotalClicks(clicks);
        SharedData.setTotalUrls(urls);
    }
}
