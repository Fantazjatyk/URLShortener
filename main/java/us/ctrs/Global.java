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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import us.dao.ReadRepository;
import us.exceptions.URLNotFoundException;
import us.services.SharedData;
import us.model.ShortURL;
import us.services.URLUtils;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
@RequestMapping(value = "/global", method = {RequestMethod.POST, RequestMethod.GET})
public class Global {

    @RequestMapping("/clicks")
    @ResponseBody
    public String getTotalClicks() {
        return String.valueOf(SharedData.getTotalClicks());
    }

    @RequestMapping("/urls")
    @ResponseBody
    public String getTotalUrls() {
        return String.valueOf(SharedData.getTotalUrls());
    }

    @RequestMapping(value = "/goto_analyze", method = RequestMethod.POST)
    public String gotoAnalyze(HttpServletRequest rq, @RequestParam String url) {
        String urlId;

        String withoutEnd = url.substring(url.lastIndexOf("/") + 1, url.length());
        if (url.contains("/")) {
            urlId = withoutEnd;
        } else {
            urlId = url;
        }

        if (!read.isURLExists(Utils.convertBase36ToNumber(urlId))) {
            throw new URLNotFoundException();
        }

        String redirectUrl = URLUtils.appendToBase(rq, "/analyze/" + urlId).toString();
        return "redirect:" + redirectUrl;
    }

    @Autowired
    ReadRepository read;

    @RequestMapping("/list")
    @ResponseBody
    public String getAllUrls(HttpServletRequest rq) {
        StringBuilder b = new StringBuilder();
        read.getAllURLs().stream()
                .sorted((e1, e2) -> e1.getCreationDate().compareTo(e2.getCreationDate()))
                .forEach(el -> b.append(getURLHTMLLink(rq, el)));
        return b.toString();
    }

    private String getURLHTMLLink(HttpServletRequest rq, ShortURL el) {
        String url = URLUtils.appendToBase(rq, "/analyze/" + el.getShorten()).toString();
        return "<a style='display:block' href=\"" + url + "\">" + el.getShorten() + "</a>";
    }

    @RequestMapping(value = "/list", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public List<ShortURL> getAllUrls(HttpServletRequest rq, Model model) {
        return read.getAllURLs().stream()
                .sorted((e1, e2) -> e1.getCreationDate().compareTo(e2.getCreationDate()))
                .peek(el -> el.setShorten(URLUtils.appendToBase(rq, el.getShorten()).toString()))
                .collect(Collectors.toList());

    }

}
