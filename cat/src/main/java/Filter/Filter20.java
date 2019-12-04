package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@WebFilter(filterName = "Filter 20", urlPatterns = {"/*"})
public class Filter20 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
     //   System.out.println("Filter 20 begins.");
        //强制类型转换为HTTPServlet
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //得到请求的资源的路径
        String path = request.getRequestURI();
        //执行其他过滤器，如过滤器执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        //打印被请求的资源名称和请求时间
        System.out.println(path +" @ "+ new Date());
     //   System.out.println("Filter 10 ends");

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    @Override
    public void destroy() { }
}