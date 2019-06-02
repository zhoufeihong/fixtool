import za.framework.dto.PageRequestDTO;
import za.framework.dto.PageResultDTO;
import com.za.console.ConsoleApplication;
import com.za.console.service.ModuleService;
import com.za.console.service.dto.MenuDTO;
import com.za.console.service.dto.ModuleDTO;
import org.junit.After;
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
import java.util.Random;

@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = ConsoleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModulServiceTests {
    @Autowired
    ModuleService moduleService;

    @Test
    @Transactional
    @Rollback(false)
    public void addModuleTest() {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setName("testName");
        moduleDTO.setOptions("testOptions");
        moduleDTO.setUrl("testUrl");
        moduleDTO.setStatus(1);
        moduleDTO.setParentId(1L);
        moduleDTO.setIsLeaf(1);
        Assert.assertTrue(moduleService.addModule(moduleDTO).getCode() == 1);
    }


    @Test
    @Transactional
    @Rollback(false)
    public void updateModuleTest() {
        List<ModuleDTO> listResult = moduleService.listModuleDTO("").getData();
        listResult.stream().forEach(f -> {
            if (f.getName().equals("testName")) {
                String testOptions = "testOptions" + (new Random()).nextInt(1000);
                f.setOptions(testOptions);
                Assert.assertTrue(moduleService.updateModule(f).getCode() == 1);
                Assert.assertTrue(moduleService.getModuleDTO(f.getId()).getData().getOptions() == testOptions);
            }
        });
    }

    @Test
    @Transactional
    @Rollback(false)
    public void listPageModuleTest() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setSize(2);
        pageRequestDTO.setPage(0);
        PageResultDTO<List<ModuleDTO>> listResult = moduleService.listModuleDTO("",pageRequestDTO);
        Assert.assertNotNull(listResult);
        Assert.assertEquals("size must be 2",listResult.getData().size(),2);
        Assert.assertTrue(listResult.getTotal() > 2);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void removeModuleTest() {
        List<ModuleDTO> listResult = moduleService.listModuleDTO("").getData();
        listResult.stream().forEach(f -> {
            if (f.getName().equals("testName")) {
                Assert.assertTrue(moduleService.removeModule(f).getCode() == 1);
            }
        });
    }

    @Test
    @Transactional
    public void toMenuListTest()
    {
        MenuDTO menuDTO = new MenuDTO(MenuDTO.ROOT_ID);
        List<ModuleDTO> moduleDTOS =  moduleService.listModuleDTO("").getData();
        menuDTO.loadChildMenus(moduleDTOS);
        List<MenuDTO> menuDTOS = menuDTO.toMenuDTOList();
        Assert.assertEquals(menuDTOS.size(),moduleDTOS.size() + 1L);
    }

    @After
    public void after() {

    }
}
