import com.za.common.dto.ResultDTO;
import com.za.console.service.RoleService;
import com.za.console.service.dto.PermissionResourceDTO;
import com.za.console.service.dto.RoleDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@EnableTransactionManagement
public class RoleServiceTests extends AbstractConsoleTest {

    @Autowired
    RoleService roleService;

    @Test
    @Transactional
    @Rollback(false)
    public void addRoleTest() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("AbstractAuditingPo");
        roleDTO.setCode("aa");
        roleDTO.setStatus(1);
        Assert.isTrue(roleService.addRole(roleDTO).getSuccess(), "角色添加测试失败");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void removeRoleTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("AbstractAuditingPo");
        Assert.isTrue(result.getSuccess(), "roleService.listRole失败");
        if (result.getData().size() > 0) {
            RoleDTO roleDTO = result.getData().get(0);
            Assert.isTrue(roleService.removeRole(roleDTO).getSuccess(), "角色删除失败");
        }
    }

    @Test
    @Transactional
    public void listRoleTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("管理员");
        System.out.println(result.getData());
        System.out.println(result.getData().size());
        System.out.println(result.getData().toArray(new RoleDTO[result.getData().size()])[0].getPermissionResources());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateRoleTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("代理");
        Assert.isTrue(result.getSuccess(), "roleService.listRole失败");
        if (result.getData().size() > 0) {
            RoleDTO role = result.getData().get(0);
            Random rd = new Random();
            role.setName(role.getName() + rd.nextInt(1000));
            roleService.updateRole(role);
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void grantAuthorizationTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("管理员");
        RoleDTO role = roleService.getRoleWithPermission(result.getData().get(0).getId()).getData();
        Set<PermissionResourceDTO> permissionResourceDTOS = new HashSet<>(32);
        PermissionResourceDTO permissionResourceDTO = new PermissionResourceDTO();
        permissionResourceDTO.setCode("001001001");
        permissionResourceDTOS.add(permissionResourceDTO);
        permissionResourceDTO = new PermissionResourceDTO();
        permissionResourceDTO.setCode("001001002");
        permissionResourceDTOS.add(permissionResourceDTO);
        role.setPermissionResources(permissionResourceDTOS);
        roleService.grantAuthorization(role);
    }

    @Test
    @Transactional
    public void queryPermissionResourceTest() {
        ResultDTO<List<PermissionResourceDTO>> resultDTO = roleService.queryPermissionResource(1L);
        org.junit.Assert.assertTrue(resultDTO.getSuccess());
    }

    @Before
    public void before() {
        System.out.println("before...");
    }

    @After
    public void after() {
        System.out.println("after...");
    }
}
