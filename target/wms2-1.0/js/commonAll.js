//添加禁用数组参数添加括号
$.ajaxSettings.traditional = true
//======弹出对话框=====
function showDialog(msg,ok,cancel) {
    $.dialog({
        title:"温馨提示",
        icon:"face-smile",
        content:msg,
        lock:true,
        ok:ok || true,
        cancel:cancel
    });
}

$(function () {
    //======新增按钮======
    $(".btn_input").click(function () {
        console.log($(this).data("url"));
        var url = $(this).data("url");
        location.href = url;
    });
    //=====页面跳转======
    $(".btn_page").click(function () {
        //获取当前页的值
        var pageNo = $(this).data("page") || $(":text[name='currentPage']").val();
        //点击按钮时会跳转,要重新设置当前页的值
        $(":text[name='currentPage']").val(pageNo);
        //提交表单
        $("#searchForm").submit();
    });
    //设置页面容量
    $("#pageSize").change(function () {
        //onchange已经改变currentPage的值了
        //先将当前页改为第一页
        $(":text[name='currentPage']").val(1);
        $("#searchForm").submit();
    });
    //========弹出框=========
    //获取所有删除的超链接,绑定事件
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        var obj = $(this).data("obj");
        showDialog("确认删除",function () {
            //发送请求
            $.get(url,function (data) {
                if(data.success){
                    showDialog("删除成功",function () {
                        location.href = "/"+obj+"/query.do"
                    });
                }else{
                    showDialog(data.msg);
                }
            },"json");
        },true);
    });

    $(".btn_audit").click(function () {
        var url = $(this).data("url");
        var obj = $(this).data("obj");
        showDialog("确认审核",function () {
            //发送请求
            $.get(url,function (data) {
                if(data.success){
                    showDialog("审核成功",function () {
                        location.href = "/"+obj+"/query.do"
                    });
                }else{
                    showDialog(data.msg);
                }
            },"json");
        },true);
    });

    //========table鼠标悬停换色=======
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });
});
