package com.project.backMng.admin.report.action;



import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.service.EtcReportService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

/**
 * 
   * @类 名： ETCReportAction
   * @功能描述： 入口总报表
   * @作者信息：吴超
   * @创建时间： 2018年3月11日上午10:22:03
   * @修改备注：
 */
@Controller
@RequestMapping(value = "backMng/admin/report/etc", method = { RequestMethod.GET, RequestMethod.POST })
public class EtcReportAction extends BaseController{
	
	private final static Logger log = LogManager.getLogger(EtcReportAction.class);
	
	private EtcReportService etcReportService;

	public EtcReportService getEtcReportService() {
		return etcReportService;
	}

	public void setEtcReportService(EtcReportService etcReportService) {
		this.etcReportService = etcReportService;
	}


	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/etc/etcReport_list.jsp");

		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, etcReportService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
}
