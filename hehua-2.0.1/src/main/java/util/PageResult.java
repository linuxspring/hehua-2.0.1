package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageResult extends HashMap {
	public List<?> rows = new ArrayList<Object>();
    public int pageSize;
    public int pageIndex;
    public int pageCount;
    public long total;

    public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
 
	public PageResult() {
		
	}
	
	public PageResult(int count, int pageSize, int pageIndex,List<?> rows ) {
        this.rows = rows;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.total=count;
        if (count % pageSize != 0) {
            this.pageCount = count/pageSize+1;
        }else{
            this.pageCount = count/pageSize;
        }
    }
}
