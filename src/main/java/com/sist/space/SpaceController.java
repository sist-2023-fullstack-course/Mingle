package com.sist.space;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.Authentication.AuthenticationService;
import com.sist.Authentication.MemberVO;

import oracle.jdbc.proxy.annotation.Post;

@Controller
public class SpaceController {
	private SpaceService service;
	private AuthenticationService authservice;
	
	@Autowired
	public SpaceController(SpaceService service, AuthenticationService authservice) {
		this.service = service;
		this.authservice = authservice;
	}

	@GetMapping("space/main.do")
	public String space_main()
	{
		return "space/list";
	}
	
	@GetMapping("space/detail.do")
	public String space_detail(int space_id, Model model)
	{
		int region_code=service.substrRegionCode(space_id);
		List<SpaceVO> list=service.listingNearby(region_code);
		for(SpaceVO vo:list)
		{
			String[] addrarr=vo.getAddress().split(" ");
			String addr=addrarr[0]+' '+addrarr[1];
			vo.setAddress(addr);
		}
		model.addAttribute("list",list);
		return "space/detail";
	}
	
	@GetMapping("space/booking.do")
	public String space_booking(@RequestParam Map<String, Object> params, int no, Model model, HttpSession session)
	{
		String user_id=(String)session.getAttribute("id");
		MemberVO mvo = authservice.getMemberByID(user_id);
		SpaceVO svo = service.spaceBookingData(no);
		model.addAttribute("book", params);
		model.addAttribute("vo", svo);
		model.addAttribute("no", no);
		model.addAttribute("mvo", mvo);
		model.addAttribute("user_id", user_id);
		return "space/booking";
	}
	
	@PostMapping("space/submitbook.do")
	public String space_submit_booking(@RequestParam Map<String, Object> params, HttpSession session)
	{
		String user_id=(String)session.getAttribute("id");
		params.put("user_id", user_id);
		service.spaceBookingSubmit(params);
		// 예약내역 상세보기 페이지로 갈지
		// if 아니면 마저 공간내역을 볼지
		
		return "redirect:/space/main.do";
	}
	
	@PostMapping("space/submitreview.do")
	public String space_submit_review(@RequestParam Map<String, String> params, HttpSession session)
	{
		/*
		 * rv_id, ratings,regdate,user_id,space_id,content
		 */
		String user_id=(String)session.getAttribute("id");
		ReviewVO vo=new ReviewVO();
		vo.setRatings(Integer.parseInt(params.get("ratings")));
	    vo.setUser_id(user_id);
	    vo.setSpace_id(Integer.parseInt(params.get("space_id")));
	    vo.setContent(params.get("content"));
		service.createReview(vo);
		return "redirect:/space/detail.do?space_id="+params.get("space_id");
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
