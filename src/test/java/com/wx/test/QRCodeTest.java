package com.wx.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wx.utils.MatrixToImageWriter;
import com.wx.utils.QRCodeFactory;

public class QRCodeTest {
	
	private static Logger logger = Logger.getLogger(QRCodeTest.class);
	
	@Test
	public void test1() {

     String content="大家好，我是李庆文，很高兴认识大家";
     String logUri="***";
     String outFileUri="***";
     int[]  size=new int[]{430,430};
     String format = "jpg";  
     
       try {
        new QRCodeFactory().CreatQrImage(content, format, outFileUri, logUri,size);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (WriterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }   
  }
	
	@Test
	public void test2(){
		
		try {
			String content = "http://iot.yihuan100.com/bd.php?sn=20C087";
			
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
			BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
			logger.info(img);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
