package com.project.common.tool;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class FormatBox {
	public WritableCellFormat LINGHT_GREEN=new WritableCellFormat();
	public WritableCellFormat LINGHT_ORANGE=new WritableCellFormat();
	public WritableCellFormat LINGHT_YELLOW=new WritableCellFormat();
	
	public WritableCellFormat FONT_TEN=new WritableCellFormat();
	
	public DateFormat DATE_FORMAT=new DateFormat("MM-dd HH:mm");
	
	public NumberFormat NUM_PERCENTAGE=new NumberFormat("#####0.000%");
	public NumberFormat NUM_DOT_SEPARATE=new NumberFormat("###,###,###,###,##0");
	public NumberFormat NUM_DOUBLE=new NumberFormat("###,###,###,##0.0##");
	
	public NumberFormat NUM_INT=new NumberFormat("###########");
	public FormatBox(){
		try {
			FONT_TEN.setFont(new WritableFont(WritableFont.ARIAL,10));
			FONT_TEN.setBorder(Border.ALL,BorderLineStyle.THIN);
			
			LINGHT_GREEN.setBackground(Colour.LIGHT_GREEN);
			LINGHT_GREEN.setBorder(Border.ALL,BorderLineStyle.THIN);
			
			LINGHT_ORANGE.setBackground(Colour.LIGHT_ORANGE);
			LINGHT_ORANGE.setBorder(Border.ALL,BorderLineStyle.THIN);
			
			LINGHT_YELLOW.setBackground(Colour.YELLOW);
			LINGHT_YELLOW.setBorder(Border.ALL,BorderLineStyle.THIN);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
