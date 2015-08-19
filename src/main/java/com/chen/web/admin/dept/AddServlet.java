package com.chen.web.admin.dept;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.service.DeptService;
import com.chen.web.Controller;

@WebServlet("/admin/dept/add.do")
public class AddServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		forward("dept/list.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			map.put("success", "error");
			map.put("success", "����������");
			renderJson(response, map);
			return;
		}
		String deptname = request.getParameter("deptname");
		if(deptname == null){
			map.put("result", "error");
			map.put("message", "�������ֲ���Ϊ��");
		}
		DeptService ds = new DeptService();
		Long id = ds.addDept(deptname);
		if(id > 0){
			map.put("result", "success");
			map.put("value", id);
			map.put("message", deptname);
		}else{
			map.put("result", "error");
			map.put("message", deptname +"�Ѿ�����");
		}
		renderJson(response, map);
		
	}
	
}
