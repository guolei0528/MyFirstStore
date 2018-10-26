package com.project.backMng.admin.voidCard.ParkVoidCardMng.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.voidCard.ParkVoidCardMng.model.ParkVoidCardBean;
import com.project.backMng.admin.voidCard.ParkVoidCardMng.service.ParkVoidCardMngService;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.common.tool.ExcelPoiTools;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/voidCard/ParkVoidCardMng", method = { RequestMethod.GET, RequestMethod.POST })
public class ParkVoidCardMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(ParkVoidCardMngAction.class);
	/**
	 * service
	 */
	private ParkVoidCardMngService parkVoidCardMngService;
	private String [] type={"1","2","1.0","2.0"};
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "query_in_time_from");
		queryParam.putRequestString(request, "query_in_time_to");
		queryParam.putRequestString(request, "query_cancel_time_from");
		queryParam.putRequestString(request, "query_cancel_time_to");
		queryParam.putRequestString(request, "query_mv_license");
		queryParam.putRequestString(request, "query_b_list_type");
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/ParkVoidCardMng/ParkVoidCardMng_list.jsp");
		view.addObject(LIST, parkVoidCardMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/ParkVoidCardMng/ParkVoidCardMng_add.jsp");
		view.addObject("parks", parkVoidCardMngService.findParkList());
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ParkVoidCardBean bean){
		try {
			LoginUserBean userBean=(LoginUserBean)request.getSession().getAttribute("loginUser");
			bean.setuser_id(userBean.getUserId());
			parkVoidCardMngService.save(bean,null);
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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request) throws UnsupportedEncodingException{
		String mv_license=getStr(request, "mv_license");
		String plate_color=getStr(request, "plate_color");
		ParkVoidCardBean bean=parkVoidCardMngService.findInfo(mv_license, plate_color);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/ParkVoidCardMng/ParkVoidCardMng_edit.jsp");
		view.addObject("parks", parkVoidCardMngService.findParkList());
//		view.addObject("areas", parkVoidCardMngService.findAreaList(bean.getpark_id()));
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request) throws UnsupportedEncodingException{
		String mv_license=getStr(request, "mv_license");
		String plate_color=getStr(request, "plate_color");
		ParkVoidCardBean bean=parkVoidCardMngService.findInfo(mv_license, plate_color);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/ParkVoidCardMng/ParkVoidCardMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,ParkVoidCardBean bean){
		try {
			parkVoidCardMngService.update(bean,null);
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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request){
		try{
//			String id=new String(getStr(request,"ID").getBytes("iso8859-1"),"utf-8");
			ObjectMap map=ObjectMap.newInstance();
			map.putRequestString(request, "mv_license");
			map.putRequestString(request, "plate_color");
			parkVoidCardMngService.delete(map, null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public ParkVoidCardMngService getParkVoidCardMngService() {
		return parkVoidCardMngService;
	}
	public void setParkVoidCardMngService(ParkVoidCardMngService parkVoidCardMngService) {
		this.parkVoidCardMngService = parkVoidCardMngService;
	}
	@ResponseBody
	@RequestMapping("/findAreaList")
	public String findAreaList(HttpServletRequest request){
		String park_id = getStr(request, "park_id");
		List<AreaInfoBean> list = parkVoidCardMngService.findAreaList(park_id);
		return super.responseJsonText(list);
	}
	
	@RequestMapping("/downLoad")
	public ModelAndView downLoad(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/download.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "query_in_time_from");
		queryParam.putRequestString(request, "query_in_time_to");
		queryParam.putRequestString(request, "query_cancel_time_from");
		queryParam.putRequestString(request, "query_cancel_time_to");
		queryParam.putRequestString(request, "query_mv_license");
		queryParam.putRequestString(request, "query_b_list_type");
		List<ParkVoidCardBean> list = parkVoidCardMngService.findList(queryParam, null);
		SimpleDateFormat sf=new SimpleDateFormat("yyMMdd");
		String targetFile="本地黑名单"+sf.format(new Date());
		String fileName=targetFile+".xls";
		targetFile=request.getRealPath("/")+targetFile+".xls";
		try {
			ExcelPoiTools tools=new ExcelPoiTools();
			int row=0;
			tools.setColWidth(new Integer[]{20,50,20,20,40,20,20});
			tools.writeHeader(row++, new String[]{"车牌号","车牌颜色(0 - 蓝牌  1 - 黄牌  2 - 黑牌 3 - 白牌)","录入时间","废弃时间","黑名单类型(1、欠费 2、逃票)","停车场编号","备注"});
			for(ParkVoidCardBean bean:list){
				
				tools.writeCell(row++, new String[]{bean.getmv_license(),bean.getPlate_color(),
						DateUtil.getExcel4yMdHms(bean.getIn_time()),DateUtil.getExcel4yMdHms(bean.getCancel_time()),bean.getb_list_type(),bean.getPark_id(),
				        bean.gets_comment()});
			}
							
			tools.writeToFile(targetFile);
			view.addObject("fileUrl", targetFile);
			view.addObject("fileName", fileName);
			return view;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/backMng/admin/voidCard/ParkVoidCardMng/list.action");
		}
		
	}
	
	@RequestMapping("/downloadModule")
	public ModelAndView downloadModule(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/download.jsp");
		String targetFile="黑名单模板";
		String fileName=targetFile+".xls";
		targetFile=request.getRealPath("/")+targetFile+".xls";
		try {
			ExcelPoiTools tools=new ExcelPoiTools();
			int row=0;
			tools.setColWidth(new Integer[]{20,50,20,20,40,20,20});
			tools.writeHeader(row++, new String[]{"车牌号","车牌颜色(0 - 蓝牌  1 - 黄牌  2 - 黑牌 3 - 白牌)","录入时间","废弃时间","黑名单类型(1、欠费 2、逃票)","停车场编号","备注"});
			tools.writeCell(row++, new String[]{"苏A12345","0","7/5/2015 08:00:00","7/5/2017 08:00:00","1","12345","示例"});
			tools.writeToFile(targetFile);
			view.addObject("fileUrl", targetFile);
			view.addObject("fileName", fileName);
			return view;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	
	
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,MultipartFile file){
		if(file!=null){
			
			try {
				List<ParkVoidCardBean> list = daoruFileList(file.getInputStream());
				LoginUserBean user=(LoginUserBean) request.getSession().getAttribute("loginUser");
				for(ParkVoidCardBean bean:list){
					if(!"1".equals(bean.getb_list_type().trim())  && !"2".equals(bean.getb_list_type().trim())){
						return super.responseJsonText(ResultBean.newErrorResult("Warning: Illegal Argument"));
					}
					if(parkVoidCardMngService.findInfo(bean.getmv_license(),bean.getPlate_color())!=null){
						//核实下已经存在的黑名单用户是更新还是跳过
						//部分数据可能不用更新，需要重写一个update
						bean.setb_list_type(bean.getb_list_type().trim());
						parkVoidCardMngService.update(bean, null);
						continue;
					}else{
						bean.setuser_id(user.getUserId());
						bean.setb_list_type(bean.getb_list_type().trim());
						parkVoidCardMngService.save(bean, null);
					}
				}
				return super.responseJsonText(ResultBean.newSuccessResult());
			} catch (IOException e) {
				e.printStackTrace();
				return super.responseJsonText(ResultBean.newErrorResult("请上传xls格式文档，格式参考导出文件"));
			}
		}
		
		return null;
	}
	
	
	
	public  synchronized List<ParkVoidCardBean> daoruFileList(InputStream inputStream) throws IOException{
//		List<String[]> result = new ArrayList<String[]>();
//		int rowSize = 0;
		List<String> asList = Arrays.asList(type);
//		BufferedInputStream in=new BufferedInputStream(inputStream);
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
	       HSSFWorkbook wb = new HSSFWorkbook(fs);
	       List<ParkVoidCardBean> list=new ArrayList<ParkVoidCardBean>();
	       for(int i=0;i<wb.getNumberOfSheets();i++){
	    	   HSSFSheet sheet = wb.getSheetAt(i);
	    	   if(sheet==null){
	    		   continue;
	    	   }
	    	   for(int j=1;j<=sheet.getLastRowNum();j++){
	    		   ParkVoidCardBean bean=new ParkVoidCardBean();
	    		   HSSFRow row = sheet.getRow(j);
	    		   HSSFCell  mv_license= row.getCell(0);
	    		   HSSFCell plate_color = row.getCell(1);
	    		   HSSFCell in_time = row.getCell(2);
	    		   HSSFCell cancel_time = row.getCell(3);
	    		   HSSFCell b_list_type = row.getCell(4);
	    		   HSSFCell park_id = row.getCell(5);
	    		   HSSFCell  s_comment= row.getCell(6);
	    		   bean.setmv_license(getValue(mv_license));
	    		   bean.setb_list_type(getValue(b_list_type));
	    		   if(asList.contains(bean.getb_list_type())){
	    			   bean.setb_list_type(bean.getb_list_type().replace(".0", ""));
	    		   }
	    		   bean.setIn_time(DateUtil.parseExcel4MdyHms(getValue(in_time)));
	    		   bean.setCancel_time(DateUtil.parseExcel4MdyHms(getValue(cancel_time)));
	    		   bean.setPark_id(getValue(park_id));
	    		   bean.sets_comment(getValue(s_comment));
	    		   bean.setPlate_color(getValue(plate_color).replace(".0", ""));
	    		   list.add(bean);
	    	   }
	       }
	       return list;
	      
	    }
	
	private String getValue(HSSFCell hssfCell) {
				if(hssfCell!=null){
					
					if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			             return String.valueOf(hssfCell.getBooleanCellValue());
			         }
			         else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			             return String.valueOf(hssfCell.getNumericCellValue());
			         }
			         else {
			             return String.valueOf(hssfCell.getStringCellValue());
			         }
				}else{
					return null;
				}
		         
		     }
	
}