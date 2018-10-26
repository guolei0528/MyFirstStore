package com.project.backMng.admin.whiteListMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.whiteListMng.service.WhiteListDeptMngService;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;


@Controller
@RequestMapping(value = "backMng/admin/whiteList/WhiteListDeptMng", method = { RequestMethod.GET, RequestMethod.POST })
public class WhiteListDeptMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(WhiteListDeptMngAction.class);
	
	
	private WhiteListDeptMngService whiteListDeptMngService;
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		/*ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_mv_license");
		queryParam.putRequestString(request,"query_color");
		queryParam.putRequestString(request,"query_obu_id");
		*/
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListDeptMng/WhiteListDeptMng_list.jsp");
		/*view.addObject(LIST, whiteListMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);*/
		
		return view;
	}
	
	
	@RequestMapping(value="/loadTree")
	@ResponseBody
	public String loadTree(HttpServletRequest request ){
		Integer LEVEL = getInt(request, "LEVEL");
		String PARENT_ID = getStr(request, "PARENT_ID");
		if( LEVEL==null){
			
			OakTreeModel model = new OakTreeModel();
			model.setName("白名单部门管理");
			model.setId("0");
			model.setIsParent("true");
			model.setParentId(null);
			model.setNodeType("0");
			
			return responseJsonText(model);
		}else if(LEVEL==0){
			OakTreeModel model = new OakTreeModel();
			model.setName("江苏省人民医院");
			model.setId("1");
			model.setIsParent("true");
			model.setParentId(null);
			model.setNodeType("0");
			return responseJsonText(model);
		}
		else if(LEVEL==1){
			OakTreeModel model = new OakTreeModel();
			model.setName("外科");
			model.setId("2");
			model.setIsParent("true");
			model.setParentId(null);
			model.setNodeType("0");
			return responseJsonText(model);
		}else if(LEVEL==2){
			OakTreeModel model = new OakTreeModel();
			model.setName("普通外科");
			model.setId("3");
			model.setIsParent("false");
			model.setParentId(null);
			model.setNodeType("0");
			return responseJsonText(model);
		}
		return null;
	}
	
	
	
	public WhiteListDeptMngService getWhiteListDeptMngService() {
		return whiteListDeptMngService;
	}

	public void setWhiteListDeptMngService(WhiteListDeptMngService whiteListDeptMngService) {
		this.whiteListDeptMngService = whiteListDeptMngService;
	}
	
}
