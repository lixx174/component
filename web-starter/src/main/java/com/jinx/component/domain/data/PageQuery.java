package com.jinx.component.domain.data;

import com.jinx.component.domain.data.Pageable;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求基础模型
 *
 * @author jinx
 */
@Getter
@Setter
public class PageQuery implements Pageable {

    /**
     * 当前页码(默认1)
     */
    private Number page = 1;
    /**
     * 分页大小(默认10)
     */
    private Number size = 10;
}
