<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript">
            $(function () {
                //给复选框邦定事件
                $("#all").click(function () {
                    $(".acb").prop("checked",this.checked);
                    console.log($(".acb:checked").size());
                });
                //给批量删除按钮邦定点击事件
                $(".btn_batchDelete").click(function () {
                    var url = $(this).data("url");
                    //检查是否有数据被选中
                    if ($(".acb:checked").size() > 0) {
                        showDialog("确定要批量删除嘛", function (data) {
                            var ids = $.map($(".acb:checked"),function (ele) {
                                return $(ele).data("eid");
                            });
                            //发送ajax请求
                            var sendData = {ids:ids};
                            $.post(url,sendData,function (data) {
                                if (data.success){
                                    showDialog("删除成功",function () {
                                        location.href = "/employee/query.do"
                                    });
                                }else{
                                    showDialog("操作失败：" +data.msg);
                                }
                            },"json");
                        }, true);
                    } else {
                        showDialog("至少选至少选中一个");
                    }
                });
            });
    </script>
    <title>WMS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>

</head>
<body>
<form id="searchForm" action="/employee/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" value="${qo.keyword}" class="ui_input_txt02" name="keyword" />
                        所属部门
                        <select id="dept" class="ui_select01" name="deptId">
                            <option value="-1">==所有部门==</option>
                            <c:forEach var="dept" items="${depts}">
                                <option value="${dept.id}">${dept.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <script>
                        $(function () {
                            $("#dept option[value='${qo.deptId}']").prop("selected",true);
                        });
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/employee/input.do"
                        />
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_batchDelete"
                               data-url="/employee/batchDelete.do"
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
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th></th>
                    </tr>
                    <c:forEach varStatus="num" var="e" items="${result.data}">
                        <tr>
                            <td><input type="checkbox" class="acb" data-eid="${e.id}"/></td>
                            <td>${num.count}</td>
                            <td>${e.name}</td>
                            <td>${e.email}</td>
                            <td>${e.age}</td>
                            <td>${e.dept.name}</td>
                            <td>
                                <a href="/employee/input.do?id=${e.id}">编辑</a>
                                <a href="javascript:" data-url="/employee/delete.do?id=${e.id}"
                                    data-obj="employee" class="btn_delete"
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
