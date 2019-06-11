import com.za.common.dto.ResultDTO;
import com.za.console.ConsoleApplication;
import com.za.console.service.PermissionResourceService;
import com.za.console.service.dto.PermissionResourceDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = ConsoleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionResourceServiceTest {

    @Autowired
    PermissionResourceService permissionResourceService;

    @Test
    @Transactional
    public void queryPermissionResourceTest() {
        ResultDTO<List<PermissionResourceDTO>> resultDTO = permissionResourceService.queryPermissionResource(1L);
        Assert.assertTrue(resultDTO.getCode() == ResultDTO.SUCCESS_CODE);
    }

    @Test
    @Transactional
    public void getPermissionResourceTest() {
        ResultDTO<PermissionResourceDTO> resultDTO = permissionResourceService.getPermissionResource(1L);
        Assert.assertTrue(resultDTO.getCode() == ResultDTO.SUCCESS_CODE);
    }
}
