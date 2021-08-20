package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQuery {
    private String query;
    private List<String> args;

    public SqlQuery() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArgs() {
        return args == null ? Collections.emptyList() : Collections.unmodifiableList(args);
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public void addArg(String arg) {
        if (args == null) {
            args = new ArrayList<>();
        }
        args.add(arg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;

        SqlQuery sqlQuery = (SqlQuery) o;

        if (query != null ? !query.equals(sqlQuery.query) : sqlQuery.query != null) return false;
        return args != null ? args.equals(sqlQuery.args) : sqlQuery.args == null;
    }

    @Override
    public int hashCode() {
        int result = query != null ? query.hashCode() : 0;
        result = 31 * result + (args != null ? args.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SqlQuery{");
        sb.append("query='").append(query).append('\'');
        sb.append(", args=").append(args);
        sb.append('}');
        return sb.toString();
    }
}
