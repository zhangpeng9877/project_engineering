<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--bootstrap--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/getParameter.js"></script>

<!-- 头部 start -->
<header id="header">
    <%--广告--%>
    <div class="top_banner">
        <img src="${pageContext.request.contextPath}/images/top_banner.jpg" alt="">
    </div>
    <%--右侧按钮--%>
    <div class="shortcut">
        <!-- 未登录状态 -->
        <c:if test="${empty sessionScope.currentUser}">
            <div class="login_out">
                <a id="loginBtn" data-toggle="modal" data-target="#loginModel" style="cursor: pointer;">登录</a>
                <a href="register.jsp" style="cursor: pointer;">注册</a>
            </div>
        </c:if>

        <!-- 登录状态 -->
        <c:if test="${not empty sessionScope.currentUser}">
            <div class="login">
                <sapn>欢迎回来，</sapn>
                <span style="font-weight: bold;font-size: 16px">
                    <c:if test="${empty currentUser.nickname}">
                        ${sessionScope.currentUser.username}
                    </c:if>
                    <c:if test="${not empty currentUser.nickname}">
                        ${sessionScope.currentUser.nickname}
                    </c:if>
                </span>
                <a href="${pageContext.request.contextPath}/userServlet?action=userInfo" class="collection">个人中心</a>
                <a href="${pageContext.request.contextPath}/cartServlet?action=findAll" class="collection">购物车</a>
                <a href="${pageContext.request.contextPath}/userServlet?action=loginOut">退出</a>
            </div>
        </c:if>
    </div>
    <%--搜索框--%>
    <div class="header_wrap">
        <div class="topbar">
            <div class="logo">
                <a href="/"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt=""></a>
            </div>
            <div class="search">
                <input id="rname" name="rname" type="text" placeholder="请输入路线名称" class="search_input" value="${rname}"
                       autocomplete="off">
                <a href="javascript:void(0);" onclick="searchClick()" class="search-button">搜索</a>
            </div>
            <div class="hottel">
                <div class="hot_pic">
                    <img src="${pageContext.request.contextPath}/images/hot_tel.jpg" alt="">
                </div>
                <div class="hot_tel">
                    <p class="hot_time">客服热线(9:00-6:00)</p>
                    <p class="hot_num">400-618-9090</p>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- 头部 end -->
<!-- 首页导航 -->
<div class="navitem">
    <ul class="nav" id="categoryUI">
        <li class="nav-active"><a href="index.jsp">首页</a></li>
        <%--ajax动态导航条--%>
        <script type="text/javascript">
            $(function () {
                // 发送ajax请求
                let url = "${pageContext.request.contextPath}/categoryServlet?action=findAll";
                $.get(url, function (response) { // 返回的是list集合json数据，遍历出即可
                    for (let c of response) {
                        $("#categoryUI").append('<li><a href="${pageContext.request.contextPath}/routeServlet?action=findByPage&cid=' + c.cid + '&currentPage=1">' + c.cname + '</a></li>');
                    }
                    $("#categoryUI").append('<li><a href="favoriterank.jsp">收藏排行榜</a></li>');
                })
            })
        </script>
    </ul>
</div>
<!-- 登录模态框 -->
<div class="modal fade" id="loginModel" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <%--头部--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="loginModelLable">
                    <ul id="myTab" class="nav nav-tabs" style="width: auto">
                        <li class="active">
                            <a href="#pwdReg" data-toggle="tab">
                                密码登录
                            </a>
                        </li>
                        <li><a href="#telReg" data-toggle="tab">短信登录</a></li>
                    </ul>
                    <span id="loginErrorMsg" style="color: red;"></span>
                </h4>

            </div>
            <%--内容--%>
            <div id="myTabContent" class="tab-content">
                <%--密码登录--%>
                <div class="tab-pane fade in active" id="pwdReg">
                    <form id="pwdLoginForm" action="#" method="post">
                        <input type="hidden" name="action" value="pwdLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control" id="login_username" name="username"
                                       placeholder="请输入用户名">
                            </div>
                            <div class="form-group">
                                <label>密码</label>
                                <input type="password" class="form-control" id="login_password" name="password"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <span id="pwdLoginSpan" style="color:red"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" id="pwdLogin" class="btn btn-primary" value="登录"/>
                        </div>
                    </form>
                </div>
                <%--短信登录--%>
                <div class="tab-pane fade" id="telReg">
                    <form id="telLoginForm" method="post" action="#">
                        <input type="hidden" name="action" value="telLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>手机号</label>
                                <input type="text" class="form-control" name="telephone" id="login_telephone"
                                       placeholder="请输入手机号">
                            </div>
                            <div class="form-group">
                                <label>手机验证码</label>
                                <input type="text" class="form-control" id="login_check" name="smsCode"
                                       placeholder="请输入手机验证码">
                            </div>
                            <input class="btn btnlink" type="text" name="telephone"
                                   value="发送手机验证码"
                                   id="login_sendSmsCode">
                            <%--<a href="javaScript:void(0)" >发送手机验证码</a>--%>
                        </div>
                        <div class="modal-footer">
                            <span id="telLoginSpan" style="color:red"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" class="btn btn-primary" id="telLogin" value="登录"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    // 密码回车键登录
    $("#login_password").bind('keypress',function (event) {
        if (event.keyCode == "13") {
            $('#pwdLogin').click();
        }
    })
    // 验证码回车键登录
    $("#login_check").bind('keypress',function (event) {
        if (event.keyCode == "13") {
            $('#telLogin').click();
        }
    })


    <%--搜索发送链接--%>
    function searchClick() {
        let rname = $("#rname").val();
        location.href = '${pageContext.request.contextPath}/routeServlet?action=findByPage&rname=' + rname;
    }

    // 回车键搜索查询
    $("#rname").bind('keypress', function (event) {
        if (event.keyCode == "13") {
            searchClick();
        }
    })


    // 手机格式的正则表达式
    function telephoneReg(obj) {
        // 获取用户名的输入框的值
        let telephone = $(obj).val();
        //手机号正则
        let mPattern = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
        return mPattern.test(telephone);
    }

    // 短信ajax请求登录
    $("#telLogin").click(function () {
        // 手机号码不能为空
        if (!isNull("#login_telephone")) {
            return;
        }
        // 短信验证不能为空
        if (!isNull("#login_check")) {
            return;
        }

        // 手机正则验证
        if (!telephoneReg("#login_telephone")) {
            $("#telLoginSpan").html("手机格式错误");
            return;
        }

        let url = "${pageContext.request.contextPath}/userServlet";
        let data = $("#telLoginForm").serialize();
        $.post(url, data, function (response) {
            if (response.success) { // 登入成功
                location.reload();  // 成功，刷新当前页
            } else {
                $("#telLoginSpan").html("验证码错误或者手机未注册");
            }
        })

    })
    // 当手机号码款或者验证码框获取焦点时，清除错误提示
    lose_focus("#login_telephone", "#telLoginSpan");
    lose_focus("#login_check", "#telLoginSpan");

    // 发送验证码的ajax请求
    $("#login_sendSmsCode").click(function () {

        // 手机正则验证
        if (!telephoneReg("#login_telephone")) {
            $("#telLoginSpan").html("手机格式错误");
            return;
        }

        let url = "${pageContext.request.contextPath}/userServlet";
        let data = "action=sendSms&telephone=" + $("#login_telephone").val();
        $.post(url, data, function (response) {
            if (!response.success) {
                alert(response.message);
            }
        });
        // 调用计时器
        countDown(this);

    })

    // 密码的ajax请求登录
    $("#pwdLogin").click(function () {

        // 判断密码不能为空
        if (!isNull("#login_username")) {
            return;
        }
        // 判断用户名不能为空
        if (!isNull("#login_password")) {
            return;
        }

        let url = "${pageContext.request.contextPath}/userServlet";
        let data = $("#pwdLoginForm").serialize();  // action=pwdLogin&username=123&password=123
        // console.log(data);
        $.post(url, data, function (response) {
            if (response.success) { // 登入成功
                location.reload();  // 成功，刷新当前页
            } else {
                $("#pwdLoginSpan").html("用户名或者密码错误");
            }
        })
    })
    // 当用户名或者密码框获取焦点时，清除错误提示
    lose_focus("#login_username", "#pwdLoginSpan");
    lose_focus("#login_password", "#pwdLoginSpan");

    // 判断是否为空
    function isNull(obj) {
        let message = $(obj).val();
        message = message.replace(/ /g, "");
        if (!message) {
            return false;   // 空返回false
        }
        return true;
    }

    // 验证码的60s倒计时
    let num = 60;

    function countDown(obj) {
        num--;
        if (num == 0) { // 当为0时可以再次点击发送短信
            $(obj).prop("disabled", false);  // 按钮可重新使用
            $(obj).val("重新发送验证码");
            num = 60;   // 重置时间
        } else {
            $(obj).prop("disabled", true);  // 按钮禁止使用
            $(obj).val("发送手机验证码(" + num + ")");
            // 一次性定时器
            setTimeout(function () {
                countDown(obj); // 递归调用
            }, 1000);

        }
    }

    // 获取焦点的方法
    function lose_focus(obj, result) {
        $(obj).focus(function () {
            $(result).html("");
        })
    }

</script>
