<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/4
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>

</head>
<body>
<form id="searchForm" action="/department/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_bottom">
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/department/input.do"
                        />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>部门名称</th>
                        <th>部门编码</th>
                        <th></th>
                    </tr>
                    <c:forEach var="d" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${num.count}</td>
                            <td>${d.name}</td>
                            <td>${d.sn}</td>
                            <td>
                                <a href="/department/input.do?id=${d.id}">编辑</a>
                                <a href="javascript:" data-url="/department/delete.do?id=${d.id}"
                                    class="btn_delete" data-obj="department"
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
