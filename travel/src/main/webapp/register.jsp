<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<!--引入头部-->
<%@include file="header.jsp" %>
<!-- 头部 end -->
<div class="rg_layout">
    <div class="rg_form clearfix">
        <%--左侧--%>
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <!--注册表单-->
            <form onsubmit="return check()" id="registerForm"
                  action="${pageContext.request.contextPath}/userServlet" method="post">
                <!--提交处理请求的标识符-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;width: 558px">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号">
                            <span id="userInfoName" style="font-size:10px;color: red"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="telephone">手机号</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="telephone" name="telephone" placeholder="请输入您的手机号">
                            <span id="userInfoPhone" style="font-size:10px;color: red"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="password" id="password" name="password" placeholder="请输入密码">
                            <span id="userInfoPass" style="font-size:10px;color: red"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="smsCode">验证码</label>
                        </td>
                        <td class="td_right check">
                            <input type="text" id="smsCode" name="smsCode" class="check" placeholder="请输入验证码">

                            <input id="sendSmsCode" value="发送手机验证码" class="btn btn-link"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <input type="submit" class="submit" value="注册">
                            <span id="msg" style="color: red;">${requestScope.resultInfo.message}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%--右侧--%>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="javascript:$('#loginBtn').click()">立即登录</a>
            </p>
        </div>
    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp" %>
</body>
</html>
<script type="text/javascript">
    // 用户失去焦点事件
    let nameReg = false;
    $("#username").blur(function () {
        // 获取用户名的输入框的值
        let username = $(this).val();

        // 用户名不能出现空白字符
        username = username.replace(/ /g, "");

        // 用户的正则表达式:4到16位（字母，数字，下划线，减号）
        let uPattern = /^[a-zA-Z0-9_-]{4,16}$/;
        nameReg = uPattern.test(username);
        if (nameReg) {
            $("#userInfoName").html("");
        } else {
            $("#userInfoName").html("4到16位(字母,数字,下划线,减号)");
            return;
        }

        // ajax请求
        let url = "${pageContext.request.contextPath}/userServlet";
        let data = "action=findByUsername&username=" + username;
        $.post(url, data, function (response) {
            // response就是返回的json对象（对应java类就是 ResultInfo）
            if (response.success) {
                $("#userInfoName").html("");
            } else {
                $("#userInfoName").html("用户名已存在");
            }
            nameReg = response.success;
        })
    })

    // 手机失去焦点事件
    let phoneReg = false;
    $("#telephone").blur(function () {
        // 获取用户名的输入框的值
        let telephone = $(this).val();

        //手机号正则
        let mPattern = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
        phoneReg = mPattern.test(telephone);
        if (phoneReg) {
            $("#userInfoPhone").html("");
        } else {
            $("#userInfoPhone").html("手机号码格式不合法");
            return;
        }

        // ajax请求
        let url = "${pageContext.request.contextPath}/userServlet";
        let data = "action=findByTelephone&telephone=" + telephone;
        $.post(url, data, function (response) {
            // response就是返回的json对象（对应java类就是 ResultInfo）
            if (response.success) {
                $("#userInfoPhone").html("");
            } else {
                $("#userInfoPhone").html("手机号码已注册");
            }
            phoneReg = response.success;
        })

    })


    // 密码的正则
    let passReg = false;
    $("#password").blur(function () {
        let password = $(this).val();
        let mPattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
        passReg = mPattern.test(password);
        if (passReg) {
            $("#userInfoPass").html("");
        } else {
            $("#userInfoPass").html("数字,英文,长度为6-20");
        }
    })
    // 判断是否可以提交注册信息
    function check() {
        if (nameReg && phoneReg && passReg) {
            return true;
        } else {
            alert("输入不合法，提交失败");
            return false;
        }
    }


    // 给按钮绑定点击事件，验证码
    $("#sendSmsCode").click(function () {
        // 获取用户输入的手机号
        let telephone = $("#telephone").val();
        console.log(telephone);
        // 发送ajax请求
        let url = "${pageContext.request.contextPath}/userServlet";
        let data = "action=sendSms&telephone=" + telephone;
        $.post(url, data, function (response) {
            if (!response.success) {
                alert(response.message);
            }
        })
        // 调用计时器
        countDown(this);
    })

    // 验证码的60s倒计时
    let number = 60;

    function countDown(obj) {
        number--;
        if (number == 0) { // 当为0时可以再次点击发送短信
            $(obj).prop("disabled", false);  // 按钮可重新使用
            $(obj).val("重新发送验证码");
            number = 60;   // 重置时间
        } else {
            $(obj).prop("disabled", true);  // 按钮禁止使用
            $(obj).val("发送手机验证码(" + number + ")");
            // 一次性定时器
            setTimeout(function () {
                countDown(obj); // 递归调用
            }, 1000);

        }
    }


</script>
