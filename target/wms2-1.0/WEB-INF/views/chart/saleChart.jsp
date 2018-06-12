<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $(".chart").click(function () {
                console.log($(this).data("url"));
                var param = $("#searchForm").serialize();
                $.artDialog.open($(this).data("url")+"?"+param,{
                    id:'saleCharts',
                    title:'销售报表',
                    width:1100,
                    height:500,
                    left:'50%',
                    opacity:0.1,
                    lock:true,
                    resize:false
                });
            });
        });
    </script>
    <title>WMS-商品订单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/chart/sale.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" var="beginDate" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate value="${qo.endDate}" var="endDate" pattern="yyyy-MM-dd"/>
                        <input type="text" class="ui_input_txt02 Wdate" name="beginDate"
                               onclick="WdatePicker();" value="${beginDate}"/> ~
                        <input type="text" class="ui_input_txt02 Wdate" name="endDate"
                               onclick="WdatePicker();" value="${endDate}"/>
                        货品名称/编码
                        <input type="text" class="ui_input_txt01" name="keyword" value="${qo.keyword}"/>
                        客户
                        <select id="clientId" class="ui_select01" name="clientId">
                            <option value="-1">全部供应商</option>
                            <c:forEach items="${clients}" var="client">
                                <option value="${client.id}" ${qo.clientId==client.id?"selected":""}>${client.name}</option>
                            </c:forEach>
                        </select>
                        品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}" ${qo.brandId==brand.id?"selected":""}>${brand.name}</option>
                            </c:forEach>
                        </select>
                        类型
                        <select id="groupType" class="ui_select01" name="groupType">
                            <c:forEach items="${groupTypeMap}" var="groupType">
                                <option value="${groupType.key}" ${qo.groupType==groupType.key?"selected":""}>${groupType.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="柱状图" class="left2right chart"
                               data-url="/chart/saleChartByBar.do"/>
                        <input type="button" value="饼状图" class="left2right chart"
                               data-url="/chart/saleChartByPie.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>分组类型</th>
                        <th>销售数量</th>
                        <th>销售总额</th>
                        <th>利润</th>
                    </tr>
                    <c:forEach var="entity" items="${saleChart}">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${entity.groupType}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                            <td>${entity.profit}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
