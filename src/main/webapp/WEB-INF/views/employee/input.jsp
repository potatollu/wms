<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        var roleTr = null;
        $(function () {
            //=====数据校验=====
            $("#editForm").validate({
                rules: {
                    name: {
                        required: true,
                        rangelength:[1,6]
                    },
                    password: {
                        required: true,
                        rangelength:[1,6]
                    },
                    repassword:{
                        equalTo:"#password",
                        rangelength:[1,6]
                    },
                    email: {
                        required:true,
                        email: true
                    },
                    age: {
                        digits:true,
                        range:[16,65]
                    }
                }
            });
        //======左移右移=======
            //把右边的权限变为selected,并提交表单
            $(".btn_submit").click(function () {
                $(".selected_roles option").prop("selected",true);
                $("#editForm").submit();
            });

            $("#selectAll").click(function () {
                //获取所有的option
                $(".all_roles option").appendTo(".selected_roles");
            });
            $("#deselectAll").click(function () {
                $(".selected_roles option").appendTo(".all_roles");
            });
            $("#select").click(function () {
                //获取所有的option
                $(".all_roles option:selected").appendTo(".selected_roles");
            });
            $("#deselect").click(function () {
                //获取所有的option
                $(".selected_roles option:selected").appendTo(".all_roles");
            });
            //=====去除重复=====
            var pids = $.map($(".selected_roles option"),function (ele) {
                return ele.value;
            });

            $.each($(".all_roles option"),function (index,ele) {
                if ($.inArray(ele.value,pids) != -1){
                    $(ele).remove();
                }
            });

            //如果是超级管理员隐藏
            if($("#admin").prop("checked")){
                roleTr = $("#roleTr").detach();
            }
            //如果不是绑定事件
            $("#admin").click(function () {
                if (this.checked){
                    roleTr = $("#roleTr").detach();
                }else {
                    //找到父类添加
                    $(this).closest("tr").after(roleTr);
                }
            });

            //======异步提交表单=====
            $("#editForm").ajaxForm(function (data) {
                var obj = $("#editForm").data("obj");
                if (data.success){
                    showDialog("保存成功",function () {
                        location.href = "/" + obj + "/query.do";
                    })
                }else {
                    showDialog("保存失败:" + data.msg);
                }
            });
            $(".btn_submit").click(function () {
                $("#editForm").submit();
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/employee/saveOrUpdate.do" method="post" data-obj="employee">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <c:if test="${empty entity.id}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" value="${entity.password}" name="password" id="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input name="repassword" type="password" class="ui_input_txt02" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${entity.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${entity.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select id="dept" name="dept.id" class="ui_select03">
                            <option value="-1">===所有部门===</option>
                            <c:forEach var="dept" items="${depts}">
                                <option value="${dept.id}">${dept.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input id="admin" type="checkbox" name="admin" value="" class="ui_checkbox01"/>
                    </td>
                </tr>
                <script>
                        $("#dept option[value='${entity.dept.id}']").prop("selected",true);
                        $("#admin").prop("checked",${empty entity ? 0 : entity.admin});
                </script>
                <tr id="roleTr">
                    <td class="ui_text_rt" width="140">角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_roles">
                                        <!--左边框显示所有权限-->
                                        <c:forEach var="obj" items="${roles}">
                                            <option value="${obj.id}">${obj.name}</option>
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
                                    <select name="ids" multiple="true" class="ui_multiselect01 selected_roles">
                                        <!--左边框显示所有权限-->
                                        <c:forEach var="obj" items="${entity.roles}">
                                            <option value="${obj.id}">${obj.name}</option>
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