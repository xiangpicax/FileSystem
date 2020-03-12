package fileter;

import util.Constants;
import util.SignUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="MyFilter", urlPatterns="/*")
public class FileFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("@@@@@@我有在拦截哦@@@@@@@");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest =(HttpServletRequest) request;
        String SID = httpServletRequest.getHeader("X-SID");
        String Signature = httpServletRequest.getHeader("X-Signature");
        System.out.println("X-SID"+SID);
        System.out.println("X-Signature"+Signature);
        boolean isLegal = SignUtils.doCheck(SID, Signature, Constants.strPublicKey);
        System.out.println(isLegal);
        if(isLegal){
            chain.doFilter(request,response);
        }
        if(!isLegal){
            httpServletResponse.setStatus(403);
        }

    }

    public void destroy() {

    }
}
