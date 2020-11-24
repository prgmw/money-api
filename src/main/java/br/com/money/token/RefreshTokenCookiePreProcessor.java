//package br.com.money.token;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class RefreshTokenCookiePreProcessor implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//
//        if ("/oauth/token".equals(req.getRequestURI())
//                && "refresh_token".equals(req.getParameter("grant_type")) &&
//                req.getCookies() != null) {
//
//            for (Cookie cook : req.getCookies()) {
//                System.out.println(cook.getValue());
//            }
//
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
