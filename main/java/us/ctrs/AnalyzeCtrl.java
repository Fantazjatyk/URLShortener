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
package us.ctrs;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import us.dao.ReadRepository;
import us.model.Click;
import us.model.ShortURL;
import us.services.URLUtils;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
@RequestMapping(value = "/analyze/{id}")
public class AnalyzeCtrl {

    @Autowired
    private ReadRepository read;

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
