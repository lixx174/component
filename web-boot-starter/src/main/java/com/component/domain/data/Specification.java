package com.component.domain.data;

import java.util.Collection;

/**
 * 规范 -> db查询条件
 *
 * @author Jinx
 */
public interface Specification {


    /**
     *
     *  作为条件领域  该接口怎么和 Example 结合起来？
     *
     *  作为 repo 的查询入参  应该如何传递
     *
     *  FIXME  应用层query 直接实现该接口  或者 是由service来build
     *
     *
     *
     *
     * @return
     */
    Collection<Example> getExamples();
}
