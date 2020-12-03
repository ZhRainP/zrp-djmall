package com.dj.p2p.controller;

import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;
import com.dj.p2p.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ldm
 * @since 2020-11-26
 */
@RestController
@RequestMapping(value = "/dict/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "字典API")
@Slf4j
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping("selectDictByParentCode")
    @ApiOperation(value = "返回注册字典数据（后台使用）")
    @ApiImplicitParam(name = "parentCode", value = "字典数据父级CODE")
    public List<Dict> selectDictByParentCode(String parentCode) throws Exception {
        log.info("进入根据父级CODE查询字典数据方法，parentCode:{}", parentCode);
        Assert.hasText(parentCode, "父级CODE不能为空");
        List<Dict> dictList = dictService.selectDictByParentCode(parentCode);
        log.info("结束根据父级CODE查询字典数据方法");
        return dictList;
    }

    @PostMapping("returnDictMessage")
    @ApiOperation(value = "返回字典数据")
    public ResultModel returnDictMessage() throws Exception {
        log.info("进入返回字典数据方法");
        ResultModel resultModel = dictService.returnDictMessage();
        log.info("结束返回字典数据方法");
        return resultModel;
    }

}
