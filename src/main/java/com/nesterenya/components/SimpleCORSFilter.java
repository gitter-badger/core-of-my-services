package com.nesterenya.components;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    String vl = request.getHeader("Origin");

    response.setHeader("Access-Control-Allow-Credentials", "true");
    // TODO SECURITY  нормальный переключаетель для !!!
    response.setHeader("Access-Control-Allow-Origin", (vl!=null)?vl:"*" ); // *
    //response.setHeader("Access-Control-Allow-Origin", "http://arenda-online.by" ); // *
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    response.setHeader("Access-Control-Expose-Headers","X-Uid, X-Authentication, Authorization, WWW-Authenticate");

    chain.doFilter(req, res);
  }

  public void init(FilterConfig filterConfig) {

  }

  public void destroy() {}

}