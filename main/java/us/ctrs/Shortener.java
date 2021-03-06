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

import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import us.dao.ReadRepository;
import us.dao.WriteRepository;
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
