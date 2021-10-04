package com.epam.esm.dto;

import java.util.List;

/**
 * Class is implementation of pattern DTO for transmission page
 * entity between service and controller.
 *
 * @param <T> the entity which presents on page
 * @author Maryia_Kuksar
 * @version 1.0
 */
public class PageDto<T> {
    private List<T> pagePositions;
    private long totalNumberPositions;

    public PageDto(List<T> pagePositions, long totalNumberPositions) {
        this.pagePositions = pagePositions;
        this.totalNumberPositions = totalNumberPositions;
    }

    public List<T> getPagePositions() {
        return pagePositions;
    }

    public void setPagePositions(List<T> pagePositions) {
        this.pagePositions = pagePositions;
    }

    public long getTotalNumberPositions() {
        return totalNumberPositions;
    }

    public void setTotalNumberPositions(long totalNumberPositions) {
        this.totalNumberPositions = totalNumberPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageDto<?> pageDto = (PageDto<?>) o;

        if (totalNumberPositions != pageDto.totalNumberPositions) return false;
        return pagePositions != null ? pagePositions.equals(pageDto.pagePositions) : pageDto.pagePositions == null;
    }

    @Override
    public int hashCode() {
        int result = pagePositions != null ? pagePositions.hashCode() : 0;
        result = 31 * result + (int) (totalNumberPositions ^ (totalNumberPositions >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDto{");
        sb.append("pagePositions=").append(pagePositions);
        sb.append(", totalNumberPositions=").append(totalNumberPositions);
        sb.append('}');
        return sb.toString();
    }
}
