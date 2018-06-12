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
<form id="searchForm" action="/systemMenu/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_bottom">
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/systemMenu/input.do?parentId=${qo.parentId}"
                        />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div id="top_nav">
                当前菜单:
               <a href="/systemMenu/query.do">根菜单</a>
                <c:forEach var="menu" items="${parents}">
                    -><a href="/systemMenu/query.do?parentId=${menu.id}">${menu.name}</a>
                </c:forEach>
            </div>
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>菜单名称</th>
                        <th>菜单编码</th>
                        <th>父菜单</th>
                        <th>URL</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${list}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${num.count}</td>
                            <td>${entity.name}</td>
                            <td>${entity.sn}</td>
                            <td>${entity.parent.name}</td>
                            <td>${entity.url}</td>
                            <td>
                                <a href="/systemMenu/input.do?id=${entity.id}&parentId=${entity.parent.id}">编辑</a>
                                <a href="javascript:" data-url="/systemMenu/delete.do?id=${entity.id}"
                                    class="btn_delete" data-obj="systemMenu"
                                >删除</a>
                                <a href="/systemMenu/query.do?parentId=${entity.id}">子菜单</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
