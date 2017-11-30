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
	function address_show(dz_mch){
		$("#address_show").html(dz_mch);
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	订单确认页面<br>
	<form action="save_order.do" method="post">
		<c:forEach items="${list_address}" var="address">
			<input onclick="address_show('${address.yh_dz}')" type="radio" name="address_id" value="${address.id}"/>${address.yh_dz}     ${address.shjr}
		</c:forEach>
		<hr>
		请确认订单：${order.jdh} ￥：${order.zje}<br>
		<c:forEach items="${order.list_flow}" var="flow">
			${flow.psfsh}:<br>${flow.mqdd}
			<div style="border:red 1px solid;">
				<c:forEach items="${flow.list_info}" var="info">
					<img src="upload/image/${info.shp_tp}" width="70px"/>
					${info.sku_mch} ${info.sku_jg}<br>
				</c:forEach>
			</div>
		</c:forEach>
		<br>
		<div style="border:1px red solid;width:500px;height:200px;">
			${sum}:<span id="address_show"></span>
		</div>
		<input type="submit" value="提交订单"/>
	</form>
</body>
</html>