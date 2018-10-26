package com.project.backMng.admin.report.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.project.tools.DateUtil;
@Component
public class SaveRecordService {

	public void execute()
	{
		System.out.println("定时任务"+DateUtil.get4yMdHms(new Date()));
	}
	
}
