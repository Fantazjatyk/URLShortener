/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import static us.ctrs.AnalyzeCtrl.CTRL_MAPPING;
import us.dao.WriteRepository;
import us.dao.ReadRepository;
import us.exceptions.URLNotFoundException;
import us.model.Click;
import us.model.ShortURL;
import us.services.URLUtils;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
@RequestMapping(value = CTRL_MAPPING)
public class AnalyzeCtrl {

    @Autowired
    private ReadRepository read;

    @Autowired
    private WriteRepository create;

    public static final String CTRL_MAPPING = "/analyze/{id}";

    @RequestMapping("")
    public String info(Model model, @PathVariable("id") String id, HttpServletResponse rs, HttpServletRequest rq) throws IOException {
        if (!read.isURLExists(Utils.convertBase36ToNumber(id))) {
            rs.sendError(405);
        }
        URL fullURL = URLUtils.appendToBase(rq, id);
        model.addAttribute("shortFullUrl", fullURL);
        return "urlinfo";
    }

    @RequestMapping("/history")
    public String history(@PathVariable String id, Model model, @ModelAttribute("sUrlId") Long sUrlId) {
        List<Click> clicks = read.getUrlClicks(sUrlId);
        model.addAttribute("clicks", clicks);
        return "history";
    }

    @ModelAttribute(name = "sUrlId")
    public Long appendShortUrlId(@PathVariable("id") String id) {
        return Utils.convertBase36ToNumber(id);
    }

    @RequestMapping("/overview")
    public String overview(@PathVariable String id, Model model, HttpServletRequest rq, @ModelAttribute("sUrlId") Long sUrlId) {

        ShortURL shortened = read.getShortURL(sUrlId);
        URL fullURL = URLUtils.appendToBase(rq, id);
        model.addAttribute("shortUrl", shortened);
        model.addAttribute("shortFullUrl", fullURL);
        model.addAttribute("clicksCount", read.getClicksCount(sUrlId));
        model.addAttribute("uniqueVisitors", read.getUniqueVisitorsCount(sUrlId));
        return "overview";
    }

}
