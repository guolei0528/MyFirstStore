package com.project.common.tool;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelTools {
	private WritableWorkbook ww;
	private WritableSheet ws;
	private FormatBox box=new FormatBox();
	
	public FormatBox getBox() {
		return box;
	}
	public void setBox(FormatBox box) {
		this.box = box;
	}
	public ExcelTools() throws BiffException, IOException{
	
	}
	public void createWorkBook(File f) throws BiffException, IOException{
		ww=Workbook.createWorkbook(f);
		ws=ww.createSheet("SHEET1", 0);
	}
	
	public ExcelTools(File f) throws BiffException, IOException{
		try {
			Workbook book=Workbook.getWorkbook(f);
			ww=Workbook.createWorkbook(f,book);
			ws=ww.getSheet(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 带格式写DOUBLE数据
	 * @param column
	 * @param row
	 * @param content
	 * @param nf
	 */
	public void writeNumberCellToExcel(int column,int row,Double content,NumberFormat nf){
		if(content==null){
			writeCellWithoutFormat(column, row, "");
			return;
		}
		try {
			WritableCellFormat wcf=new WritableCellFormat(nf);
			wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
			Number n=new Number(column,row,content,wcf);
			ws.addCell((WritableCell) n);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 带格式写DOUBLE数据
	 * @param column
	 * @param row
	 * @param content
	 * @param nf
	 */
	public void writeNumberCellToExcel(int column,int row,Integer content){
		if(content==null){
			writeCellWithoutFormat(column, row, "");
			return;
		}
		try {
			WritableCellFormat wcf=new WritableCellFormat(new FormatBox().NUM_INT);
			wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
			Number n=new Number(column,row,content,wcf);
			ws.addCell((WritableCell) n);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeNumberCellToExcel(int column,int row,Double content,NumberFormat nf,Colour color){
		
		try {
			if(content==null){
				WritableCellFormat wcf=new WritableCellFormat();
				wcf.setBackground(color);
				wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
				writeCellWithFormat(column, row, "", wcf);
			}else{
				WritableCellFormat wcf=new WritableCellFormat(nf);
				wcf.setBackground(color);
				wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
				Number n=new Number(column,row,content,wcf);
				ws.addCell((WritableCell) n);
			}
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 不带格式写字段串
	 * @param column
	 * @param row
	 * @param content
	 */
	public void writeCellWithoutFormat(int column,int row,String content){
		Label tempLable=new Label(column,row,content);
		tempLable.setCellFormat(box.FONT_TEN);
		try {
			ws.addCell(tempLable);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 合并单元格
	 * @param col0
	 * @param row0
	 * @param col1
	 * @param row3
	 */
	public void marginCells(int col0,int row0,int col1,int row3){
		try {
			ws.mergeCells(col0,row0,col1,row3);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 统计多单元格数据和
	 * @param column
	 * @param row
	 * @param obj
	 */
	public void writeMultiColSumResult(int column,int row,Object[] obj){
		int length=obj.length;
		if(length==0){
			return;
		}
		double total=0;
		boolean needToWriteFlag=false;
		for(int i=0;i<length;i++){
			String temp=ws.getCell((Integer) obj[i],row).getContents();
			if(temp!=null){
				if(!temp.equals("")){
					total = total+Double.parseDouble(temp);
					needToWriteFlag=true;
				}
			}
		}
		if(needToWriteFlag){
			writeNumberCellToExcel(column,row,total,box.NUM_DOT_SEPARATE);
		}
	}
	public String getCellValueStr(int column,int row){
		return ws.getCell(column,row).getContents();
	}
	/**
	 * 获取单位格数据
	 * @param column
	 * @param row
	 * @return
	 */
	public double getCellValueDouble(int column,int row){
		String temp=ws.getCell(column,row).getContents();
		if(temp!=null){
			if(!temp.equals("")){
				return Double.parseDouble(temp);
			}
		}
		return 0;
	}
	public void writeCellWithFormat(int column,int row,String content,WritableCellFormat cf){
		writeCellWithoutFormat(column, row, content);
		ws.getWritableCell(column,row).setCellFormat(cf);
	}
	/**
	 * 按行进行数据求和
	 * @param column
	 * @param row
	 * @param rowNum
	 */
	public void writeRowSumResultToExcel(int column,int row,int rowNum){
//		String[] table={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//		String expression="SUM("+table[column]+String.valueOf(row-rowNum+1)+":"+table[column]+String.valueOf(row)+")";
//		Formula f=new Formula(column,row,expression);
//		WritableCell cc=ws.getWritableCell(column,row);
//		f.setCellFormat(cc.getCellFormat());
//		try {
//			ws.addCell(f);
//		} catch (RowsExceededException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		double total=0;
		boolean needToWriteFlag=false;
		for(int i=1;i<rowNum+1;i++){
			String temp=ws.getCell(column,row-i).getContents();
			if(temp!=null){
				if(!temp.equals("")){
					total = total+Double.parseDouble(temp);
					needToWriteFlag=true;
				}
			}
		}
		if(needToWriteFlag){
			writeNumberCellToExcel(column,row,total,box.NUM_DOT_SEPARATE);
		}
	}
	/**
	 * 向EXCEL表格中写日期
	 * @param col
	 * @param row
	 * @param time
	 * @param df
	 */
	public void writeDateToExcel(int col,int row,Timestamp time,DateFormat df){
		WritableCellFormat format=new WritableCellFormat(df);
		Date d=new Date();
		DateTime dt=new DateTime(col,row,d,format);
		try {
			ws.addCell(dt);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 写EXCEL，关闭工作区
	 *
	 */
	public void closeExcel(){
		try {
			if(ww!=null){
				ww.write();
				ww.close();
			}
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
