package com.chen.web.schedule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.service.ScheduleService;
import com.chen.web.Controller;

@WebServlet("/schedule/undo.do")
public class UndoneServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			map.put("result", "error");
			map.put("message", "�Ƿ�����");
			renderJson(response, map);
			return;
		}
		
		String id = request.getParameter("id");
		
		if(!StringUtils.isNumeric(id)){
			map.put("result", "error");
			map.put("message", "��Ч����");
			renderJson(response, map);
			return;
		}
		Long scheduleid = Long.valueOf(id);
		if(scheduleid < 0){
			map.put("result", "error");
			map.put("message", "�޸��û�");
			renderJson(response, map);
			return;
		}
		
		ScheduleService schs = new ScheduleService();
		if(schs.updateUndoState(scheduleid) > 0){
			map.put("result", "success");
			map.put("message", "ok");
			map.put("value", scheduleid);
		}else{
			map.put("result", "error");
			map.put("message", "������æ");
		}
		renderJson(response, map);
	}
}
