package com.chen.web.admin.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.service.UserService;
import com.chen.web.Controller;

@WebServlet("/admin/user/deluser.do")
public class DelServlet extends Controller{

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			response.sendError(401, "Ȩ��ʧЧ");
			return;
		}
		String userid = request.getParameter("id");
		if(userid == null){
			response.sendError(401, "Ȩ��ʧЧ");
			return;
		}
		Long id = null;
		if(StringUtils.isNumeric(userid)){
			id = Long.valueOf(userid);
		}else{
			response.sendError(401, "Ȩ��ʧЧ");
			return;
		}
		if(id < 0){
			map.put("result", "error");
			map.put("message","�벻Ҫ���ж��ѹ�����");
			//return;
		}
		UserService us = new UserService();
		
		if(us.delUser(id) > 0){
			map.put("result", "success");
			map.put("message", "�û���ɾ��");
			//response.sendRedirect("/admin/list.do");
			//return;
		}else{
			map.put("result", "error");
			map.put("message", "�û�������");
			//return;
		}
		renderJson(response, map);
	}

}
