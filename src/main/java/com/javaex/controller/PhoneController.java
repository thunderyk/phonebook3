package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping("/phone")
public class PhoneController {

	//메소드 단위로 기능을 정의
	
	@RequestMapping(value="/list",method= {RequestMethod.GET,RequestMethod.POST})
	public String list(Model model) {
		
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPhoneList();
		model.addAttribute("pList",personList);
		
		
		return "/WEB-INF/views/list.jsp";
	}
	
	@RequestMapping(value="/writeForm",method= {RequestMethod.GET,RequestMethod.POST})
	public String writeForm() {
		
		return "/WEB-INF/views/writeForm.jsp";
	}
	
	@RequestMapping(value="/write",method= {RequestMethod.GET,RequestMethod.POST})
	public String write(@RequestParam("name") String name,
						@RequestParam("hp") String hp,
						@RequestParam("company") String company) {
		
		PersonVo personVo = new PersonVo(name,hp,company);
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.PhoneInsert(personVo);
		
		return "redirect:./list";
	}
	
	@RequestMapping(value="/delete",method= {RequestMethod.GET,RequestMethod.POST})
	public String delete(@RequestParam("id") int id) {
		
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.PhoneDelete(id);
		
		return "redirect:./list";
	}
	
	@RequestMapping(value="/modifyForm",method= {RequestMethod.GET,RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("id") int id) {
		
		PhoneDao phoneDao = new PhoneDao();
		
		PersonVo personVo = phoneDao.getPerson(id);
		model.addAttribute("personVo",personVo);
		
		return "/WEB-INF/views/modifyForm.jsp";
	}
	
	@RequestMapping(value="/modify",method= {RequestMethod.GET,RequestMethod.POST})
	public String modify(@RequestParam("id") int id,
			             @RequestParam("name") String name,
			             @RequestParam("hp") String hp,
			             @RequestParam("company") String company) {
		
		PersonVo personVo = new PersonVo(id,name,hp,company);
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.PhoneUpdate(personVo);
		
		return "redirect:./list";
	}
	//피드
	
	//생성자
	
	//메소드 g/s
	//메소드 기능 1개씩
		//리스트
		//등록폼
		//수정
		//수정폼
		//삭제
	
	
}
