package com.helome.monitor.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
	/**
	 * 节点id,这个很重要到加载远程服务器数据
	 */
	private long id;

	/**
	 * 显示的节点文本
	 */
	private String text;

	/**
	 * 节点状态, 'open' 或者 'closed'
	 */
	private String state = "closed";

	/**
	 * 指明检查节点是否选中
	 */
	private boolean checked;

	/**
	 * 可以添加到节点的自定义属性
	 */
	private Map<String, Object> attributes;

	/**
	 * 子节点
	 */
	private List<TreeNode> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		if (null == attributes) {
			attributes = new HashMap<String, Object>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		if (null == children) {
			children = new ArrayList<TreeNode>();
		}
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
