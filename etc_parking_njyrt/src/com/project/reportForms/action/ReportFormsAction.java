package com.project.reportForms.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.reportForms.model.InOrOutFlowInfo;
import com.project.reportForms.model.ParkingSpaceInfo;
import com.project.reportForms.service.ExitQueryService;
import com.project.reportForms.service.TempCostService;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.springmvc.action.BaseController;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("reportForms")
public class ReportFormsAction extends BaseController {
	private static final Logger log = LogManager.getLogger(ReportFormsAction.class);

	private ExitQueryService exitQueryService;
	private TempCostService tempCostService;

	public TempCostService getTempCostService() {
		return tempCostService;
	}

	public void setTempCostService(TempCostService tempCostService) {
		this.tempCostService = tempCostService;
	}

	public ExitQueryService getExitQueryService() {
		return exitQueryService;
	}

	public void setExitQueryService(ExitQueryService exitQueryService) {
		this.exitQueryService = exitQueryService;
	}
	
	@ResponseBody
	@RequestMapping("/queryRecordsByEntrytime")
	public Map<String,Object> queryEntranceData(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = receivePostData(request);
			String parkid = json.getString("parkid");
			String ymd = json.getString("ymd");
//			String ymd = "2017-08-31";
//			String parkid = "3204681";
			ObjectMap param = ObjectMap.newInstance();
			param.put("ymd", ymd);
			param.put("parkid", parkid);
			
			int match = 0;
			int entrance = exitQueryService.countFlowInEntrance(param);
			int exit = exitQueryService.countFlowInExit(param);
			int temp = tempCostService.countFlow(param);
			log.info("此日共驶入："+entrance+"车次，其中"+exit+"车次已出库，"+temp+"车次仍在库");
			map.put("flowInEntrance", entrance);
			map.put("flowInExit", exit);
			map.put("flowInTemp", temp);
			if(entrance!=(exit+temp)){
				match = 1;
				List<String> listEn = exitQueryService.getLicensesByDayInEntrance(param);
				List<String> listEx = exitQueryService.getLicensesByDayInExit(param);
				List<String> listTp = tempCostService.getLicensesByDay(param);
				boolean flag = true;
				Iterator<String> iEn = listEn.iterator();
				while(iEn.hasNext()){
					String s = iEn.next();
					if(flag && listEx.contains(s)){
						iEn.remove();
						Iterator<String> iEx = listEx.iterator();
						while(flag && iEx.hasNext()){
							if(iEx.next().equals(s)){
								iEx.remove();
								flag = false;
							}
						}
					}
					if(flag && listTp.contains(s)){
						iEn.remove();
						Iterator<String> iTp = listTp.iterator();
						while(flag && iTp.hasNext()){
							if(iTp.next().equals(s)){
								iTp.remove();
								flag = false;
							}
						}
					}
					flag = true;
				}
				log.info(listEn.size());
				log.info(listEx.size());
				log.info(listTp.size());
				map.put("entranceList", listEn);
				map.put("exitList", listEx);
				map.put("tempList", listTp);
			}
			map.put("match", match);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 按班次日统计当日数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countDataByDateByShift")
	public Map<String, Object> countDataByDateByShift(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = receivePostData(request);
			log.info("收到的请求参数："+json);
			if(json!=null){
				//获取停车场编号
				String parkid = json.getString("parkid");
				//获取查询日期
				int ymd = json.getInt("ymd");
				ObjectMap param = parseParams2Map(parkid, ymd);
				searchDataOfOneDay(map, parkid, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 按自然日统计当日数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countDataByDateByNature")
	public Map<String, Object> countDataByDateByNature(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = receivePostData(request);
			log.info("收到的请求参数："+json);
			if(json!=null){
				//获取停车场编号
				String parkid = json.getString("parkid");
				//获取查询日期
				long mills = json.getLong("mills");
				ObjectMap param = parseParams2Map(parkid, mills);
				searchDataOfOneDay(map, parkid, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private void searchDataOfOneDay(Map<String, Object> map, String parkid, ObjectMap param) {
		//查询停车位信息
		List<ParkingSpaceInfo> parkingInfoList = tempCostService.getParkingSpaceInfo(parkid);
		log.info("停车场的停车位信息："+parkingInfoList);
		map.put("parkingInfoList", parkingInfoList);
		//查询入口流量信息
		List<InOrOutFlowInfo> entranceFlowList = exitQueryService.countEntranceFlow(param);
		log.info("停车场的入口流量信息："+entranceFlowList);
		map.put("entranceFlowList", entranceFlowList);
		//查询出口流量信息
		List<InOrOutFlowInfo> exitFlowList = exitQueryService.countExitFlow(param);
		log.info("停车场的出口流量信息："+exitFlowList);
		map.put("exitFlowList", exitFlowList);
		//查询出口总流量
		int exitTotalFlow = exitQueryService.countTotalFlowOfExit(param);
		log.info("停车场的出口总流量："+exitTotalFlow);
		map.put("exitTotalFlow", exitTotalFlow);
		//查询总收入
		double allIncome = 0;
		if(exitTotalFlow>0){
			allIncome = exitQueryService.getAllIncome(param);
		}
		log.info("停车场的总收入为："+allIncome);
		map.put("allIncome", allIncome);
		//查询收费为0的出口流量
		int exitZeroFlow = exitQueryService.countFlowOfZero(param);
		log.info("停车场的0元流量："+exitZeroFlow);
		map.put("exitZeroFlow", exitZeroFlow);
		//查询出口表里没有入口信息（及入口时间为1970年）的流量
		int exitFlowWithoutEntrytime = exitQueryService.countFlowWithoutEntrytime(param);
		log.info("停车场驶出车辆中没有入口信息的流量："+exitFlowWithoutEntrytime);
		map.put("exitFlowWithoutEntrytime", exitFlowWithoutEntrytime);
		//查询此日驶出的车辆分别是哪几日驶入的，各有几辆
		List<InOrOutFlowInfo> exitFlowByEntrytime = exitQueryService.groupFlowByEntrytime(param);
		log.info("停车场中此日驶出车辆的驶入信息："+exitFlowByEntrytime);
		map.put("exitFlowByEntrytime", exitFlowByEntrytime);
		//查询入口总流量
		int entranceTotalFlow = exitQueryService.countTotalFlowOfEntrance(param);
		log.info("停车场的入口总流量："+entranceTotalFlow);
		map.put("entranceTotalFlow", entranceTotalFlow);
		//查询无人值守期间入口流量
		int entranceNightFlow = exitQueryService.countNightFlowOfEntrance(param);
		log.info("停车场的无人值守期间入口流量："+entranceNightFlow);
		map.put("entranceNightFlow", entranceNightFlow);
		//查询无人值守期间出口流量
		InOrOutFlowInfo exitNightFlow = exitQueryService.countNightFlowOfExit(param);
		log.info("停车场的无人值守期间出口流量："+exitNightFlow);
		map.put("exitNightFlow", exitNightFlow);
	}
	
	
	/**
	 * 接收发送过来的数据，转换成json格式
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject receivePostData(HttpServletRequest request) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}
		String reqBody = sb.toString();
		br.close();
		br = null;
		return JSONObject.fromObject(reqBody);
	}

	/**
	 * 将参数封装到Map
	 * @param parkid
	 * @param ymd
	 * @return
	 */
	public static ObjectMap parseParams2Map(String parkid,int ymd){
		ObjectMap map = ObjectMap.newInstance();
		map.put("parkid", parkid);
		map.put("ymd", ymd);
		return map;
	}
	
	/**
	 * 将参数封装到Map
	 * @param parkid
	 * @param mills
	 * @return
	 */
	public static ObjectMap parseParams2Map(String parkid,long mills){
		Date time = DateUtil.getDate(mills);
		ObjectMap map = ObjectMap.newInstance();
		map.put("parkid", parkid);
		map.put("time", time);
		return map;
	}
	
	
}
