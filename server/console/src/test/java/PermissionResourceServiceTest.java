import com.za.common.dto.ResultDTO;
import com.za.console.service.PermissionResourceService;
import com.za.console.service.dto.PermissionResourceDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableTransactionManagement
public class PermissionResourceServiceTest extends AbstractConsoleTest {

    @Autowired
    PermissionResourceService permissionResourceService;

    @Test
    @Transactional
    public void getPermissionResourceTest() {
        ResultDTO<PermissionResourceDTO> resultDTO = permissionResourceService.getPermissionResource(1L);
        Assert.assertTrue(resultDTO.getSuccess());
    }
}
