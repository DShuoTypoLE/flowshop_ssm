<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <script type="text/javascript" src="bs/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="bs/validform/style.css">
    <script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script>
    <script type="text/javascript" src="js/admin/adminManage/adminEdit.js"></script>
</head>
<body>
<c:if test="${!empty adminMessage}">
    <h3 class="text-center">${adminMessage}</h3>
</c:if>
<div class="container">
    <h2 class="text-center">用户修改</h2>
    <form action="jsp/admin/AdminManageServlet?action=update" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${adminInfo.id}">
        <div class="form-group">
            <label for="userName" class="col-sm-2 col-sm-offset-2 control-label">用户名：</label>
            <div class="col-sm-4">
                <span>${adminInfo.userName}</span>
            </div>
        </div>
        <div class="form-group">
            <label for="passWord" class="col-sm-2 col-sm-offset-2 control-label">密码：</label>
            <div class="col-sm-4">
                <input type="password" name="passWord" id="passWord" class="form-control" value="${adminInfo.passWord}">
            </div>
            <div class="col-sm-4">
                <span class="Validform_checktip">密码为4~8位字符</span>
            </div>
        </div>
        <div class="form-group">
            <label for="passWord_ck" class="col-sm-2 col-sm-offset-2 control-label">确认密码：</label>
            <div class="col-sm-4">
                <input type="password" name="passWord_ck" id="passWord_ck" class="form-control"
                       value="${adminInfo.passWord}">
            </div>
            <div class="col-sm-4">
                <span class="Validform_checktip"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 col-sm-offset-2 control-label">姓名：</label>
            <div class="col-sm-4">
                <input type="text" id="name" name="name" class="form-control" value="${adminInfo.name}">
            </div>
            <div class="col-sm-4">
                <span class="Validform_checktip">姓名为2~8位字符</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-sm-offset-2 control-label" style="margin-left:390px;">
                <input class="btn btn-success btn-block" type="submit" value="更新">
            </label>
            <label class="col-sm-2 control-label">
                <input class="btn btn-warning btn-block" type="reset" value="重置">
            </label>
        </div>
    </form>
    <%--		&lt;%&ndash;	获取表单提交后的返回值	&ndash;%&gt;--%>
    <%--		<iframe name="form" id="form" style="display:none"></iframe>--%>
</div>
<%--<script>--%>
<%--	&lt;%&ndash;	根据后台传回的值进行跳转登陆页面&ndash;%&gt;--%>
<%--	$("#form").load(function(){--%>
<%--		var text = $(this).contents().find("body").text();--%>
<%--		console.log(text);--%>
<%--		//修改成功后准备页面跳转--%>
<%--		if(confirm(text + ",请重新登录~")){--%>
<%--			window.location.href = "LoginOutServlet";--%>
<%--		}else{--%>
<%--			alert("选择取消也没用,啦啦啦~快去重新登陆吧~~");--%>
<%--			window.location.href = "LoginOutServlet";--%>
<%--		}--%>
<%--	})--%>
<%--</script>--%>
</body>
<%--<script src="https://cdn.bootcss.com/jquery.form/4.2.2/jquery.form.js"></script>--%>
<%--<script>--%>
<%--    //表单提交,会把所有有name属性的值提交到后台--%>
<%--    $("#sub").click(function () {--%>
<%--        $("#form_reg").ajaxSubmit(function (data) {--%>
<%--            console.log(data);--%>
<%--            if (confirm(data + ",请重新登录~")) {--%>
<%--                window.location.href = "LoginOutServlet";--%>
<%--            } else {--%>
<%--                alert("选择取消也没用,啦啦啦~快去重新登陆吧~~");--%>
<%--                window.location.href = "LoginOutServlet";--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
</html>