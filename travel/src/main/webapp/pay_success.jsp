<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webbase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages-paysuccess.css">
    <title>微信支付</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container-fluid" >
    <div class="cart py-container" >
        <!--主内容-->
        <div class="paysuccess" style="height: 500px">
            <div class="success">
                <h3><img src="img/right.png" width="28" height="28" style="display: inline">　恭喜您，支付成功啦！</h3>
                <div class="paydetail">
                    <p>支付方式：微信支付</p>
                    <p>支付金额：￥1006.00元</p>
                    <p class="button"><a href="#" class="sui-btn btn-xlarge btn-danger">查看订单</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="index.jsp" class="sui-btn btn-xlarge ">继续购物</a></p>
                </div>
            </div>

        </div>
    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
