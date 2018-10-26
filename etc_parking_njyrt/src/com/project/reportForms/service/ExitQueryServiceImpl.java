package com.project.reportForms.service;

import java.util.List;

import com.project.reportForms.model.InOrOutFlowInfo;
import com.project.tools.BigDecimalTool;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class ExitQueryServiceImpl extends BaseService implements ExitQueryService {
	
	public ExitQueryServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("reportForms.exit");
	}

	@Override
	public double getAllIncome(ObjectMap param) {
		int fen = super.findInteger(ns("getAllIncome"),param);
		double yuan = BigDecimalTool.div(fen, 100, 2);
		return yuan;
	}

	@Override
	public List<InOrOutFlowInfo> countExitFlow(ObjectMap param) {
		List<InOrOutFlowInfo> list = super.findList(ns("countExitFlow"), param);
		for(InOrOutFlowInfo info:list){
			info.setIncomeOfYuan(0.0);
			if(info.getIncome()!=null && info.getIncome()!=0){
				double yuan = BigDecimalTool.div(info.getIncome(), 100, 2);
				info.setIncomeOfYuan(yuan);
			}
		}
		return list;
	}
	
	@Override
	public int countTotalFlowOfExit(ObjectMap param) {
		int total = super.findInteger(ns("countTotalFlow"),param);
		return total;
	}

	@Override
	public int countFlowOfZero(ObjectMap param) {
		int zero = super.findInteger(ns("countZeroFlow"),param);
		return zero;
	}

	@Override
	public InOrOutFlowInfo countNightFlowOfExit(ObjectMap param) {
		InOrOutFlowInfo flow = super.findObj(ns("countNightFlow"),param);
		flow.setIncomeOfYuan(0.0);
		if(flow.getIncome()!=null){
			flow.setIncomeOfYuan(BigDecimalTool.div(flow.getIncome(), 100, 2));
		}
		flow.setAmountOfYuan(0.0);
		if(flow.getAmount()!=null){
			flow.setAmountOfYuan(BigDecimalTool.div(flow.getAmount(), 100, 2));
		}
		return flow;
	}

	@Override
	public List<InOrOutFlowInfo> countEntranceFlow(ObjectMap param) {
		List<InOrOutFlowInfo> list = super.findList("reportForms.entrance.countEntrance", param);
		return list;
	}

	@Override
	public int countTotalFlowOfEntrance(ObjectMap param) {
		int total = super.findInteger("reportForms.entrance.countTotalFlow",param);
		return total;
	}

	@Override
	public int countNightFlowOfEntrance(ObjectMap param) {
		int flow = super.findInteger("reportForms.entrance.countNightFlow",param);
		return flow;
	}

	@Override
	public List<String> getLicensesByDayInEntrance(ObjectMap param) {
		List<String> list = super.findList("reportForms.entrance.getLicensesByDay", param);
		return list;
	}

	@Override
	public List<String> getLicensesByDayInExit(ObjectMap param) {
		List<String> list = super.findList(ns("getLicensesByDay"), param);
		return list;
	}

	@Override
	public int countFlowInEntrance(ObjectMap param) {
		return super.findInteger("reportForms.entrance.countFlow", param);
	}

	@Override
	public int countFlowInExit(ObjectMap param) {
		return super.findInteger(ns("countFlowByEntrytime"), param);
	}

	@Override
	public int countFlowWithoutEntrytime(ObjectMap param) {
		return super.findInteger(ns("countFlowWithoutEntrytime"), param);
	}

	@Override
	public List<InOrOutFlowInfo> groupFlowByEntrytime(ObjectMap param) {
		List<InOrOutFlowInfo> list = super.findList(ns("groupFlowByEntrytime"), param);
		return list;
	}
}
