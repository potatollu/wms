package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.OrderSaleQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IChartService;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartController {
    @Autowired
    private IChartService chartService;
    @Autowired
    private ISupplierService supplierService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IClientService clientService;

    @RequestMapping("order")
    public String chart(Model model, @ModelAttribute("qo")OrderChartQueryObject qo){
        model.addAttribute("oderChart",chartService.selectOrderChart(qo));
        model.addAttribute("brands",brandService.list());
        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("groupTypeMap",OrderChartQueryObject.groupTypeMap);
        return "chart/orderChart";
    }

    @RequestMapping("sale")
    public String saleChart(Model model, @ModelAttribute("qo")OrderSaleQueryObject qo){
        model.addAttribute("clients",clientService.list());
        model.addAttribute("saleChart",chartService.selectSaleChart(qo));
        model.addAttribute("brands",brandService.list());
        model.addAttribute("groupTypeMap",OrderSaleQueryObject.groupTypeMap);
        return "chart/saleChart";
    }
    @RequestMapping("saleChartByBar")
    public String saleChartByBar(Model model,OrderSaleQueryObject qo){
        List<Map<String, Object>> charts = chartService.selectSaleChart(qo);
        //装分组名称
        List<String> groupType = new ArrayList<>();
        //装总金额
        List<Object> totalAmount = new ArrayList<>();
        for (Map<String, Object> chart : charts) {
            groupType.add(chart.get("groupType").toString());
            totalAmount.add(chart.get("totalAmount").toString());
        }
        model.addAttribute("groupType", JSON.toJSONString(groupType));
        model.addAttribute("totalAmount", JSON.toJSONString(totalAmount));
        return "chart/saleChartByBar";
    }
    @RequestMapping("saleChartByPie")
    public String saleChartByPie(Model model,OrderSaleQueryObject qo){
        List<Map<String, Object>> charts = chartService.selectSaleChart(qo);
        //装分组名称
        List<String> groupTypes = new ArrayList<>();
        //存放报表数据
        List<Map<String,Object>> datas = new ArrayList<>();
        for (Map<String, Object> chart : charts) {
            groupTypes.add(chart.get("groupType").toString());
            Map<String,Object> map = new HashMap<>();
            map.put("value",chart.get("totalAmount"));
            map.put("name",chart.get("groupType"));
            datas.add(map);
            model.addAttribute("groupType",OrderSaleQueryObject.groupTypeMap.get(qo.getGroupType()));
        }
        model.addAttribute("groupTypes",JSON.toJSONString(groupTypes));
        model.addAttribute("datas",JSON.toJSONString(datas));
        return "chart/saleChartByPie";
    }


}
