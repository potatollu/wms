<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/ajaxForm/ajaxForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $("input").prop("readonly",true);
            //======异步提交表单=====
            $("#editForm").ajaxForm(function (data) {
                var obj = $("#editForm").data("obj");
                if (data.success){
                    showDialog("保存成功!",function () {
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
<form id="editForm" action="/stockOutcomeBill/saveOrUpdate.do" method="post"  data-obj="stockOutcomeBill">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">商品订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">入库单编码</td>
                    <td class="ui_text_lt">
                        <input type="text" name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <input type="text" name="depot.name" value="${entity.depot.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">客户</td>
                    <td class="ui_text_lt">
                        <input type="text" name="client.name" value="${entity.client.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <ftm:formatDate value="${entity.vdate}" var="vdate" pattern="yyyy-MM-dd"/>
                        <input name="vdate" readonly="readonly" value="${vdate}" onclick="WdatePicker({minDate:new Date()});" class="ui_input_txt02 Wdate"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0">
                            <thead>
                            <tr>
                                <th width="170">货品</th>
                                <th width="100">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="100">金额小计</th>
                                <th width="180">备注</th>
                                <th width="120"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:if test="${not empty entity.id}">
                                <c:forEach var="item" items="${entity.items}">
                                    <tr>
                                        <td>
                                            <input disabled="true" value="${item.product.name}" class="ui_input_txt02" tag="name"/>
                                            <!--把明细保存到集合的第一个元素里-->
                                            <input type="hidden" name="items[0].product.id" tag="pid"/>
                                        </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input type="text" tag="salePrice" name="items[0].salePrice" value="${item.salePrice}"
                                               class="ui_input_txt"/></td>
                                    <td><input  type="text" tag="number" name="items[0].number" value="${item.number}"
                                               class="ui_input_txt"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input  type="text" tag="remark" name="items[0].remark" value="${item.remark}" class="ui_input_txt01"/></td>
                                </tr>
                            </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="返回列表" class="ui_input_btn01 btn_submit"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
