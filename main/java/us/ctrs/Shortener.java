/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import us.dao.WriteRepository;
import us.dao.ReadRepository;
import us.model.ShortURL;
import us.services.URLUtils;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
public class Shortener {

    @Autowired
    WriteRepository create;

    @Autowired
    ReadRepository read;

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity shorten(@RequestParam String url, HttpServletRequest rq) {
        if (url.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (read.isURLExists(url)) {
            Long urlId = read.getShortUrlId(url);
            return ResponseEntity.ok(URLUtils.appendToBase(rq,
                    Utils.convertNumberToBase36(BigInteger.valueOf(urlId))).toString());

        }
        BigInteger id = create.addShortURL(url);
        return ResponseEntity.ok(URLUtils.appendToBase(rq, Utils.convertNumberToBase36(id)).toString());

    }

}
