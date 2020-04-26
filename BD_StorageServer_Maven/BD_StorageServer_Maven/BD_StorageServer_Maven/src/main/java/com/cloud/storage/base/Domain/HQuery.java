package com.cloud.storage.base.Domain;

import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

public class HQuery {

    private String table;  
    private String family;  
    private String qualifier;  
    private String qualifierValue;  
    private String row;  
    private String startRow;  
    private String stopRow;  
    private Filter filter;  
    private PageFilter pageFilter;  
    private Scan scan;  
    private String searchLimit;
    private List<HBaseColumn> columns = Lists.newArrayList();

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getQualifierValue() {
		return qualifierValue;
	}

	public void setQualifierValue(String qualifierValue) {
		this.qualifierValue = qualifierValue;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}


	public String getStopRow() {
		return stopRow;
	}

	public void setStopRow(String stopRow) {
		this.stopRow = stopRow;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public PageFilter getPageFilter() {
		return pageFilter;
	}

	public void setPageFilter(PageFilter pageFilter) {
		this.pageFilter = pageFilter;
	}

	public Scan getScan() {
		return scan;
	}

	public void setScan(Scan scan) {
		this.scan = scan;
	}

	public List<HBaseColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<HBaseColumn> columns) {
		this.columns = columns;
	}

	public String getSearchLimit() {
		return searchLimit;
	}

	public void setSearchLimit(String searchLimit) {
		this.searchLimit = searchLimit;
	}

	public String getStartRow() {
		return startRow;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	} 
    
    
}
