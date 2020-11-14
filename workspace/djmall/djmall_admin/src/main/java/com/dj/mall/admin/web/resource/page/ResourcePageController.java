package com.dj.mall.admin.web.resource.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.resource.ResourceVOResp;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.resouce.ResourceApi;
import com.dj.mall.common.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dj.mall.common.constant.CodeConstant.RESOURCE_MANAGER_CODE;

@Controller
@RequestMapping("res")
public class ResourcePageController {
    @Reference
    private ResourceApi resourceApi;
    /**
     * 去资源列表
     * @return
     */
    @RequestMapping("toList")
    @RequiresPermissions(RESOURCE_MANAGER_CODE)
    public String toList() {
        return "resource/resource_list";
    }

    /**
     * 去添加
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(Integer pId, ModelMap model) {
        ResourceDTO resourceDTO = resourceApi.findResourceById(pId);
        model.put("pId", pId);
        model.put("resource", DozerUtil.map(resourceDTO, ResourceVOResp.class));
        return "resource/add";
    }
}
