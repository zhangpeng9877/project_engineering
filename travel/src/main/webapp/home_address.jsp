<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webbase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages-seckillOrder.css">
    <title>地址管理</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp" %>
<div class="container-fluid">
    <!--header-->
    <div id="account">
        <div class="py-container">
            <div class="yui3-g home">
                <!--左侧列表-->
                <%@include file="home_left.jsp" %>
                <!--右侧主内容-->
                <div class="yui3-u-5-6 order-pay">
                    <div class="body userAddress">
                        <div class="address-title">
                            <span class="title">地址管理</span>
                            <a data-toggle="modal" data-target="#addressModel" data-keyboard="false"
                               class="sui-btn  btn-info add-new">添加新地址</a>
                            <span class="clearfix"></span>
                        </div>
                        <div class="address-detail">
                            <table class="sui-table table-bordered">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>地址</th>
                                    <th>联系电话</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${addressList}" var="address">
                                    <tr>
                                        <td>${address.contact}</td>
                                        <td>${address.address}</td>
                                        <td>${address.telephone}</td>
                                        <td>
                                            <a href="javascript:;">编辑</a>
                                            <a class="delete_add"
                                               href="${pageContext.request.contextPath}/addressServlet?action=deleteByAid&aid=${address.aid}">删除</a>

                                            <c:if test="${address.isdefault == 0}">
                                                <a href="${pageContext.request.contextPath}/addressServlet?action=updateByAidWithIsdefault&aid=${address.aid}">设为默认</a>
                                            </c:if>
                                            <c:if test="${address.isdefault == 1}">
                                                <span style="color: #999999">默认</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- 地址模态框 -->
                        <div class="modal fade" id="addressModel" tabindex="-1" role="dialog"
                             aria-labelledby="loginModelLable">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <%-- 新增地址--%>
                                    <div class="tab-pane fade in active">
                                        <form id="xxxx" onsubmit="return isFrom()"
                                              action="${pageContext.request.contextPath}/addressServlet" method="post">
                                            <input type="hidden" name="action" value="save">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label>姓名</label>
                                                    <input id="contact" type="text" class="form-control" name="contact"
                                                           placeholder="姓名">
                                                </div>
                                                <div class="form-group">
                                                    <label>地址</label>
                                                    <input id="address" type="text" class="form-control" name="address"
                                                           placeholder="请输入地址">
                                                </div>
                                                <div class="form-group">
                                                    <label>联系电话</label>
                                                    <input id="telephone" type="text" class="form-control"
                                                           name="telephone"
                                                           placeholder="联系电话">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <span id="errorSpan"
                                                      style="margin-right: 35px;font-size: 12px;color: red"></span>
                                                <input type="button" class="btn btn-default" data-dismiss="modal"
                                                       value="关闭">
                                                <input type="submit" class="btn btn-primary" value="保存"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp" %>
</body>
</html>
<script type="text/javascript">
    // 删除对应的收获地址提示信息
    $(".delete_add").click(function () {
        let name = $(this).parent().parent().children("td:eq(0)").text();   // 获取到它父节点的父节点的第一个子节点标签中的值
        return confirm("你确定要删除【" + name + "】的收获地址吗?");
    })

    // 手机号码的校验
    let phoneReg = false;
    $("#telephone").blur(function () {
        let mPattern = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
        phoneReg = mPattern.test($(this).val());
        if (!phoneReg) {
            $("#errorSpan").html("手机号码格式错误");
            return;
        }
    })

    // 对空白字符的校验,调用header.jsp页面上的方法
    function isFrom() {
        let flag = isNull("#contact") && isNull("#address") && isNull("#telephone") && phoneReg;
        if (!flag) {
            $("#errorSpan").html("格式错误无法提交");
        }
        return flag;
    }

    // 获取焦点之后，取消错误提交，调用header.jsp页面上的方法
    lose_focus("#contact", "#errorSpan");
    lose_focus("#address", "#errorSpan");
    lose_focus("#telephone", "#errorSpan");
</script>
