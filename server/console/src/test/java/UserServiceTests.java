import com.za.common.dto.ResultDTO;
import com.za.console.ConsoleApplication;
import com.za.console.entity.RolePO;
import com.za.console.entity.UserPO;
import com.za.console.reponsitory.RoleReponsitory;
import com.za.console.reponsitory.UserReponsitory;
import com.za.console.service.RoleService;
import com.za.console.service.UserService;
import com.za.console.service.dto.RoleDTO;
import com.za.console.service.dto.UserDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = ConsoleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    @Autowired
    UserReponsitory userReponsitory;

    @Autowired
    RoleReponsitory roleReponsitory;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void getUserTest() {
        UserPO user = userReponsitory.getOne(1L);
        Set<RolePO> roles = user.getRoles();
        Assert.assertNotNull(user);
        Assert.assertTrue(roles.size() > 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void addRoleTest() {
        UserDTO user = userService.getUser(1L).getData();
        RoleDTO r = user.getRoles().stream().filter(f -> f.getId() == 2).findFirst().orElse(null);
        if(r != null) {
            user.getRoles().remove(r);
        }
        RoleDTO role = roleService.getRole(2L).getData();
        ResultDTO<UserDTO> userODto = userService.getUser(1L);
        user.getRoles().add(role);
        userService.updateRole(userODto.getData());
    }

    @Test
    @Transactional
    public void listUserTest()
    {
       ResultDTO<List<UserDTO>> userDtoList =  userService.listUser("c");
       Assert.assertTrue(userDtoList.getData().size() > 0);
       ResultDTO result = userService.getToken("admin","123456");
       Assert.assertEquals(result.getCode(),1);
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
