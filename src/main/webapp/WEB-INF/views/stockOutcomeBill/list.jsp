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
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-商品订单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/stockOutcomeBill/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" var="beginDate" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate value="${qo.endDate}" var="endDate" pattern="yyyy-MM-dd"/>
                        <input type="text" class="ui_input_txt Wdate" name="beginDate"
                               onclick="WdatePicker();" value="${beginDate}"/> ~
                        <input type="text" class="ui_input_txt Wdate" name="endDate"
                               onclick="WdatePicker();" value="${endDate}"/>
                        仓库
                        <select id="depotId" class="ui_select01" name="depotId">
                            <option value="-1">全部仓库</option>
                            <c:forEach items="${depots}" var="depot">
                                <option value="${depot.id}" ${qo.depotId==depot.id?"selected":""}>${depot.name}</option>
                            </c:forEach>
                        </select>
                        客户
                        <select id="clientId" class="ui_select01" name="clientId">
                            <option value="-1">全部客户</option>
                            <c:forEach items="${clients}" var="client">
                                <option value="${client.id}" ${qo.clientId==client.id?"selected":""}>${client.name}</option>
                            </c:forEach>
                        </select>
                        状态
                        <select id="status" class="ui_select01" name="status">
                            <option value="-1">全部</option>
                            <option value="0">待审核</option>
                            <option value="1">已审核</option>
                        </select>
                        <script>
                            $("#status option[value=${qo.status}]").prop("selected", true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/stockOutcomeBill/input.do"
                        />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>入库单号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>用户</th>
                        <th>总数量</th>
                        <th>总金额</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${num.count}</td>
                            <td><fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd"/></td>
                            <td>${entity.depot.name}</td>
                            <td>${entity.client.name}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                            <td>${entity.inputUser.name}</td>
                            <td>${entity.auditor.name}</td>
                            <td>
                                <c:if test="${entity.status==0}">
                                    <span style="color: red;">未审核</span>
                                </c:if>
                                <c:if test="${entity.status==1}">
                                    <span style="color: green;">已审核</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${entity.status == 0}">
                                    <a href="javascript:"
                                       data-url="/stockOutcomeBill/audit.do?id=${entity.id}"
                                       class="btn_audit" data-obj="stockOutcomeBill"
                                    >审核</a>
                                    <a href="/stockOutcomeBill/input.do?id=${entity.id}">编辑</a>
                                    <a href="javascript:"
                                       data-url="/stockOutcomeBill/delete.do?id=${entity.id}"
                                       class="btn_delete" data-obj="stockOutcomeBill"
                                    >删除</a>
                                </c:if>
                                <c:if test="${entity.status == 1}">
                                    <a href="/stockOutcomeBill/show.do?id=${entity.id}">查看</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%@include file="/WEB-INF/views/common/page.jsp" %>
        </div>
    </div>
</form>
</body>
</html>
