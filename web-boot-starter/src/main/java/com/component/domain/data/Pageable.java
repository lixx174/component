package com.component.domain.data;

/**
 * 分页领域接口  领域层使用该接口交互
 *
 * @author Jinx
 */
public interface Pageable {

    /**
     * 当前页数
     *
     * @return 页数 limit p
     */
    Number getPage();

    /**
     * 每页数据条数
     *
     * @return 条数 limit 10, p
     */
    Number getSize();
}
