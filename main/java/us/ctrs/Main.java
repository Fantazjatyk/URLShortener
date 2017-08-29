/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import us.services.SharedData;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
public class Main {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("totalClicks", SharedData.getTotalClicks());
        model.addAttribute("totalUrls", SharedData.getTotalUrls());
        return "main";
    }
}
