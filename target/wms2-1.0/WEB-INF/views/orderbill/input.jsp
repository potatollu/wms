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
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //点击放大镜打开窗口页面
            $(".searchproduct").click(function () {
                //获取到放大镜所在的行
                var currentTr =  $(this).closest("tr");
                //发送请求,设置窗口属性
                $.artDialog.open("/product/selectProductList.do",{
                    id:'selectProduct',
                    title:'选择商品',
                    width:1000,
                    height:500,
                    left:'50%',
                    opacity:0.1,
                    lock:true,
                    resize:false,
                    close:function () {
                        //接收子窗口返回来的数据
                        var productInfo = $.artDialog.data("productInfo");
                        if(productInfo){
                            //找到每一个input,将数据填充到明细表中
                            currentTr.find("[tag='name']").val(productInfo.name);
                            currentTr.find("[tag='pid']").val(productInfo.id);
                            currentTr.find("[tag='brand']").text(productInfo.brandName);
                            currentTr.find("[tag='costPrice']").val(productInfo.costPrice);
                        }
                        //清空商品信息
                        $.artDialog.removeData("productInfo");
                    }
                });
            });
            //为价格和数量的输入框绑定失去焦点事件
            //通过属性过滤选择器
            $("[tag='costPrice'],[tag='number']").blur(function () {
                var tr = $(this).closest("tr");
                var costPrice = parseFloat(tr.find("[tag='costPrice']").val()) || 0;
                var number = parseFloat(tr.find("[tag='number']").val()) || 0;
                var amount = (costPrice*number).toFixed(2);
                tr.find("[tag='amount']").text(amount);

            });
            //添加明细
            $(".appendRow").click(function () {
                //克隆表格的第一行
                var newTr = $("#edit_table_body tr:first").clone(true);
                //先设置为空
                newTr.find("[tag='name']").val("");
                newTr.find("[tag='pid']").val("");
                newTr.find("[tag='brand']").text("");
                newTr.find("[tag='costPrice']").val("");
                newTr.find("[tag='number']").val("");
                newTr.find("[tag='remark']").val("");
                newTr.find("[tag='amout']").text("");
                newTr.appendTo($("#edit_table_body"));
            });

            //保存多条明细信息
            $("#editForm").submit(function () {
                $.each($("#edit_table_body tr"),function (index,val) {
                    //console.log(val);
                    //提交表单到的时候修改每一行的name属性值
                    $(val).find("[tag='pid']").prop("name","items["+index+"].product.id");
                    $(val).find("[tag='costPrice']").prop("name","items["+index+"].costPrice");
                    $(val).find("[tag='number']").prop("name","items["+index+"].number");
                    $(val).find("[tag='remark']").prop("name","items["+index+"].remark");
                })
            });
            //删除明细
            $(".removeItem").click(function () {
                //只有最后一行明细的时候就不用删了
                if($("#edit_table_body tr").size() > 1){
                    $(this).closest("tr").remove();
                }else {
                    var firstTr = $("#edit_table_body tr:first");
                    firstTr.find("[tag='name']").val("");
                    firstTr.find("[tag='pid']").val("");
                    firstTr.find("[tag='brand']").text("");
                    firstTr.find("[tag='costPrice']").val("");
                    firstTr.find("[tag='number']").val("");
                    firstTr.find("[tag='remark']").val("");
                    firstTr.find("[tag='amout']").text("");
                }
            });
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
<form id="editForm" action="/orderBill/saveOrUpdate.do" method="post"  data-obj="orderBill">
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
                    <td class="ui_text_rt" width="140">采购单编码</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <select id="supplierId" class="ui_select03" name="supplier.id">
                            <c:forEach var="supplier" items="${suppliers}">
                                <option value="${supplier.id}" ${entity.supplier.id==supplier.id ?"selected='selected'":""}>${supplier.name}</option>
                            </c:forEach>
                        </select>
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
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
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
                            <c:if test="${empty entity.id}">
                                <tr>
                                    <!--放大镜所在的行-->
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <!--把明细保存到集合的第一个元素里-->
                                        <input type="hidden" name="items[0].product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><input tag="costPrice" name="items[0].costPrice"
                                               class="ui_input_txt"/></td>
                                    <td><input tag="number" name="items[0].number"
                                               class="ui_input_txt"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><input tag="remark" name="items[0].remark" class="ui_input_txt01"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty entity.id}">
                                <c:forEach var="item" items="${entity.items}">
                                    <tr>
                                        <!--放大镜所在的行-->
                                        <td>
                                            <input disabled="true" readonly="true" value="${item.product.name}" class="ui_input_txt02" tag="name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <!--把明细保存到集合的第一个元素里-->
                                        <input type="hidden" name="items[0].product.id" value="${item.product.id}" tag="pid"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="costPrice" name="items[0].costPrice" value="${item.costPrice}"
                                               class="ui_input_txt"/></td>
                                    <td><input tag="number" name="items[0].number" value="${item.number}"
                                               class="ui_input_txt"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" name="items[0].remark" value="${item.remark}" class="ui_input_txt01"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
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
