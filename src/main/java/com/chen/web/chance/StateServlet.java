package com.chen.web.chance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Account;
import com.chen.entity.ChanceEvent;
import com.chen.service.ChanceService;
import com.chen.service.EventService;
import com.chen.web.Controller;

@WebServlet("/chance/changestate.do")
public class StateServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String chanceid = request.getParameter("id");
		ChanceEvent ce = new ChanceEvent();
		ChanceService chs = new ChanceService();
		EventService es = new EventService();
		Map<String, Object>map = new HashMap<String, Object>();
		if(StringUtils.isNumeric(chanceid)){
			map.put("result", "error");
			map.put("message", "��������");
		}
		Long id = Long.valueOf(chanceid);
		Account account = (Account)request.getSession().getAttribute("account");
		/////
		ce.setEvchanceid(id);
		ce.setEvaccountid(account.getId());
		ce.setEvcreatetime(getNowtime());
		/////
		if("success".equals(type)){
			if(chs.chanceSuccess(id) >0){
				map.put("result", "success");
				map.put("message", "�¼��ѳɹ�");
				ce.setEvcontent("�¼�����Ϊ�ɹ�");
				ce.setEvstate(1);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "����success����");
			}
		}else if("fail".equals(type)){
			if(chs.chanceFail(id) > 0){
				map.put("result", "success");
				map.put("message", "�¼��Ѹ�Ϊʧ��");
				ce.setEvcontent("�¼�����Ϊʧ��");
				ce.setEvstate(2);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "����fail����");
			}
		}else if("doing".equals(type)){
			if(chs.chanceDoing(id) > 0){
				map.put("result", "success");
				map.put("message", "�¼����ڽ���");
				ce.setEvcontent("����Ϊ�ɸ���");
				ce.setEvstate(0);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "ʱ��doing����");
			}
		}else if("over".equals(type)){
			if(chs.chanceOver(id) > 0){
				map.put("result", "success");
				map.put("message", "�¼��Ѹ�Ϊ�ս�");
				ce.setEvcontent("ʱ�䴦��Ϊ�ս�");
				ce.setEvstate(3);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "ʱ��over����");
			}
		}else{
			map.put("result", "error");
			map.put("message", "����ʽ����");
		}
		renderJson(response, map);
	}
}
