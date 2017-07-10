package com.jflyfox.autocreate.util;

/**
 * 
 */
public class TbQuestion {

	private static final long serialVersionUID = 1L;

    //columns START
	/**  **/
	private int id ;
	/** 类型1单选2多选3单行输入4多选输入 **/
	private int ques_type ;
	/** 标题 **/
	private String title;
	/** 排序 **/
	private int sort ;
	/** 是否必须 **/
	private int is_require ;
	/** 关联id **/
	private int relate_id ;
	/** 问卷id **/
	private int qid ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQues_type() {
		return ques_type;
	}

	public void setQues_type(int ques_type) {
		this.ques_type = ques_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getIs_require() {
		return is_require;
	}

	public void setIs_require(int is_require) {
		this.is_require = is_require;
	}

	public int getRelate_id() {
		return relate_id;
	}

	public void setRelate_id(int relate_id) {
		this.relate_id = relate_id;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}
}