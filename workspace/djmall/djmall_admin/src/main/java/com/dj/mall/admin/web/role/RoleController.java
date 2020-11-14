package com.dj.mall.admin.web.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.role.RoleVOReq;
import com.dj.mall.admin.vo.role.RoleVOResp;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.dto.ZTreeData;
import com.dj.mall.autr.api.role.RoleApi;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    @Reference
    private RoleApi roleApi;

    /**
     * 角色列表
     * @return
     * @throws Exception
     */
    @GetMapping("roleList")
    public ResultModel roleList() throws Exception {
        List<RoleDTO> roleDTOList = roleApi.findList();
        return new ResultModel().success(DozerUtil.mapList(roleDTOList, RoleVOResp.class));
    }

    /**
     * 添加角色
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("insert")
    public ResultModel insert(RoleVOReq roleVOReq) throws Exception {
        Assert.hasText(roleVOReq.getRoleName(), "角色名不能为空");
        RoleDTO roleDTO = DozerUtil.map(roleVOReq, RoleDTO.class);
        roleApi.insertRole(roleDTO);
        return new ResultModel().success();
    }

    /**
     * 角色修改
     * @param roleVOResp
     * @return
     * @throws Exception
     */
    @RequestMapping("update")
    public ResultModel updateRole(RoleVOResp roleVOResp) throws Exception{
        roleApi.updateRole(DozerUtil.map(roleVOResp, RoleDTO.class));
        return new ResultModel().success();
    }

    /**
     * 获取角色关联资源
     * @param id 角色ID
     * @return
     * @throws Exception
     */
    @GetMapping("getRoleResource")
    public ResultModel getRoleResource(Integer id) throws Exception {
        List<ZTreeData> zTreeDataList = roleApi.getRoleResource(id);
        return new ResultModel().success(zTreeDataList);
    }

    /**
     * 保存关联的资源
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("savaRoleResource")
    public ResultModel savaRoleResource(RoleVOReq roleVOReq) throws Exception {
        roleApi.savaRoleResource(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel().success();
    }
}
