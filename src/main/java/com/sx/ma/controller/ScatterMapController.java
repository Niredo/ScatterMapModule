package com.sx.ma.controller;

import com.sx.ma.service.BscLabService;
import com.sx.ma.service.BscTestitemService;
import com.sx.ma.service.ScatterMapService;
import com.sx.ma.service.SelectionRangeMapService;
import com.sx.ma.utils.Constant;
import com.sx.ma.utils.ResponseResult;
import com.sx.ma.vo.RequestVo;
import com.sx.ma.vo.TestItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 点状图操作处理
 *
 * @author nhh
 */
@RestController
@Slf4j
@RequestMapping("/scatterMap")
public class ScatterMapController {
    @Autowired
    private BscTestitemService bscTestitemService;
    @Autowired
    private ScatterMapService scatterMapService;
    @Autowired
    private BscLabService bscLabService;
    @Autowired
    private SelectionRangeMapService selectionRangeMapService;

    /**
     * 页面渲染初始化，加载测试项目集合
     *
     * @return ResponseResult
     */
    @GetMapping("/testItemList")
    public ResponseResult testItemList() {
        List<TestItemVo> testItemList = bscTestitemService.selectAll();
        return ResponseResult.okResult(testItemList);
    }

    /**
     * 获取对应数据库下的所有仪器列表
     *
     * @param dataBase 表名
     * @return 仪器集合
     */
    @PostMapping("/instrumentNameList")
    public ResponseResult instrumentNameList(@RequestBody String dataBase) {
        String dataBaseName = Constant.PREFIX_DATABASE + dataBase;
        List<String> instrumentNameList = scatterMapService.selectInstrumentNameList(dataBaseName);
        return ResponseResult.okResult(instrumentNameList);
    }

    /**
     * 生成点状图
     *
     * @param requestVo 请求体VO
     * @return 统一请求返回体
     */
    @PostMapping("/map")
    public ResponseResult map(@RequestBody RequestVo requestVo) {
        return scatterMapService.map(requestVo);
    }

    /**
     * 截断范围点状图
     *
     * @param requestVo 请求体VO
     * @return 统一请求返回体
     */
    @PostMapping("/selectionRangeMap")
    public ResponseResult selectionRangeMap(@RequestBody RequestVo requestVo) {
        return selectionRangeMapService.map(requestVo);
    }
}
