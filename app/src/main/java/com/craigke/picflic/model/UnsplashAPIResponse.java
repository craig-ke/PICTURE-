
package com.craigke.picflic.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnsplashAPIResponse implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    private final static long serialVersionUID = 222011625387836245L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UnsplashAPIResponse() {
    }

    /**
     * 
     * @param total
     * @param totalPages
     * @param results
     */
    public UnsplashAPIResponse(Integer total, Integer totalPages, List<Result> results) {
        super();
        this.total = total;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
