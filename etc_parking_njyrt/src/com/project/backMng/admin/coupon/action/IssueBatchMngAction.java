package com.project.backMng.admin.coupon.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.coupon.model.IssueBatchMngBean;
import com.project.backMng.admin.coupon.service.IssueBatchMngService;
import com.project.common.tool.ExcelPoiTools;
import com.project.common.tool.MatrixToImageWriter;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/coupon/IssueBatchMng", method = { RequestMethod.GET, RequestMethod.POST })
public class IssueBatchMngAction extends BaseController{
	
	private final static Logger log = LogManager.getLogger(IssueBatchMngAction.class);
	
//	private CarInfoMngService carInfoMngService;
	private IssueBatchMngService issueBatchMngService;
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_coupon_type");
		queryParam.putRequestString(request,"query_issuer_code");
		queryParam.putRequestInt(request,"query_batch_id");
		queryParam.putRequestString(request,"query_start_time");
		queryParam.putRequestString(request,"query_end_time");
		// 获得状态
		String query_status = request.getParameter("query_status");
		if(query_status!=null && !"".equals(query_status))
		{
			queryParam.put("query_status", Integer.parseInt(query_status));
		}
		// 获取发行日期
		String issue_date_str = request.getParameter("query_issue_date");
		if(issue_date_str!=null && !"".equals(issue_date_str))
		{
			String issue_date = issue_date_str.substring(2, 4)+issue_date_str.substring(5, 7);
			queryParam.put("query_issue_date", issue_date);
		}
//		query_issuer_date
		Page page = getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/IssueBatchMng/IssueBatchMng_list.jsp");
		view.addObject(LIST, issueBatchMngService.findList(queryParam, page));
		queryParam.put("query_issue_date", issue_date_str);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String issue_id=getStr(request, "issue_id");
		IssueBatchMngBean bean = issueBatchMngService.findInfo(issue_id);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/IssueBatchMng/IssueBatchMng_view.jsp");
		view.addObject(BEAN, bean);
		return view;
	}
	
	
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/IssueBatchMng/IssueBatchMng_add.jsp");
		
		// 获取当前时间
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE,1);
		Date tomorrow =cal.getTime();
		// 获得有效开始时间和有效结束时间
		String startTime = DateUtil.get4yMd(now)+" 00:00:00";
		String endTime = DateUtil.get4yMd(tomorrow)+" 00:00:00";
		
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("start_time", startTime);
		queryParam.put("end_time", endTime);
		view.addObject(BEAN, queryParam);
		return view;
	}
	
	
	/**
	 * 展示新增的界面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,IssueBatchMngBean bean){
		try {
			/*String msg = carInfoMngService.save(bean);
			//唯一性校验
			if(!"success".equals(msg))
			{
				return super.responseJsonText(ResultBean.newErrorResult(msg));
			}
			return super.responseJsonText(ResultBean.newSuccessResult());*/
			String start_time = request.getParameter("start_time_str");
			String end_time = request.getParameter("end_time_str");
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			bean.setCreate_user(loginUserBean.getUserId());
			bean.setStart_time(DateUtil.parse4yMdHms(start_time));
			bean.setEnd_time(DateUtil.parse4yMdHms(end_time));
			boolean flag = issueBatchMngService.save(bean);
			
			
			if(flag)
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
//		return null;
	}
	
	
	
	
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/downLoad", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView downLoad(HttpServletRequest request){
//		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/IssueBatchMng/IssueBatchMng_add.jsp");
//		return view;
		String checkedDate = request.getParameter("checkedData");
		/*String[] dataArray = checkedDate.split(",");
		List<Integer> issue_id = new ArrayList<Integer>();
		for(int i=0;i<dataArray.length;i++)
		{
			issue_id.add(Integer.parseInt(dataArray[i]));
		}*/
		
		List<CouponMngBean> couponMngBean = issueBatchMngService.findByIssueId(checkedDate);
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		String targetFile="优惠券"+sf.format(new Date());
		String fileName=targetFile+".xls";
		targetFile=request.getRealPath("/")+targetFile+".xls";
		
		ExcelPoiTools tools=new ExcelPoiTools();
		int row=0;
		tools.setColWidth(new Integer[]{15,16,8,9,10,5,10,13,18,18});
		tools.setDefaultRowHeight((short)950);
		
		tools.writeHeader(row++, new String[]{"发行券编号","验证码","二维码","发行商","发行日期","批次","使用限制","优惠内容","开始时间","结束时间"});
		ByteArrayOutputStream byteArrayOut=null;
		 try {
		int index = 1;
		String couponType = "";
		String couponToll = "";
		String startTime = "";
		String endTime ="";
		for(CouponMngBean bean:couponMngBean){
			
			// 限制类型为金额
			if(bean.getCoupon_type() != null && "J".equals(bean.getCoupon_type()))
			{
				couponType = "金额";
				//优惠内容
				couponToll = "优惠金额"+String.valueOf(bean.getCoupon_toll()/100)+"元";
			}
			// 限制类型为金额
			if(bean.getCoupon_type() != null && "S".equals(bean.getCoupon_type()))
			{
				couponType = "时长";
				//优惠时长
				couponToll = "优惠时长"+String.valueOf(bean.getCoupon_toll()/60)+"小时";
			}
			startTime = DateUtil.get4yMdHms(bean.getStart_time());
			endTime = DateUtil.get4yMdHms(bean.getEnd_time());
			
			tools.writeCell(row++, new String[]{bean.getCoupon_code(),bean.getVerify_code(),"",bean.getIssuer_name(),"20"+bean.getIssue_date(),
			        String.valueOf(bean.getBatch_id()),couponType,couponToll,startTime,endTime});
			couponType = "";
			couponToll="";
			startTime="";
			endTime="";
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN, 0);
			byteArrayOut = new ByteArrayOutputStream(); 
			BitMatrix bitMatrix = new MultiFormatWriter().encode(bean.getVerify_code(), BarcodeFormat.QR_CODE, 55, 55,hints);
//			MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOut);
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//			BufferedImage image= BarcodeUtil.getImage(bean.getVerify_code());
			ImageIO.write(image, "png", byteArrayOut);
			//anchor主要用于设置图片的属性  
//            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 1, index, (short) 1, index+1);     
//            anchor.setAnchorType(3); 
//            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
//            tools.writeCell(index, 1, byteArrayOut.toByteArray());
            tools.writeImgToCell(index, 2, byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG);
            index++;
            byteArrayOut.close();
		}
		// 状态改为已导出
		if(checkedDate != null && !"".equals(checkedDate))
		{
			issueBatchMngService.exprotByIssueId(checkedDate);
		}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 finally{
			 if(byteArrayOut != null)
			 {
				 try {
					byteArrayOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		tools.writeToFile(targetFile);
		ModelAndView view=new ModelAndView("/download.jsp");
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}
	
	/**
	 * 
	   * @Title : invalid 
	   * @功能描述: 优惠券作废管理页面
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：ModelAndView 
	   * @throws ：
	 */
	@RequestMapping(value = "/invalid", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView invalid(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/IssueBatchMng/IssueBatchMng_invalid.jsp");
		return view;
	}
	
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/invalidCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	public String invalidCoupon(HttpServletRequest request){
		ObjectMap objectParam = ObjectMap.newInstance();
		objectParam.putRequestString(request,"invaild_type");
		objectParam.putRequestString(request,"invaild_coupons");
		objectParam.putRequestString(request,"invaild_start_coupon");
		objectParam.putRequestString(request,"invaild_coupon_num");
		objectParam.putRequestString(request,"invaild_batch");
		
		String msg = issueBatchMngService.invaildCoupon(objectParam);
		// 判断是否成功
//		if(!"success".equals(msg))
//		{
			return super.responseJsonText(ResultBean.newSuccessResult(msg));
//		}
//		return super.responseJsonText(ResultBean.newSuccessResult());
//		return super.responseJsonText(msg);
	}
	
	
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCouponCodeByBatch", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchCouponCodeByBatch(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_coupon_type");
		queryParam.putRequestString(request,"query_issuer_code");
		
		// 赋值限制类型
		String use_restrict = request.getParameter("query_use_restrict");
		if(use_restrict != null && !"".equals(use_restrict))
		{
			queryParam.putRequestInt(request,"query_use_restrict");
		}
		
		// 赋值发行年月
		String issue_date = request.getParameter("query_issue_date");
		if(issue_date != null && !"".equals(issue_date))
		{
			issue_date = issue_date.replace("-", "");
			issue_date = issue_date.substring(2);
			queryParam.put("query_issue_date", issue_date);
		}
		
		// 赋值限制类型
		String batch_code = request.getParameter("query_batch_code");
		if(batch_code != null && !"".equals(batch_code))
		{
			queryParam.putRequestInt(request,"query_batch_code");
		}
		
		// 
		List<String> coupon_code = issueBatchMngService.searchCouponCodeByBatch(queryParam);
//		IssueBatchMngBean bean = issueBatchMngService.findInfo(issue_id);
		return super.responseJsonText(coupon_code);
	}
	
	public IssueBatchMngService getIssueBatchMngService() {
		return issueBatchMngService;
	}


	public void setIssueBatchMngService(IssueBatchMngService issueBatchMngService) {
		this.issueBatchMngService = issueBatchMngService;
	}


}
