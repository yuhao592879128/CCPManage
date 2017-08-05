package cn.ccpm.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * ͨ�ý�� get �� post���������
 * 
 * 
 * 
 */
@WebFilter
public class EncodingFilter implements Filter {
	  
    private String encoding = "UTF-8";  
    protected FilterConfig filterConfig;  
      
      
    public void init(FilterConfig filterConfig) throws ServletException {  
        this.filterConfig = filterConfig;  
        //��������Ĭ�ϱ�����UTF-8����Ҳ������web.xml�����ļ��������Լ���Ҫ�ı���  
        if(filterConfig.getInitParameter("encoding") != null)  
            encoding = filterConfig.getInitParameter("encoding");  
    }  
  
    public void doFilter(ServletRequest srequset, ServletResponse sresponse,  
            FilterChain filterChain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest)srequset;  
        request.setCharacterEncoding(encoding);  
        filterChain.doFilter(srequset, sresponse);  
    }  
      
    public void destroy() {  
        this.encoding = null;  
    }  
}
