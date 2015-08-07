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


//
//  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//    HttpServletResponse response = (HttpServletResponse) res;
//    HttpServletRequest request = (HttpServletRequest) req;
//    response.setHeader("Access-Control-Allow-Origin", "*");
//    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//    response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
//    response.setHeader("Access-Control-Max-Age", "3600");
//
//    chain.doFilter(req, res);
//    /*if (request.getMethod()!= "OPTIONS") {
//      chain.doFilter(req, res);
//    } else {
//    }*/
//
//    /*HttpServletResponse response = (HttpServletResponse) res;
//    response.setHeader("Access-Control-Allow-Origin", "*");
//    response.setHeader("Access-Control-Allow-Methods", "POST,PUT, GET, OPTIONS, DELETE");
//    response.setHeader("Access-Control-Max-Age", "3600");
//    response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, authorization, X-Custom-Header, XMLHttpRequest");
//    response.setHeader("Access-Control-Allow-Credentials", "true");
////Access-Control-Allow-Credentials
//    //response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
//
//    HttpServletRequest request = (HttpServletRequest) req;
//    if (request.getMethod()!= "OPTIONS") {
//      chain.doFilter(req, res);
//    } else {
//    }
//    //chain.doFilter(req, res);*/
//  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    String vl = request.getHeader("Origin");

    response.setHeader("Access-Control-Allow-Credentials", "true");
    // TODO SECURITY !!!
    response.setHeader("Access-Control-Allow-Origin", (vl!=null)?vl:"*" ); // *
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    response.setHeader("Access-Control-Expose-Headers","X-Uid, X-Authentication, Authorization, WWW-Authenticate");
  //https://spring.io/guides/tutorials/spring-security-and-angular-js/
    //if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
   //     response.setStatus(HttpServletResponse.SC_OK);
   // } else {
    //  chain.doFilter(req, res);
    //}
    chain.doFilter(req, res);
  }

  public void init(FilterConfig filterConfig) {

  }

  public void destroy() {}

}