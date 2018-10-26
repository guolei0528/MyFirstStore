package com.project.backMng.platuser.finance.InvoiceInfoMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.platuser.finance.InvoiceInfoMng.model.InvoiceInfoBean;
import com.project.backMng.platuser.finance.InvoiceInfoMng.service.InvoiceInfoMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/finance/InvoiceInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class InvoiceInfoMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(InvoiceInfoMngAction.class);
	/**
	 * service
	 */
	private InvoiceInfoMngService invoiceInfoMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_flag");
		
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/finance/InvoiceInfoMng/InvoiceInfoMng_list.jsp");
		view.addObject(LIST, invoiceInfoMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/addIn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/finance/InvoiceInfoMng/InvoiceInfoMng_addIn.jsp");
		
		return view;
	}
	@RequestMapping(value = "/addOut", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addOut(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/finance/InvoiceInfoMng/InvoiceInfoMng_addOut.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,InvoiceInfoBean bean){
		try {
			invoiceInfoMngService.save(bean,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	/**
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		String id=getStr(request,"ID");
		InvoiceInfoBean bean=invoiceInfoMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/finance/InvoiceInfoMng/InvoiceInfoMng_edit.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String ID=getStr(request, "ID");
		InvoiceInfoBean bean=invoiceInfoMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/finance/InvoiceInfoMng/InvoiceInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,InvoiceInfoBean bean){
		try {
			invoiceInfoMngService.update(bean,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request){
		try{
			String id=getStr(request,"ID");
			invoiceInfoMngService.delete(id,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public InvoiceInfoMngService getInvoiceInfoMngService() {
		return invoiceInfoMngService;
	}
	public void setInvoiceInfoMngService(InvoiceInfoMngService invoiceInfoMngService) {
		this.invoiceInfoMngService = invoiceInfoMngService;
	}
	
}