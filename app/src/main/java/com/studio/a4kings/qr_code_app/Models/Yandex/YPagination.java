package com.studio.a4kings.qr_code_app.Models.Yandex;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown =  true)
public class YPagination {
    @JsonProperty("has_next")
    private boolean hasNext;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("page_count")
    private Integer pageCount;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("page")
    private Integer page;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getPerPage() {

        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
