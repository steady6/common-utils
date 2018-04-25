package com.framework.common.controller;

import com.framework.common.utils.GenExcelUtil;
import com.framework.common.entity.ExcelItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/3/29
 * @description
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @RequestMapping("export")
    public void export(@RequestParam("name") String name,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        List<ExcelItem> list = new ArrayList<>();
        ExcelItem item = new ExcelItem();
        item.setName("kk");
        item.setAge("23");
        list.add(item);
        ExcelItem item1 = new ExcelItem();
        item1.setName("kk1");
        item1.setAge("24");
        list.add(item1);
                GenExcelUtil.genExcel("导出测试-" + name , "sheet名字: " + name, list,
                        new String[]{"姓名", "年龄"},
                        new String[]{"name", "age"},
                        response);
            }


}
