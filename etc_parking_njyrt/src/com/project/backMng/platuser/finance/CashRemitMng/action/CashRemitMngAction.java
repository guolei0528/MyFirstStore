package com.project.backMng.platuser.finance.CashRemitMng.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.platuser.finance.CashRemitMng.model.CashRemitBean;
import com.project.backMng.platuser.finance.CashRemitMng.service.CashRemitMngService;
import com.project.backMng.platuser.finance.PaymentMng.model.PaymentBean;
import com.project.backMng.platuser.finance.PaymentMng.service.PaymentMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/finance/CashRemitMng", method = { RequestMethod.GET, RequestMethod.POST })
public class CashRemitMngAction extends BaseController {

	private final static Logger log = LogManager.getLogger(CashRemitMngAction.class);
	
	/**
	 * service
	 */
	private PaymentMngService paymentMngService;
	
	private CashRemitMngService cashRemitMngService;

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.putRequestString(request, "query_operator_id");
		queryParam.putRequestString(request, "income_time_from");
		queryParam.putRequestString(request, "income_time_to");

		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/platuser/finance/cashRemitMng/CashRemitMng_list.jsp");
		view.addObject(LIST, cashRemitMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);

		return view;
	}

	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,PaymentBean bean){
		try {
			if(Double.parseDouble(bean.getreal_bill())>Double.parseDouble(bean.getpay_bill())){
				return responseJsonText(ResultBean.newErrorResult("实收金额不能低于应收金额"));
			}
			bean.setdate(bean.getdate().replace("-", ""));
			Integer count = paymentMngService.findSamePayment(bean.getoperator_id(), bean.getdate());
			if(count>0){
				return responseJsonText(ResultBean.newErrorResult("请不要重复提交"));
			}
			paymentMngService.save(bean,null);
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request) {
		String operator_id = getStr(request, "ID");
		String begin_time = getStr(request, "begin_time");
		PaymentBean bean = paymentMngService.findInfo(operator_id, begin_time);
		ModelAndView view = new ModelAndView("/jsp/backMng/platuser/finance/cashRemitMng/CashRemitMng_edit.jsp");
		view.addObject(BEAN, bean);

		return view;
	}

	/**
	 * 展示查看的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request) {
		String create_time = getStr(request, "create_time");
		String user_id = getStr(request, "user_id");
		/*String date = getStr(request, "date");
		String park_id = getStr(request, "park_id");
		String area_id = getStr(request, "area_id");
		*/
		Date createTime = new Date(Long.parseLong(create_time));
		
		ObjectMap objectMap=ObjectMap.newInstance();
		objectMap.put("user_id", user_id);
		objectMap.put("create_time", createTime);
		/*objectMap.put("date", date);
		objectMap.put("park_id", park_id);
		objectMap.put("area_id", area_id);*/
		
		CashRemitBean cashRemitBean = cashRemitMngService.findInfo(objectMap);
		
//		PaymentBean bean = paymentMngService.findInfo(operator_id, date);
		ModelAndView view = new ModelAndView("/jsp/backMng/platuser/finance/cashRemitMng/CashRemitMng_view.jsp");
		view.addObject(BEAN, cashRemitBean);

		//		String msg = cashRemitMngService.findInfo(objectMap);
		
		return view;
	}

	/**
	 * 更新
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request, PaymentBean bean) {
		try {
			paymentMngService.update(bean, getLoginUser(request));
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request) {
		try {
			String id = getStr(request, "ID");
			paymentMngService.delete(id, getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}

	
	@RequestMapping("/findUser")
	@ResponseBody
	public String findUser(HttpServletRequest request) {
		
		String date = getStr(request, "date");
		List<ObjectMap> maps = paymentMngService.findUser(date);
		
		return responseJsonText(maps);
	}

	@RequestMapping("/findAmountAndBill")
	@ResponseBody
	public String findAmountAndBill(HttpServletRequest request) {
		
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("user_operator", getStr(request, "user_operator"));
		queryParam.put("exit_shift", getStr(request, "exit_shift"));
		queryParam.put("lane_id", getStr(request, "lane_id"));
		String date = getStr(request, "date");
		date = date.replace("-", "");
		queryParam.put("date", date);
		ObjectMap map = paymentMngService.findAmountAndBill(queryParam);
		return responseJsonText(map);
	}

	public PaymentMngService getPaymentMngService() {
		return paymentMngService;
	}
	
	public void setPaymentMngService(PaymentMngService paymentMngService) {
		this.paymentMngService = paymentMngService;
	}

	public CashRemitMngService getCashRemitMngService() {
		return cashRemitMngService;
	}

	public void setCashRemitMngService(CashRemitMngService cashRemitMngService) {
		this.cashRemitMngService = cashRemitMngService;
	}
	
}