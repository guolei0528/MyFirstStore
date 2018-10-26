package com.project.sys.admin.login.model;


import java.util.ArrayList;
import java.util.List;

import com.redoak.jar.base.model.OakTreeModel;
/**
 * 用户会话树
 * @author OAK
 *
 */
public class UserSessionTree {
	
	public OakTreeModel node;
	
	public List<OakTreeModel> childNodeList=new ArrayList<OakTreeModel>();

	public OakTreeModel getNode() {
		return node;
	}

	public void setNode(OakTreeModel node) {
		this.node = node;
	}

	public List<OakTreeModel> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(List<OakTreeModel> childNodeList) {
		this.childNodeList = childNodeList;
	}
	
}
