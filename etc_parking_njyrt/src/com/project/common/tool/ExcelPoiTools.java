package com.project.common.tool;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.util.IOUtils;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExcelPoiTools {
	
	private static final double _size = 1.2;

	private HSSFWorkbook workbook;
	
	private HSSFSheet sheet;
	
	private Drawing  drawing;
	
	private List<HSSFSheet> sheets;
	
	private List<Drawing>  drawings;
	
	
	public ExcelPoiTools() {
		// 声明一个工作薄
		workbook = new HSSFWorkbook();
		// 生成一个表格
		sheet = workbook.createSheet("SHEET1");
		drawing = sheet.createDrawingPatriarch();
	}
	
	public void setSheetName(int i,String sheetName)
	{
		workbook.setSheetName(i, sheetName);
	}
	
	
	public ExcelPoiTools(String sheetName) {
		// 声明一个工作薄
		workbook = new HSSFWorkbook();
		// 生成一个表格
		sheet = workbook.createSheet(sheetName);
	}
	
	
	// 更新多个页签
	public ExcelPoiTools(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	
	public ExcelPoiTools(File f) throws FileNotFoundException, IOException{
		workbook=new HSSFWorkbook(new FileInputStream(f));
		sheet=workbook.getSheetAt(0);
	}
	/**
	 * 按行读取数据
	 * @param row
	 * @param startCol
	 * @param endCol
	 * @return
	 */
	public List<Object> readRow(int row,int startCol,int endCol){
		List<Object> list=new ArrayList<Object>();
		for(int i=startCol;i<=endCol;i++){
			list.add(readCell(row, i));
		}
		return list;
	}
	/**
	 * 按单元格读取数据
	 * @param row
	 * @param startCol
	 * @param endCol
	 * @return
	 */
	public Object readCell(int row,int column){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			return null;
		}
		int cellType=hssfRow.getCell(column).getCellType();
		Object result=null;
		switch (cellType) {
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result=hssfRow.getCell(column).getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				result=hssfRow.getCell(column).getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				result=hssfRow.getCell(column).getStringCellValue();
				break;
	
			default:
				break;
		}
		return result;
	}
	/**
	 * 读取单元格字符串数据
	 * @param row
	 * @param startCol
	 * @param endCol
	 * @return
	 */
	public String readCellStr(int row,int column){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			return null;
		}		
		if(null==hssfRow.getCell(column)){
			return "";
		}else{
			return hssfRow.getCell(column).getStringCellValue();
		}

	}
	

	/**
	 * 读取单元格DOUBLE数据
	 * @param row
	 * @param startCol
	 * @param endCol
	 * @return
	 */
	public Double readCellDouble(int row,int column){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			return null;
		}
		
		return hssfRow.getCell(column).getNumericCellValue();
	}
	/**
	 * 读取单元格INT数据
	 * @param row
	 * @param column
	 * @return
	 */
	public Integer readCellInt(int row,int column){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			return null;
		}
		
		Double d=readCellDouble(row,column);
		if(d==null){
			return null;
		}else{
			return d.intValue();
		}
	}
	/**
	 * 读取单元格LONG数据
	 * @param row
	 * @param column
	 * @return
	 */
	public Long readCellLong(int row,int column){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			return null;
		}
		
		Double d=readCellDouble(row,column);
		if(d==null){
			return null;
		}else{
			return d.longValue();
		}
	}
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeHeader(int row,String[] columnValues){
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		if(columnValues!=null){
			int length=columnValues.length;
			for(int i=0;i<length;i++){
				if(columnValues[i]==null){
					continue;
				}else{
					writeCell(row, i,columnValues[i],style);
				}
			}
		}
	}
	
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeHeaderList(int row,List<String[]> columnValues){
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		if(columnValues!=null && columnValues.size() != 0){
			int size = columnValues.size();
			for(int i=0;i<size;i++)
			{
				int length=columnValues.get(i).length;
				for(int j=0;j<length;j++){
					if(columnValues.get(i)[j]==null){
						continue;
					}else{
						writeCell(row,j,i,columnValues.get(i)[j],style);
					}
				}
				
			}
		}
	}
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeHeader(HSSFSheet sheet,int row,String[] columnValues){
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		if(columnValues!=null){
			int length=columnValues.length;
			for(int i=0;i<length;i++){
				if(columnValues[i]==null){
					continue;
				}else{
					writeCell(sheet,row, i,columnValues[i],style);
				}
			}
		}
	}
	
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeList(List<Map<String,Object>> params){
		int row = 0;
		for(Map param:params)
		{
			// 重新赋值初始行数
			row = 0;
			// 获取页签名称
			String sheetName = param.get("sheetName").toString();
			HSSFSheet sheet = workbook.createSheet(sheetName);
			// 设置列宽
			setColWidth(sheet,(Integer[])param.get("columnWidth"));
			// 表头
			writeHeader(sheet,row++,(String[])param.get("header"));
			// 数据
			List<String[]> valueList  = (List<String[]>)param.get("value");
			
			
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			
			// 判断是否有数据
			if(valueList != null && valueList.size() != 0)
			{
				for(String[] value:valueList)
				{
					writeCell(sheet,row++,value,style);
				}
			}
		}
		
		
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		
	}
	
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeCell(int row,Object[] columnValues){
		if(columnValues!=null){
			int length=columnValues.length;
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for(int i=0;i<length;i++){
				if(columnValues[i]==null){
					continue;
				}else{
					writeCell(row, i,columnValues[i],style);
				}
			}
		}
	}
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeCell(int row,int sheetNum,Object[] columnValues){
		if(columnValues!=null){
			int length=columnValues.length;
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for(int i=0;i<length;i++){
				if(columnValues[i]==null){
					continue;
				}else{
					writeCell(row, i,sheetNum,columnValues[i],style);
				}
			}
		}
	}
	
	/**
	 * 按照行写数据
	 * @param row
	 * @param columnValues
	 */
	public void writeCell(HSSFSheet sheet,int row,Object[] columnValues,HSSFCellStyle style){
		if(columnValues!=null){
			int length=columnValues.length;
			/*HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);*/
			for(int i=0;i<length;i++){
				if(columnValues[i]==null){
					continue;
				}else{
					writeCell(sheet,row, i,columnValues[i],style);
				}
			}
		}
	}
	
	
	
	
	/**
	 * 写单个单元格
	 * @param row
	 * @param column
	 * @param value
	 */
	public void setColWidth(int column,int width){
		sheet.setColumnWidth(column, width*256);
	}
	
	/**
	 * 写单个单元格
	 * @param row
	 * @param column
	 * @param value
	 */
	public void setColWidth(Integer[] columnWidthArray){
		for(int i=0;i<columnWidthArray.length;i++){
			sheet.setColumnWidth(i, columnWidthArray[i]*256);
		}
	}
	
	/**
	 *  在传入的页签里增加单元格宽度
	 * 
	 */
	public void setColWidth(HSSFSheet sheet,Integer[] columnWidthArray){
		for(int i=0;i<columnWidthArray.length;i++){
			sheet.setColumnWidth(i, columnWidthArray[i]*256);
		}
	}
	
	
	/**
	 * 写单个单元格
	 * @param row
	 * @param column
	 * @param value
	 */
	public void writeCell(int row,int column,Object value){
		HSSFRow hssfRow = getOrCreateRow(row);
		
		if(value instanceof String){
			String str=(String)value;
			if(str.contains(".jpg") || str.contains(".png")){
				value= ((String) value).replace("inputimg", "");
				// 出力图片
				writeImgToCell(row,column,value.toString());
			}
			// 出力string
			HSSFCell cell = hssfRow.createCell(column);
			cell.setCellValue((String)value);
		}else if(value instanceof Integer){
			HSSFCell cell = hssfRow.createCell(column,HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Integer)value);
		}else if(value instanceof Float){
			HSSFCell cell = hssfRow.createCell(column,HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Float)value);
		}else if(value instanceof Long){
			HSSFCell cell = hssfRow.createCell(column,HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Long)value);
		}else if(value instanceof Double){
			HSSFCell cell = hssfRow.createCell(column,HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Double)value);
		}else{
			HSSFCell cell = hssfRow.createCell(column);
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	
	// 写入图片到IMG 2016/05/13  add by fengsy
	public void writeImgToCell(int row,int column,String fileName){
        int pgtype;    //图片文件的类型
	//	String fileName = "D:/win64/apache-tomcat-7.0.69/wtpwebapps/sampleProject/productImg/2-1.jpg";  
		if(fileName.endsWith(".jpg")){
			pgtype=workbook.PICTURE_TYPE_JPEG;
		}else if (fileName.endsWith(".png")) {
			pgtype=workbook.PICTURE_TYPE_PNG;
		}else{
			return;
		}
		
		InputStream is;
		try {
			is = new FileInputStream(fileName);
			byte[] bytes = IOUtils.toByteArray(is);  
			int pictureIdx = workbook.addPicture(bytes, pgtype);  
			CreationHelper helper = workbook.getCreationHelper();  
			ClientAnchor anchor = helper.createClientAnchor();  
			  
			// 图片插入坐标  
			anchor.setCol1(column);  
			anchor.setRow1(row);  
			// 插入图片  
			Picture pict = drawing.createPicture(anchor, pictureIdx);  
			pict.resize(_size);    //
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	// 写入图片到IMG 2016/05/13  add by fengsy
		public void writeImgToCell(int row,int column,byte[] bytes,int pgtype){
//	        int pgtype;    //图片文件的类型
		//	String fileName = "D:/win64/apache-tomcat-7.0.69/wtpwebapps/sampleProject/productImg/2-1.jpg";  
			
			InputStream is;
			try {
//				is = new FileInputStream(fileName);
//				byte[] bytes = IOUtils.toByteArray(is);  
				int pictureIdx = workbook.addPicture(bytes, pgtype);  
				CreationHelper helper = workbook.getCreationHelper();  
				ClientAnchor anchor = helper.createClientAnchor();  
				  
				// 图片插入坐标  
				anchor.setCol1(column);  
				anchor.setRow1(row);  
				// 插入图片  
				Picture pict = drawing.createPicture(anchor, pictureIdx);  
				pict.resize(_size);    //
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	
	
	/**
	 * 写单个单元格
	 * @param row
	 * @param column
	 * @param value
	 */
	public void writeCell(int row,int column,Object value,HSSFCellStyle style){
		HSSFRow hssfRow = getOrCreateRow(row);
		
		HSSFCell cell = hssfRow.createCell(column);
		cell.setCellStyle(style); 
		if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer||
				value instanceof Float||
				value instanceof Long||
				value instanceof Double){
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	/**
	 * 写单个单元格,多个页签
	 * @param row
	 * @param column
	 * @param value
	 */
	public void writeCell(int row,int column,int sheetNum,Object value,HSSFCellStyle style){
		HSSFRow hssfRow = getOrCreateRow(row,sheetNum);
		
		HSSFCell cell = hssfRow.createCell(column);
		cell.setCellStyle(style); 
		if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer||
				value instanceof Float||
				value instanceof Long||
				value instanceof Double){
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	/**
	 * 写单个单元格,多个页签
	 * @param row
	 * @param column
	 * @param value
	 */
	public void writeCell(HSSFSheet sheet,int row,int column,Object value,HSSFCellStyle style){
		HSSFRow hssfRow = getOrCreateRow(row,sheet);
		
		HSSFCell cell = hssfRow.createCell(column);
		cell.setCellStyle(style); 
		if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer||
				value instanceof Float||
				value instanceof Long||
				value instanceof Double){
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	private HSSFRow getOrCreateRow(int row){
		HSSFRow hssfRow = sheet.getRow(row);
		if(hssfRow==null){
			hssfRow=sheet.createRow(row);
		}
		return hssfRow;
	}
	
	
	private HSSFRow getOrCreateRow(int row,int sheetNum){
		HSSFRow hssfRow =  workbook.getSheetAt(sheetNum).getRow(row);
		if(hssfRow==null){
			hssfRow= workbook.getSheetAt(sheetNum).createRow(row);
		}
		return hssfRow;
	}
	
	
	/**
	 * 
	   * @Title : getOrCreateRow 
	   * @功能描述: 根据页签填写值
	   * @传入参数：@param row
	   * @传入参数：@param sheet
	   * @传入参数：@return
	   * @返回类型：HSSFRow 
	   * @throws ：
	 */
	private HSSFRow getOrCreateRow(int row,HSSFSheet sheet){
		HSSFRow hssfRow =  sheet.getRow(row);
		if(hssfRow==null){
			hssfRow= sheet.createRow(row);
		}
		return hssfRow;
	}
	
	/**
	 * 写至文件
	 * @param targetFile
	 * @return
	 */
	public boolean writeToFile(String targetFile){
		OutputStream out=null;
		boolean flag=false;
		try {
			out= new FileOutputStream(targetFile);
			workbook.write(out);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flag=false;
				}
			}
		}
		return flag;
	}
	
	// add by fengsy 20160713
	// 读取数据
	public List<List<Object>> tReadExcel(String filepath,int cellStart,int cellEnd) throws Exception{
		List<List<Object>> listdata=new ArrayList<List<Object>>();

		ExcelPoiTools et=new ExcelPoiTools(new File(filepath));
		int START_ROW=1;
		// 读取列
		while(true){
			List<Object> list=new ArrayList<Object>();
			// 读取一条的所有cell的值
			for(int i =cellStart-1;i<=cellEnd;i++){
				list.add(et.readCellStr(START_ROW,i));
			}
			if(list.get(0)==null){
				break;
			}
			START_ROW++;
			listdata.add(list);
		}

		return listdata;
	}
	
	public void write(OutputStream stream) throws IOException{
		workbook.write(stream);
	}
	
	public void setDefaultRowHeight(short height)
	{
		sheet.setDefaultRowHeight(height);
	}

	public List<HSSFSheet> getSheets() {
		return sheets;
	}

	public void setSheets(List<HSSFSheet> sheets) {
		
		
		
		this.sheets = sheets;
	}

	public List<Drawing> getDrawings() {
		return drawings;
	}

	public void setDrawings(List<Drawing> drawings) {
		this.drawings = drawings;
	}
	
	
}