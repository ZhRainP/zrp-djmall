package com.dj.mall.admin.web.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.resource.ResourceVOReq;
import com.dj.mall.admin.vo.resource.ResourceVOResp;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.resouce.ResourceApi;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("res")
public class ResourceController {
    @Reference
    private ResourceApi resourceApi;

    /**
     * 父级列表
     * @return
     * @throws Exception
     */
    @GetMapping("resourceList")
    public ResultModel resourceList() throws Exception {
        List<ResourceDTO> resourceList = resourceApi.findList();
        return new ResultModel().success(DozerUtil.mapList(resourceList, ResourceVOResp.class));
    }

    /**
     * 新增资源
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("insert")
    public ResultModel insert(ResourceVOReq resourceVOReq) throws Exception {
        Assert.hasText(resourceVOReq.getResourceName(), "资源名不能为空");
        Assert.hasText(resourceVOReq.getResourceCode(), "编码不能为空");
        Assert.hasText(resourceVOReq.getUrl(), "资源路径不能为空");
        ResourceDTO resourceDTO = DozerUtil.map(resourceVOReq, ResourceDTO.class);
        resourceApi.insertResource(resourceDTO);
        return new ResultModel().success();
    }

    /**
     * 修改资源
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("update")
    public ResultModel update(ResourceVOReq resourceVOReq) throws Exception{
        resourceApi.updateResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel().success();
    }

    /**
     * 删除资源
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("deleteResource")
    public ResultModel deleteResource(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.deleteResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel().success();
    }
}
