package za.framework.dto;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;

@Data
public class PageRequestDTO {
    private static final String START_REGEX = "^((\\+)|(\\-))";
    private Integer page;
    private Integer size;
    private OrderDTO orderDTO;

    public PageRequest toPageRequest() {
        if (orderDTO != null) {
            return PageRequest.of(this.page, this.size, this.orderDTO.toSort());
        }
        return PageRequest.of(page, size);
    }

    public static PageRequestDTO of(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        PageRequestDTO result = new PageRequestDTO();
        result.setPage(page);
        result.setSize(size);
        return result;
    }

    public static PageRequestDTO ofAsc(Integer page, Integer size, String... properties) {
        return getPageRequestDTO(page, size, properties, OrderDTO.Direction.ASC);
    }

    public static PageRequestDTO ofDesc(Integer page, Integer size, String... properties) {
        return getPageRequestDTO(page, size, properties, OrderDTO.Direction.DESC);
    }

    public static PageRequestDTO getPageRequestDTO(Integer page, Integer size, String[] properties, String direction) {

        PageRequestDTO result = of(page, size);
        if (properties != null && StringUtils.isNotBlank(properties[0])) {
            OrderDTO orderDTO = OrderDTO.of(direction, properties);
            result.setOrderDTO(orderDTO);
        }
        return result;
    }

    public static PageRequestDTO ofOperation(Integer page, Integer size, String properties) {
        String direction = OrderDTO.Direction.ASC;
        if (StringUtils.isBlank(properties) || ((properties.length() < 2) && properties.matches(START_REGEX))) {
            return of(page, size);
        }
        if (properties.startsWith("-")) {
            direction = OrderDTO.Direction.DESC;
        }
        if (properties.startsWith("+") || properties.startsWith("-")) {
            properties = properties.replaceFirst(START_REGEX, "");
        }
        return getPageRequestDTO(page, size, new String[]{properties}, direction);
    }
}
