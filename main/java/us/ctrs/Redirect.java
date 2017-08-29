/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.ctrs;

import java.text.MessageFormat;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.dao.WriteRepository;
import us.dao.ReadRepository;
import us.exceptions.URLNotFoundException;
import us.model.ShortURL;
import us.services.Utils;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Controller
public class Redirect {

    @Autowired
    WriteRepository create;

    @Autowired
    ReadRepository read;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String redirect(@PathVariable("id") String id, HttpServletRequest rq) {

        Long l = Utils.convertBase36ToNumber(id);
        String original = null;
        original = read.getShortUrlOriginal(l);

        if (original == null) {
            return "redirect:http://www.us.michal-szymanski.pl/";
        }
        try {
            create.saveClickData(l, rq);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).warning(e.getMessage());
        } finally {
            return MessageFormat.format("redirect: {0}", original);
        }

    }

}
