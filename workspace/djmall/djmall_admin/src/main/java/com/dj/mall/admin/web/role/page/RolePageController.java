package com.dj.mall.admin.web.role.page;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.role.RoleVOResp;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.role.RoleApi;
import com.dj.mall.common.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dj.mall.common.constant.CodeConstant.ROLE_MANAGER_CODE;


@Controller
@RequestMapping("role")
public class RolePageController {
    @Reference
    private RoleApi roleApi;
    /**
     * 去角色列表
     * @return
     */
    @RequestMapping("toList")
    @RequiresPermissions(ROLE_MANAGER_CODE)
    public String toList(){
        return "role/role_list";
    }

    /**
     * 去添加
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(){
        return "role/add_role";
    }


    /**
     * 去修改
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, ModelMap model){
        RoleDTO roleDTO = roleApi.findRoleById(id);
        model.put("role", DozerUtil.map(roleDTO, RoleVOResp.class));
        return "role/update_role";
    }

    /**
     * 关联资源
     * @param id 角色ID
     * @param model
     * @return
     */
    @RequestMapping("toRoleResource")
    public String toRoleResource(Integer id, ModelMap model){
        model.put("id", id);
        return "role/role_resource";
    }
}
