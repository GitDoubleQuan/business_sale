<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">

	function search_attr_up(attr_id, value_id, shxmch) {
		// 增加属性参数
		var a = "<span id='search_shxmch_"+attr_id+"' style='cursor:pointer'>";
		var b = "<input name='attr' type='hidden' value='{\"shxm_id\":"+attr_id+",\"shxzh_id\":"+value_id+"}' />";//14_15
		var c = "<a href='javascript:search_attr_down("+attr_id+");'>"+shxmch+"</a>";
		var d = "</span>";
		$("#search_param").append(a+b+c+d);
		$("#search_id_"+attr_id).hide();

		// 异步提交查询请求
		search_attr_sku();
	}
	function search_trade_up(trade_id,pp_mch) {
		// 增加属性参数
		var a = "<span id='search_ppmch_"+trade_id+"' style='cursor:pointer'>";
		var b = "<input name='trade'type='hidden' value="+trade_id+" />";
		var c = "<a href='javascript:search_trade_down("+trade_id+");'>"+pp_mch+"</a>";
		var d = "</span>";
		$("#search_param").append(a+b+c+d);
		$("#search_trade_id_"+trade_id).hide();

		// 异步提交查询请求
		search_attr_sku();

	}
	
	function search_attr_down(attr_id) {
		// 减少属性参数
		$("#search_id_"+attr_id).show();
		$("#search_shxmch_"+attr_id).remove();
		
		// 异步提交查询请求
		search_attr_sku();
	}

	function search_trade_down(trade_id) {
		$("#search_trade_id_"+trade_id).show();
		$("#search_ppmch_"+attr_id).remove();

		search_attr_sku();
	}
	
	function search_attr_sku() {

		// 加入排序参数
		var order = $("#search_order").val();
		var class_2_id = ${class_2_id};
		// 从页面获得保存属性参数
		var array = new Array();
		var paramString = "class_2_id="+class_2_id+"&order="+order;
		var inputs = $("#search_param input[name='attr']");
		$(inputs).each(function(i,json){
			var obj = $.parseJSON(json.value);	
			paramString = paramString+"&list_attr_value["+i+"].shxm_id="+obj.shxm_id+"&list_attr_value["+i+"].shxzh_id="+obj.shxzh_id;
		
		});

		var tradeInputs = $("#search_param input[name='trade']");
		$(tradeInputs).each(function (i,input) {
			paramString = paramString + "&trade_id="+input.value ;
		})
		
		// 异步提交查询请求
		// param[0]=14&param[1]=15&class_2_id=28
		// k=v&k=v
  		$.post("attr_search.do",paramString,function(data){
			$("#search_list_inner").html(data);
		});  
	}
	
	function search_order(new_order){
		var old_order = $("#search_order").val();
		if(old_order==new_order){
			new_order = new_order + " desc ";
		}
		$("#search_order").val(new_order);
		search_attr_sku();
	}


	
</script>
<title>硅谷商城</title>
</head>
<body>
	
	<div class="menu">
		<div class="nav">
			<div class="navs">
				<div class="left_nav">
					全部商品分类
					<div class="nav_mini" style="display:none">
						<ul>
							<li>
								<a href="">家用电器</a>
								<div class="two_nav">
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
								</div>
							</li>
							<li><a href="">手机、数码、通信</a></li>
							<li><a href="">电脑、办公</a></li>
							<li><a href="">家具、家居、家装</a></li>
							<li><a href="">男装、女装、内衣</a></li>
							<li><a href="">个户化妆</a></li>
							<li><a href="">鞋靴</a></li>
							<li><a href="">户外运动</a></li>
							<li><a href="">汽车</a></li>
							<li><a href="">母婴</a></li>
							<li><a href="">食品</a></li>
							<li><a href="">营养保健</a></li>
							<li><a href="">图书</a></li>
							<li><a href="">彩票</a></li>
							<li><a href="">理财</a></li>
						</ul>
					</div>
				</div>
				<ul>
					<li><a href="">服装城</a></li>
					<li><a href="">美妆馆</a></li>
					<li><a href="">超市</a></li>
					<li><a href="">全球购</a></li>
					<li><a href="">闪购</a></li>
					<li><a href="">团购</a></li>
					<li><a href="">拍卖</a></li>
					<li><a href="">金融</a></li>
					<li><a href="">智能</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="filter">
		<h2>显示器</h2>
		<div id="search_param" class="filter_div">
		</div>
	</div>
	<input  id="search_order" value=" order by jg "/>
	<div class="Sscreen">
		<div class="title">
			共1205个商品
		</div>
		<c:forEach items="${list_trade}" var="trade">
		<div id="search_trade_id_${trade.id}">
			<div class="list">
				<div class="img">
					<img alt="" width="100%" height="70%" src="upload/image/${trade.url}">
				</div>
				<div class="title"><a href="javascript:search_trade_up(${trade.id},${trade.ppmch});">${trade.ppmch}</a></div>
			</div>
		</div>
		</c:forEach>
		
		<c:forEach items="${list_attr}" var="attr">
			<div id="search_id_${attr.id}">
				<div class="list">
					<span>${attr.shxm_mch}：</span>
					<c:forEach items="${attr.list_value}" var="val">
					<a href="javascript:search_attr_up(${attr.id},${val.id},'${val.shxzh}${val.shxzh_mch}');">${val.shxzh}${val.shxzh_mch}</a>
					</c:forEach>
				</div>
			</div>
		</c:forEach>
		


		<div class="list">
			<span class="list_span" id="list_beas"><a href="javascript:search_order(' order by sku_xl ');">销量 </a></span>
			<span class="list_span"><a href="javascript:search_order(' order by jg ');">价格</a> </span>
			<span class="list_span"><a href="javascript:search_order(' order by plsh ');">评论数 </a></span>
			<span class="list_span"><a href="javascript:search_order(' order by sku.chjshj ');">上架时间 </a></span>
		</div>
	</div>


</body>
</html>