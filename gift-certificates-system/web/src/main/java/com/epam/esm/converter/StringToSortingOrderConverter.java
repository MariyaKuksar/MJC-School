package com.epam.esm.converter;

import com.epam.esm.entity.SortingOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortingOrderConverter implements Converter<String, SortingOrder> {

    @Override
    public SortingOrder convert(String sortingOrder) {
        return SortingOrder.valueOf(sortingOrder.toUpperCase());
    }
}
