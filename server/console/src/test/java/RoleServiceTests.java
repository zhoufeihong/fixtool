import com.za.common.dto.ResultDTO;
import com.za.common.utils.BeanExtUtils;
import com.za.console.ConsoleApplication;
import com.za.console.service.RoleService;
import com.za.console.service.dto.RoleAuthDTO;
import com.za.console.service.dto.RoleDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = ConsoleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleServiceTests {

    @Autowired
    RoleService roleService;

    @Test
    @Transactional
    @Rollback(false)
    public void addRoleTest() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("a");
        roleDTO.setCode("aa");
        roleDTO.setStatus(1);
        Assert.isTrue(roleService.addRole(roleDTO).getCode() == 0, "角色添加测试失败");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void removeRoleTest() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(9L);
        Assert.isTrue(roleService.removeRole(roleDTO).getCode() == 0, "角色删除失败");
    }

    @Test
    @Transactional
    public void listRoleTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("管理员");
        System.out.println(result.getData());
        System.out.println(result.getData().size());
        System.out.println(result.getData().toArray(new RoleDTO[result.getData().size()])[0].getRoleAuths());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateRoleTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("管理员");
        RoleDTO role = result.getData().get(0);
        Random rd = new Random();
        role.setName(role.getName() + rd.nextInt(1000));
        roleService.updateRole(role);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void grantAuthorizationTest() {
        ResultDTO<List<RoleDTO>> result = roleService.listRole("管理员");
        RoleDTO role = result.getData().get(0);
        Set<RoleAuthDTO> roleAuthDTOS = BeanExtUtils.copyPropertiesOfList(role.getRoleAuths(), RoleAuthDTO.class);
        role.setRoleAuths(roleAuthDTOS);
        roleService.grantAuthorization(role);
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
