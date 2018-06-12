<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/ajaxForm/ajaxForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        //======左移右移=======
        $(function () {

            //======异步提交表单=====
            $("#editForm").ajaxForm(function (data) {
                var obj = $("#editForm").data("obj");
                if (data.success){
                    showDialog("保存成功22",function () {
                        location.href = "/" + obj + "/query.do";
                    })
                }else {
                    showDialog("保存失败:" + data.msg);
                }
            });

            //把右边的权限变为selected,并提交表单
            $(".btn_submit").click(function () {
                $(".selected_permission option").prop("selected",true);
                $(".selected_menu option").prop("selected",true);
                $("#editForm").submit();
            });

            $("#selectAll").click(function () {
                //获取所有的option
                $(".all_permissions option").appendTo(".selected_permission");
            });
            $("#deselectAll").click(function () {
                $(".selected_permission option").appendTo(".all_permissions");
            });
            $("#select").click(function () {
                //获取所有的option
                $(".all_permissions option:selected").appendTo(".selected_permission");
            });
            $("#deselect").click(function () {
                //获取所有的option
                $(".selected_permission option:selected").appendTo(".all_permissions");
            });
            //=========
            //把右边的权限变为selected,并提交表单
            $(".btn_submit").click(function () {
                $(".selected_menu option").prop("selected",true);
                $("#editForm").submit();
            });

            $("#selectAllMenu").click(function () {
                //获取所有的option
                $(".all_menu option").appendTo(".selected_menu");
            });
            $("#deselectAllMenu").click(function () {
                $(".selected_menu option").appendTo(".all_menu");
            });
            $("#selectMenu").click(function () {
                //获取所有的option
                $(".all_menu option:selected").appendTo(".selected_menu");
            });
            $("#deselectMenu").click(function () {
                //获取所有的option
                $(".selected_menu option:selected").appendTo(".all_menu");
            });


            //=====去除重复=====
            var pids = $.map($(".selected_permission option"),function (ele) {
                return ele.value;
            });
            
            $.each($(".all_permissions option"),function (index,ele) {
                if ($.inArray(ele.value,pids) != -1){
                    $(ele).remove();
                }
            });

            //=====去除重复=====
            var mids = $.map($(".selected_menu option"),function (ele) {
                return ele.value;
            });

            $.each($(".all_menu option"),function (index,ele) {
                if ($.inArray(ele.value,mids) != -1){
                    $(ele).remove();
                }
            });

        });
    </script>
</head>
<body>
<form id="editForm" action="/role/saveOrUpdate.do" method="post" data-obj="role">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">角色编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">角色名称</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色编码</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">分配角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_permissions">
                                        <!--左边框显示所有权限-->
                                        <c:forEach var="p" items="${permissions}">
                                            <option value="${p.id}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="select" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select name="ids" multiple="true" class="ui_multiselect01 selected_permission">
                                        <c:forEach var="p" items="${entity.permissions}">
                                            <option value="${p.id}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">分配菜单</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_menu">
                                        <!--左边框显示所有权限-->
                                        <c:forEach var="entity" items="${menus}">
                                            <option value="${entity.id}">${entity.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="selectMenu" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAllMenu" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselectMenu" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAllMenu" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select name="menuIds" multiple="true" class="ui_multiselect01 selected_menu">
                                        <c:forEach var="menu" items="${entity.menus}">
                                            <option value="${menu.id}">${menu.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="确定保存" class="ui_input_btn01 btn_submit"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
