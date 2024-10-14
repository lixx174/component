package com.jinx.component.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页响应模型
 *
 * @author jinx
 */
@Getter
@Setter
@AllArgsConstructor
public class PageReply<T> implements Page<T> {

    /**
     * 当前页码
     */
    private Number page;
    /**
     * 分页大小
     */
    private Number size;
    /**
     * 总页数
     */
    private Number pages;
    /**
     * 数据
     */
    private List<T> content;

    public static <T> PageReply<T> of(Number page, Number size, Number pages, List<T> records) {
        return new PageReply<>(page, size, pages, records);
    }
}
