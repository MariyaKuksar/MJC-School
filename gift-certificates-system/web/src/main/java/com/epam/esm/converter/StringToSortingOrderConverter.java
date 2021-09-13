package com.epam.esm.converter;

import com.epam.esm.entity.SortingOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Class converts String to SortingOrder.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see Converter
 */
@Component
public class StringToSortingOrderConverter implements Converter<String, SortingOrder> {

    @Override
    public SortingOrder convert(String sortingOrder) {
        return SortingOrder.valueOf(sortingOrder.toUpperCase());
    }
}
