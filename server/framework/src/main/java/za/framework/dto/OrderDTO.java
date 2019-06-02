package za.framework.dto;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;

public class OrderDTO {

    private String direction = Direction.ASC;
    private String[] properties;

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getProperties() {
        if (this.properties != null) {
            return this.properties.clone();
        }
        return new String[]{};
    }

    public void setProperties(String... properties) {
        this.properties = properties;
    }

    public static OrderDTO of(String direction, String... properties) {
        OrderDTO s = new OrderDTO();
        s.setDirection(direction);
        s.setProperties(properties);
        return s;
    }

    public static OrderDTO of(String... properties) {
        OrderDTO s = new OrderDTO();
        s.setProperties(properties);
        return s;
    }

    public Sort toSort() {
        if (StringUtils.isBlank(direction)
                || properties == null
                || properties.length == 0) {
            return Sort.unsorted();
        }
        Sort.Direction directionTemp = this.direction == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(directionTemp, this.properties);
    }

    public static class Direction {
        private Direction() {
        }

        public static final String DESC = "desc";
        public static final String ASC = "asc";
    }

}