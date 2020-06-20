<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webbase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages-weixinpay.css">
    <title>微信支付</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/qrcode.min.js"></script>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container-fluid">
    <div class="cart py-container">
        <!--主内容-->
        <div class="checkout py-container  pay">
            <div class="checkout-tit">
                <h4 class="fl tit-txt"><span class="success-icon"></span><span  class="success-info">订单提交成功，请您及时付款！订单号：${oid}</span></h4>
                <span class="fr"><em class="sui-lead">应付金额：</em><em  class="orange money">￥${price}</em>元</span>
                <div class="clearfix"></div>
            </div>
            <div class="checkout-steps">
                <div class="fl weixin">微信支付</div>
                <div class="fl sao">
                    <p class="red" style="padding-bottom: 40px"><%--二维码已过期，刷新页面重新获取二维码。--%></p>
                    <div class="fl code">
                        <div id="qr_code"></div>    <%--支付二维码--%>
                        <div class="saosao">
                            <p>请使用微信扫一扫</p>
                            <p>扫描二维码支付</p>
                        </div>
                    </div>
                    <div class="fl" style="background:url(./img/phone-bg.png) no-repeat;width:350px;height:400px;margin-left:40px">

                    </div>

                </div>
                <div class="clearfix"></div>
            </div>
        </div>

    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
<script type="text/javascript">
    let qrcode =  new QRCode("qr_code","${pay_url}");   // 生成微信支付二维码

    // 每隔5s向后台查询订单是否付款
    setInterval(function () {
        // 发送ajax请求
        let url = "${pageContext.request.contextPath}/orderServlet";
        let data = "action=findState&oid=${oid}";
        $.post(url,data,function (response) {
            if (response.success) { // 支付成功了
                location.href = "${pageContext.request.contextPath}/pay_success.jsp";   // 跳转到成功的支付页面
            }
        })
    },5000)



    // 超时10分钟支付失败
    setTimeout(function () {
        location.href = "${pageContext.request.contextPath}/pay_fail.jsp";   // 跳转到失败的支付页面
    },600000)
</script>
