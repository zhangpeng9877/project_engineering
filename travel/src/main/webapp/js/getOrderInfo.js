$(function(){
	$(".address").hover(function(){
		$(this).addClass("address-hover");	
	},function(){
		$(this).removeClass("address-hover");	
	});
})

$(function(){
	$(".addr-item .name").click(function(){
		 $(this).toggleClass("selected").siblings().removeClass("selected");	
	});
	$(".payType li").click(function(){
		 $(this).toggleClass("selected").siblings().removeClass("selected");	
	});
})

// 判断是否选择好订单地址，是否可以提交
$(function () {
    $(".submit").click(function () {
        let val = $('input:radio[name="addressId"]:checked').val();
        if (!val) {
            alert("请先选择下单地址");
            return false;
        }
        return true;
    })
})
