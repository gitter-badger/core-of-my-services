package com.nesterenya.components;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CrawlServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private String rewriteQueryString(String url_with_escaped_fragment) {
        //http://localhost:8080/#/rent/1
        //return "http://localhost:8080/#/main";
        //http://localhost:8080/?_escaped_fragment_=main
        return url_with_escaped_fragment.replace("_escaped_fragment_=","#/");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException {

        String url = ((HttpServletRequest)request).getRequestURL().toString();
        String queryString = ((HttpServletRequest)request).getQueryString();

        if ((queryString != null) && (queryString.contains("_escaped_fragment_"))) {

            // rewrite the URL back to the original #! version
            // remember to unescape any %XX characters
            String url_with_hash_fragment = url + rewriteQueryString(queryString);
            System.out.println(url_with_hash_fragment);
            // use the headless browser to obtain an HTML snapshot
            final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);

            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.setCssErrorHandler(new SilentCssErrorHandler());

            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setRedirectEnabled(false);
            webClient.getOptions().setAppletEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setPopupBlockerEnabled(true);
            webClient.getOptions().setTimeout(10000);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(true);

            HtmlPage page = webClient.getPage(url_with_hash_fragment);

            // important!  Give the headless browser enough time to execute JavaScript
            // The exact time to wait may depend on your application.
            webClient.waitForBackgroundJavaScript(5000);

            // return the snapshot
            //response.setContentType("application/xml");
            //response.setContentType("text/html");
            response.setCharacterEncoding( "UTF-8" );
            response.getWriter().println(page.asXml());

        } else {
            try {
                // not an _escaped_fragment_ URL, so move up the chain of servlet (filters)
                chain.doFilter(request, response);
            } catch (ServletException e) {
                System.err.println("Servlet exception caught: " + e);
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroy() {

    }
}