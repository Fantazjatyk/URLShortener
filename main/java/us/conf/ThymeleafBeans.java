/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.conf;

import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Configuration
public class ThymeleafBeans implements ApplicationContextAware {

    ApplicationContext ctx;

    @Bean
    public ThymeleafViewResolver resolver() {
        ThymeleafViewResolver r = new ThymeleafViewResolver();
        // r.setStaticVariables(variables);
        r.setTemplateEngine(engine());
        r.setCharacterEncoding(CharEncoding.UTF_8);
        return r;
    }

    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver r = new SpringResourceTemplateResolver();
        r.setApplicationContext(ctx);
        r.setPrefix("/WEB-INF/templates/");
        r.setSuffix(".html");
        r.setTemplateMode(TemplateMode.HTML);
        r.setCharacterEncoding(CharEncoding.UTF_8);
        return r;
    }

    public SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
