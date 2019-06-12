package za.framework.dto;

import com.za.common.dto.ResultDTO;
import com.za.common.utils.BeanExtUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Max;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
     * @param
     * @return
     */
    public static <I, T> PageResultDTO<List<T>> pageSuccess(Page<I> page, Class<T> targetClass) {
        PageResultDTO<List<T>> pageResultDTO = pageSuccess("", BeanExtUtils.copyPropertiesOfList(page.getContent(), targetClass), page.getTotalPages());
        return setElsePageResult(page, pageResultDTO);
    }

    private static <T> PageResultDTO<T> setElsePageResult(Page<?> page, PageResultDTO<T> result) {
        result.total = (int) page.getTotalElements();
        return result;
    }

}

