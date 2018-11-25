package entity;

import java.io.Serializable;

/**
 * @author Reolcharm 封装结果集, 新增成功与否
 */
public class Result implements Serializable {
	private Boolean success;
	private String message;

	public Result(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
