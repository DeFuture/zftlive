package com.zftlive.android.sample.db;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zftlive.android.base.BaseEntity;

/**
 * [我的收藏]实体
 * @author 曾繁添
 */
@DatabaseTable(tableName="Favorite")
public class Favorite extends BaseEntity {

	/**
	 * 主键ID
	 */
	@DatabaseField(id=true)
	private String id;
	
	/**
	 * 发布日期yyyy-MM-dd
	 */
	@DatabaseField
	private String publishDate;
	
	/**
	 * 频道类别
	 */
	@DatabaseField
	private String category;
	
	/**
	 * 文章标题
	 */
	@DatabaseField
	private String title;
	
	/**
	 * 链接URL
	 */
	@DatabaseField
	private String linkURL ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
}
