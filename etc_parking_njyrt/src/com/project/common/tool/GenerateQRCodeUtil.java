package com.project.common.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateQRCodeUtil {
	private final static int DEFAULT_SIZE=300;
	
	public static void generateQRcode(String text,File file,String writeStr) throws WriterException, IOException{
		createQRcode(DEFAULT_SIZE, DEFAULT_SIZE, text, file, writeStr);
	}
	
	public static void createQRcode(Integer width,Integer height,String text,File file,String writeStr) throws WriterException, IOException{
		
		if(writeStr==null||writeStr.trim().length()==0){
			writeStr="";
		}
		
		String format = "png";
		
		Map<EncodeHintType, Object> paramMap = new HashMap<EncodeHintType, Object>(); 
		
		paramMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		
		 BitMatrix bitMatrix = new MultiFormatWriter().encode(text,  
	                BarcodeFormat.QR_CODE, width, height, paramMap);// 生成矩阵  
	     
		MatrixToImageWriter.writeToFile(bitMatrix, format, file);
	     
	     BufferedImage image = toBufferedImage(bitMatrix);
	     
	     //加字
	     BufferedImage strImage = new BufferedImage(image.getWidth(), image.getHeight()+100, image.getType());
	     
	     Graphics2D g=strImage.createGraphics();

	     g.drawImage(image, 0, 0, null);
	     g.setColor(Color.white);
	     g.fillRect(0,image.getHeight(),strImage.getWidth(),strImage.getHeight());
	     g.setFont(new Font(null,Font.PLAIN,15)); //字体、字型、字号 
	     
	     g.setColor(Color.BLACK);
	     g.drawString(writeStr,11,strImage.getHeight()-80); //画文字 
	     g.dispose();
	     
	     
		if (!ImageIO.write(strImage, format, file)) {
			throw new IOException("QRCodeSaveError");
		}
	}
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
}
