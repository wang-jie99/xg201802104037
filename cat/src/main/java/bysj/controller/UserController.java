package bysj.controller;

import bysj.domain.User;
import bysj.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
        /**
         * PUT, http://49.235.12.142:8080/bysj1837/user.ctl, 修改学位
         * <p>
         * 修改一个学位对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
         *
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
        @Override
        protected void doPut(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String user_json = JSONUtil.getJSON(request);
            //将JSON字串解析为Degree对象
            User user = JSON.parseObject(user_json, User.class);

            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            //到数据库表修改Degree对象对应的记录
            try {
                boolean update = UserService.getInstance().updateUser(user);
                if(update) {
                    message.put("message", "修改成功");
                }else{
                    message.put("message", "未修改成功");
                }
            } catch (SQLException e) {
                message.put("message", "数据库操作异常");
                e.printStackTrace();
            } catch (Exception e) {
                message.put("message", "网络异常");
                e.printStackTrace();
            }
            //响应message到前端
            response.getWriter().println(message);
        }

        /**
         * GET, http://49.235.12.142:8080/bysj1837/degree.ctl?id=1, 查询id=1的学位
         * GET, http://49.235.12.142:8080/bysj1837/degree.ctl, 查询所有的学位
         * 把一个或所有学位对象响应到前端
         *
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //读取参数id
            String id_str = request.getParameter("id");
            String username = request.getParameter("username");
            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            try {
                //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
                if (id_str == null && username == null) {
                    responseUsers(response);
                } else if (id_str == null) {
                    responseUserByUsername(username, response);
                }else {
                    int id = Integer.parseInt(id_str);
                    responseUser(id, response);
                }
            } catch (SQLException e) {
                message.put("message", "数据库操作异常");
                e.printStackTrace();
                //响应message到前端
                response.getWriter().println(message);
            } catch (Exception e) {
                message.put("message", "网络异常");
                e.printStackTrace();
                //响应message到前端
                response.getWriter().println(message);
            }
        }

        //响应一个学位对象
        private void responseUser(int id, HttpServletResponse response)
                throws ServletException, IOException, SQLException {
            //根据id查找学位
            User user = UserService.getInstance().find(id);
            String json = JSON.toJSONString(user);
            //响应degree_json到前端
            response.getWriter().println(json);
        }

    private void responseUserByUsername(String name, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //指定schoolId的所有专业对象
        User user = UserService.getInstance().findByUsername(name);
        String userByUsername_json = JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect);
        //响应departments_json到前端
        response.getWriter().println(userByUsername_json);
    }

        //响应所有学位对象
        private void responseUsers(HttpServletResponse response)
                throws ServletException, IOException, SQLException {
            //获得所有学位
            Collection<User> users = UserService.getInstance().findAll();
            String degrees_json = JSON.toJSONString(users, SerializerFeature.DisableCircularReferenceDetect);
            //响应degrees_json到前端
            response.getWriter().println(degrees_json);
        }
}
