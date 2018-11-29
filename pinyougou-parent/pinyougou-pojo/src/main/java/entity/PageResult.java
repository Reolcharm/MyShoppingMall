package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 查询分页的结果集. json格式: {total:10,rows:[{},{}]}
 * 
 * @author Reolcharm
 *
 */
public class PageResult implements Serializable {
	/**
	 * 总记录数
	 */
	private Long total;

	/**
	 * 当前页结果, 分页查询后的结果
	 */
	private List rows;

	public PageResult(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
