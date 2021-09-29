package com.epam.esm.dto;

import com.epam.esm.entity.SortingOrder;

import java.util.List;

/**
 * Class is implementation of pattern DTO for transmission gift certificate
 * search params between service and controller
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public class GiftCertificateSearchParamsDto {
    private List<String> tagNames;
    private String partNameOrDescription;
    private SortingOrder sortingOrderByDate;
    private SortingOrder sortingOrderByName;

    public GiftCertificateSearchParamsDto() {
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getPartNameOrDescription() {
        return partNameOrDescription;
    }

    public void setPartNameOrDescription(String partNameOrDescription) {
        this.partNameOrDescription = partNameOrDescription;
    }

    public SortingOrder getSortingOrderByDate() {
        return sortingOrderByDate;
    }

    public void setSortingOrderByDate(SortingOrder sortingOrderByDate) {
        this.sortingOrderByDate = sortingOrderByDate;
    }

    public SortingOrder getSortingOrderByName() {
        return sortingOrderByName;
    }

    public void setSortingOrderByName(SortingOrder sortingOrderByName) {
        this.sortingOrderByName = sortingOrderByName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificateSearchParamsDto that = (GiftCertificateSearchParamsDto) o;

        if (tagNames != null ? !tagNames.equals(that.tagNames) : that.tagNames != null) return false;
        if (partNameOrDescription != null ? !partNameOrDescription.equals(that.partNameOrDescription) : that.partNameOrDescription != null)
            return false;
        if (sortingOrderByDate != that.sortingOrderByDate) return false;
        return sortingOrderByName == that.sortingOrderByName;
    }

    @Override
    public int hashCode() {
        int result = tagNames != null ? tagNames.hashCode() : 0;
        result = 31 * result + (partNameOrDescription != null ? partNameOrDescription.hashCode() : 0);
        result = 31 * result + (sortingOrderByDate != null ? sortingOrderByDate.hashCode() : 0);
        result = 31 * result + (sortingOrderByName != null ? sortingOrderByName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GiftCertificateSearchParamsDto{");
        sb.append("tagNames=").append(tagNames);
        sb.append(", partNameOrDescription='").append(partNameOrDescription).append('\'');
        sb.append(", sortingOrderByDate=").append(sortingOrderByDate);
        sb.append(", sortingOrderByName=").append(sortingOrderByName);
        sb.append('}');
        return sb.toString();
    }
}
