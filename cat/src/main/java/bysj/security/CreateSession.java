package bysj.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/createSession")
public class CreateSession extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //如果当前请求对应着服务器的一个session对象，则返回该对象
        //如果服务器内存中没有session对象与当前请求对应，则创建一个session对象并返回该对象
        HttpSession session = request.getSession();
        //如果session不活跃间隔大于10秒，则该session失败
        session.setMaxInactiveInterval(10);
        response.getWriter().println("session will last 10 seconds");
    }
}
