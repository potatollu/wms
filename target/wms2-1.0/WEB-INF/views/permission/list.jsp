<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/permission/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_bottom">
                        <input type="button" value="加载权限" class="ui_input_btn01 btn_reload"
                            data-url="/permission/reload.do"
                        />
                    </div>
                </div>
            </div>
        </div>
        <script>
                $(".btn_reload").click(function () {
                    var url = $(this).data("url");
                    showDialog("加载权限需要耗费较长时间,确实要重新加载吗??",function () {
                        $.get(url,function (data) {
                            if (data.success){
                                showDialog("加载成功",function () {
                                    location.href = "/permission/query.do";
                                })
                            }else {
                                showDialog("加载权限失败");
                            }
                        },"json");
                },true);
                });
        </script>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>权限名称</th>
                        <th>权限表达式</th>
                        <th></th>
                    </tr>
                    <c:forEach var="p" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${num.count}</td>
                            <td>${p.name}</td>
                            <td>${p.expression}</td>
                            <td>
                                <a href="javascript:" data-url="/permission/delete.do?id=${p.id}"
                                   class="btn_delete" data-obj="permission"
                                >删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%@include file="/WEB-INF/views/common/page.jsp"%>
        </div>
    </div>
</form>
</body>
</html>

