package za.framework.dto;

import com.za.common.dto.ResultDTO;
import com.za.common.utils.BeanExtUtils;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageResultDTO<T> extends ResultDTO<T> {

    private Integer total;

    /**
     * @param msg
     * @param data
     * @param total
     * @param <T>
     * @return
     */
    public static <T> PageResultDTO<T> pageSuccess(String msg, T data, Integer total) {
        PageResultDTO<T> result = new PageResultDTO<>();
        result.setCode(1);
        result.setData(data);
        if (msg == null) {
            msg = "执行成功";
        }
        result.setTotal(total);
        result.setMsg(msg);
        return result;
    }

    /**
     * @param page
     * @return
     */
    public static PageResultDTO pageSuccess(Page<?> page) {
        PageResultDTO<?> pageResultDTO = pageSuccess("", page.getContent(), page.getTotalPages());
        return setElsePageResult(page, pageResultDTO);
    }

    /**
     * @param page
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageResultDTO pageSuccess(Page<?> page, Class<T> targetClass) {
        PageResultDTO pageResultDTO = pageSuccess("", BeanExtUtils.copyPropertiesOfList(page.getContent(), targetClass), page.getTotalPages());
        return setElsePageResult(page,pageResultDTO);
    }

    private static PageResultDTO<?> setElsePageResult(Page<?> page, PageResultDTO<?> result) {
        result.total = (int) page.getTotalElements();
        return result;
    }

}

