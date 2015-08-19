package com.chen.web.admin.dept;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.service.DeptService;
import com.chen.web.Controller;

@WebServlet("/admin/dept/change.do")
public class ChangeServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forward("dept/change.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sid = request.getParameter("id");
		String deptname = request.getParameter("deptname");
		Map<String, Object> map = new HashMap<String, Object>();
		if(sid == null || StringUtils.isNumeric(sid)){
			map.put("result", "error");
			map.put("message", "�Ƿ�����");
		}
		Long id = Long.valueOf(sid);
		if(id < 0){
			map.put("result", "error");
			map.put("message", "idΪ�������Ƿ�����");
			map.put("value", deptname);
		}
		if(deptname == null){
			map.put("result", "error");
			map.put("message", "���������Ϊ�գ��Ƿ�����");
			map.put("value", deptname);
		}
		DeptService ds = new DeptService();
		if(ds.changeDept(deptname, id) > 0){
			map.put("result", "success");
			map.put("message", "�޸ĳɹ�");
			map.put("value", deptname);
		}else{
			map.put("result", "error");
			map.put("message", "id������");
			map.put("value", deptname);
		}
		renderJson(response, map);
	}
	
}
