package com.project.base.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.redoak.jar.base.model.ResultBean;

import net.sf.json.JSONObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

	/**
	 * @Description:捕获所有Controller中@RequestMapping注解的方法执行过程中抛出的Exception
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(value = { Exception.class })
	public ModelAndView handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		ex.printStackTrace();
		log.error(ex);
		/**
		 * 如果是ajax请求，将响应信息写入response的body
		 */
		if (isAjax(request)) {
			dealAjaxException(ex,response);
			return null;
		}

		return new ModelAndView("/error.jsp");
	}

	/**
	 * @Description:把ajax响应信息写入response body
	 */
	private void dealAjaxException(Exception ex, HttpServletResponse response) {
		ResultBean ri = new ResultBean(false, "操作失败，请联系管理员！");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSONObject.fromObject(ri).toString());
			writer.flush();
		} catch (IOException e) {
			log.error(e);
		} finally {
			writer.close();
		}
	}

	/**
	 * @Description:判断是否ajax请求
	 */
	private boolean isAjax(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
	}
}
