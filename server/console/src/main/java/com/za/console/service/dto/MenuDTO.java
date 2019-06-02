package com.za.console.service.dto;

import com.za.common.utils.BeanExtUtils;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Data
public class MenuDTO {

    public final static Long ROOT_ID = 0L;

    public MenuDTO() {

    }

    public MenuDTO(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private String url;
    private String status;
    private String isLeaf;
    private String options;
    private Integer rankIndex;
    private List<MenuDTO> childMenus;

    /**
     * 加载子菜单，深度优先
     * @param moduleDTOS
     * @return
     */
    public MenuDTO loadChildMenus(List<ModuleDTO> moduleDTOS) {
        if (moduleDTOS == null || moduleDTOS.size() == 0) {
            return this;
        }
        //使用栈遍历构建树
        final Stack<MenuDTO> stackTemp = new Stack<>();
        stackTemp.push(this);
        while (!stackTemp.empty()) {
            //当前查找的菜单节点
            MenuDTO currentMenu = stackTemp.pop();
            //查找子菜单
            moduleDTOS.stream().forEach(f -> {
                //判断为当前查找菜单的子节点
                if (f.getParentId() == currentMenu.getId()) {
                    if (currentMenu.childMenus == null) {
                        currentMenu.childMenus = new LinkedList<>();
                    }
                    //转换
                    MenuDTO menuTemp = BeanExtUtils.copyProperties(f, MenuDTO.class);
                    //入栈，在while (!stackTemp.empty())出栈->查找子菜单
                    stackTemp.push(menuTemp);
                    //添加到当前菜单子节点
                    currentMenu.childMenus.add(menuTemp);
                }
            });
            //菜单排序
            if (currentMenu.childMenus != null) {
                currentMenu.childMenus.sort((c1, c2) -> {
                    if (c1.getRankIndex() > c2.getRankIndex()) {
                        return 1;
                    }
                    if (c1.getRankIndex() < c2.getRankIndex()) {
                        return -1;
                    }
                    return 0;
                });
            }
        }
        return this;
    }

    /**
     *  输出所有菜单，广度优先
     * @return
     */
    public List<MenuDTO> toMenuDTOList() {
        //广度优先
        //结果集
        final List<MenuDTO> resultList = new LinkedList<>();
        //队列
        final Queue<MenuDTO> queueTemp = new LinkedList<>();
        queueTemp.add(this);
        while (!queueTemp.isEmpty()) {
            MenuDTO menuDTOTemp = queueTemp.poll();
            resultList.add(menuDTOTemp);
            if (menuDTOTemp.childMenus != null) {
                menuDTOTemp.childMenus.stream().forEach(f -> queueTemp.offer(f));
            }
        }
        return resultList;
    }
}
