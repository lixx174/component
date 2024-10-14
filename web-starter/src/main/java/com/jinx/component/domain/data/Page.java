package com.jinx.component.domain.data;

import java.util.List;

/**
 * @author Jinx
 */
public interface Page<T> {

    Number getPage();

    Number getSize();

    Number getPages();

    List<T> getContent();
}
