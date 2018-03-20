package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class GetSearchResultResponse {
    private Record[] searchresults;
    private LibraryServerResult GetSearchResultResult;

    public Record[] getSearchresults() {
        return searchresults;
    }

    public void setSearchresults(Record[] searchresults) {
        this.searchresults = searchresults;
    }

    public LibraryServerResult getGetSearchResultResult() {
        return GetSearchResultResult;
    }

    public void setGetSearchResultResult(LibraryServerResult getSearchResultResult) {
        GetSearchResultResult = getSearchResultResult;
    }
}


