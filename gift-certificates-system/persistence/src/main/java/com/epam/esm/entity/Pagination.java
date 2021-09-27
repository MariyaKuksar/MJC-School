package com.epam.esm.entity;

public class Pagination {
    private int offset;
    private int limit;

    public Pagination() {
    }

    public Pagination(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination that = (Pagination) o;

        if (offset != that.offset) return false;
        return limit == that.limit;
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + limit;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pagination{");
        sb.append("offset=").append(offset);
        sb.append(", limit=").append(limit);
        sb.append('}');
        return sb.toString();
    }
}
