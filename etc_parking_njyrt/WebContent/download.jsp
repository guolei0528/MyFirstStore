<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<%
	java.io.FileInputStream fis = null;
	java.io.OutputStream os = null;
	try {
		request.setCharacterEncoding("GB2312");
		String dd = (String)request.getAttribute("fileUrl");
		String filename = (String)request.getAttribute("fileName");
		java.io.File f=new java.io.File(dd);
		if(f.exists()){
			filename = new String(filename.getBytes("GB2312"), "ISO-8859-1");
//			response.setContentType("application/msword");
			String UPPER_NAME=filename.toUpperCase();
			if(UPPER_NAME.endsWith("DOC")||UPPER_NAME.endsWith("DOCX")){
				response.setContentType("application/msword");
			}else if(UPPER_NAME.endsWith("XLS")||UPPER_NAME.endsWith("XLSX")){
				response.setContentType("application/vnd.ms-excel");
			}else if(UPPER_NAME.endsWith("PPT")||UPPER_NAME.endsWith("PPTX")){
				response.setContentType("application/ms-powerpoint");
			}else if(UPPER_NAME.endsWith("BMP")){
				response.setContentType("image/bmp");
			}else if(UPPER_NAME.endsWith("GIF")){
				response.setContentType("image/gif");
			}else if(UPPER_NAME.endsWith("PNG")||UPPER_NAME.endsWith("JPEG")||UPPER_NAME.endsWith("JPG")){
				response.setContentType("image/jpeg");
			}else{
				response.setContentType("text/html");
			}
			response.setHeader("Content-disposition","attachment; filename=" + filename);
			//更新完后，设定cookie，用于页面判断更新完成后的标志
            Cookie status = new Cookie("updateStatus","success");
            status.setMaxAge(600);
            response.addCookie(status);//添加cookie操作必须在写出文件前，如果写在后面，随着数据量增大时cookie无法写入。
			fis = new java.io.FileInputStream(dd);
			os = response.getOutputStream();
			byte[] buff = new byte[1024];
			int bytesRead = 0;
			while (-1 != (bytesRead = fis.read(buff, 0, buff.length))) {
				os.write(buff, 0, bytesRead);
			}
		}else{
			response.setContentType("text/html");
			java.io.PrintWriter pw=response.getWriter();
			pw.write("<script>alert('该文件不存在或已经被转移!');history.back();</script>");
			pw.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (fis != null)
			fis.close();
		if (os != null)
			os.close();
		 //out.close();
		out.clear();
		out = pageContext.pushBody();
	}
%>