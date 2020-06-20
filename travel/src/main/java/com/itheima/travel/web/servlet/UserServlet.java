package com.itheima.travel.web.servlet;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.WebBeanUtils;
import com.itheima.travel.web.factory.BeanFactory;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/5/16 15:47
 */

@WebServlet("/userServlet")
@MultipartConfig        // 文件上传的注解
public class UserServlet extends BaseServlet {

    private UserService userService = (UserService) BeanFactory.getBean("userService");

    // 短信验证登录
    protected void telLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数，封装到实体类
        User user = WebBeanUtils.populate(new User(), request.getParameterMap());
        // 先查询该手机用户
        user = userService.findByUser(user);
        // 判断是否存在
        if (user == null) { // 表示不存在
            ResultInfo resultInfo = new ResultInfo(false);
            super.stringToJsonWrite(resultInfo, response);
            return;
        }

        // ------------执行到这一步则表示手机号存在

        // 获取请求的验证码
        String smsCode = request.getParameter("smsCode");

        // 获取连接对象
        Jedis jedis = JedisUtils.getJedis();
        // 获取储存在redis中的验证码
        String redisCode = jedis.get("codeSms_" + user.getTelephone());

        if (redisCode == null || !(redisCode.equals(smsCode))) { //表示验证码错误,登入失败
            ResultInfo resultInfo = new ResultInfo(false);
            super.stringToJsonWrite(resultInfo, response);
        } else {    // 表示登录成功，验证码正确
            // 存储用户对象到session中
            request.getSession().setAttribute("currentUser", user);
            ResultInfo resultInfo = new ResultInfo(true, "登录成功", user);
            super.stringToJsonWrite(resultInfo, response);
            // 删除redis中的验证码
            jedis.del("codeSms_" + user.getTelephone());
        }
        // 归还i资源
        jedis.close();

    }

    // 用户退出
    protected void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 删除用户的session域对象
        request.getSession().removeAttribute("currentUser");
        // request.getSession().invalidate();   // 清除所有的session
        // 重定向到首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // ajax用户密码登录
    protected void pwdLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        // 封装到实体类
        User user = WebBeanUtils.populate(new User(), map);

        // 调用service中的方法返回结果
        ResultInfo resultInfo = userService.pwdLogin(user);

        // 判断是否登录成功
        if (resultInfo.getSuccess()) {  // 成功
            // 保存用户数据到session域对象中
            request.getSession().setAttribute("currentUser", resultInfo.getData());
        }
        // 响应json数据给客户端
        super.stringToJsonWrite(resultInfo, response);

    }

    // 注册方法
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --------验证码判断 start
        // 获取请求参数
        String smsCode = request.getParameter("smsCode");
        String telephone = request.getParameter("telephone");

        // 获取jedis连接池对象
        Jedis jedis = JedisUtils.getJedis();
        // 获取储存再redis中的验证码
        String redisCode = jedis.get("codeSms_" + telephone);


        // 进行校验
        if (redisCode == null || (!redisCode.equals(smsCode))) {    // 验证码错误
            request.setAttribute("resultInfo", new ResultInfo(false, "验证码错误"));
            // 跳转到注册页
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            // 归还
            jedis.close();
            return;
        }

        // --------验证码判断 end

        // 获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        // 调用工具类快速封装
        User user = WebBeanUtils.populate(new User(), map);
        // 调用userService中的方法
        ResultInfo resultInfo = userService.register(user);
        // 判断释放注册成功
        if (resultInfo.getSuccess()) {  // 成功
            // 重定向到成功页面(为了防止重复提交表单)
            response.sendRedirect(request.getContextPath() + "/register_ok.jsp");
        } else {    // 失败
            // 保存到域对象中
            request.setAttribute("resultInfo", resultInfo);
            // 重新跳转到注册页
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
        // 删除redis中的验证码
        jedis.del("codeSms_" + telephone);
        // 归还
        jedis.close();
    }

    // 验证用户名是否存在
    protected void findByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String username = request.getParameter("username");
        // 调用方法
        User userName = new User();
        userName.setUsername(username);
        User user = userService.findByUser(userName);
        // 判断用户名是否存在
        ResultInfo resultInfo = null;
        if (user == null) { // 不存在
            resultInfo = new ResultInfo(true);
        } else {    // 存在，不能注册
            resultInfo = new ResultInfo(false);
        }
        // 调用父类的方法，转换为json数据，并响应
        super.stringToJsonWrite(resultInfo, response);

    }

    // 判断手机号码是否存在
    protected void findByTelephone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String telephone = request.getParameter("telephone");

        // 调用方法
        User userPhone = new User();
        userPhone.setTelephone(telephone);
        User user = userService.findByUser(userPhone);

        // 判断是否存在
        ResultInfo resultInfo = null;
        if (user == null) { // 表示不存在,可以注册
            resultInfo = new ResultInfo(true);
        } else {    // 表示存在，无法注册
            resultInfo = new ResultInfo(false);
        }

        // 调用父类的方法，转换为json数据，并响应
        super.stringToJsonWrite(resultInfo, response);

    }

    // 验证码发送
    protected void sendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String telephone = request.getParameter("telephone");

        // 调用方法，完成短信的发送，
        String codeSms = RandomUtil.randomNumbers(6);
        ResultInfo resultInfo = userService.sendSms(telephone, codeSms);

        // 将验证码写入session中
        if (resultInfo.getSuccess()) {
            // 获取jedis连接对象
            Jedis jedis = JedisUtils.getJedis();
            // 把验证码储存到redis中,并设置验证码的存活时间
            jedis.setex("codeSms_" + telephone, 300, codeSms);
            // 归还连接池
            jedis.close();
            System.out.println("短信验证码:" + codeSms);
        }

        // 转换为json数据，响应
        super.stringToJsonWrite(resultInfo, response);
    }

    // 用户个人信息回显
    protected void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先判断用户是否登录
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            // 重定向到主页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        // 如果存在，则转发到个人信息页面
        request.getRequestDispatcher("/home_index.jsp").forward(request, response);
    }

    // 修改个人信息
    protected void updateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数，封装成实体类
        User populate = WebBeanUtils.populate(new User(), request.getParameterMap());

        // -------------文件上传start
        // 获取文件对象
        Part part = request.getPart("pic");
        // 获取文件名和后缀名
        String fileName = part.getSubmittedFileName();
        // 使用糊涂工具包,获取文件的扩展名
        String extName = FileUtil.extName(fileName);
        // 判断用户是否提交文件
        if (fileName.length() > 0) {
            // 指定文件的虚拟路径(重新给文件取名字，使用uuid保证唯一)
            String path = "/pic/" + IdUtil.simpleUUID() + "." + extName;
            // 获取服务器的真实路径
            String realPath = request.getServletContext().getRealPath(path);
            // 保存文件
            part.write(realPath);
            // 给user对象中的图片设置访问地址
            populate.setPic(path);
        }
        // -------------文件上传end


        // 更新用户信息
        userService.updateInfo(populate);
        // 调用service中的查询方法，查询该用户信息
        User currentUser = new User();
        currentUser.setUid(populate.getUid());
        currentUser = userService.findByUser(currentUser);
        // 重置到session域对象中回显数据
        request.getSession().setAttribute("currentUser", currentUser);
        // 重定向到个人信息页面
        response.sendRedirect(request.getContextPath() + "/userServlet?action=userInfo");
    }
}
