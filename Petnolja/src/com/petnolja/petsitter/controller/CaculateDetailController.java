package com.petnolja.petsitter.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petnolja.admin.model.vo.Admin;
import com.petnolja.common.model.vo.PageInfo;
import com.petnolja.petsitter.model.service.PetsitterService;
import com.petnolja.petsitter.model.vo.Petsitter;

/**
 * Servlet implementation class CaculateDetailController
 */
@WebServlet("/calculateDetail.ad")	// 추후 수정
public class CaculateDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaculateDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Admin loginAdmin = (Admin)request.getSession().getAttribute("loginAdmin");
		
		if(loginAdmin == null) {
			
			request/*.getSession()*/.setAttribute("errorMsg", "접근이 불가능합니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		} else {
			
			int sitterNo = Integer.parseInt(request.getParameter("sno"));	// 추후 추가해줘야함
			int month = Integer.parseInt(request.getParameter("month")); // 추후 추가해줘야함
			
			// 페이징
			int listCount; 		// 현재 총 게시글 갯수 
			int currentPage;	// 현재 페이지 (즉, 사용자가 요청한 페이지)
			int pageLimit;		// 페이지 하단에 보여질 페이징바의 페이지 최대갯수 (몇개 단위씩)
			int boardLimit;		// 한 페이지내에 보여질 게시글 최대갯수 (몇개 단위씩)
			
			int maxPage;		// 가장 마지막 페이지 (총 페이지 수)
			int startPage;		// 페이지 하단에 보여질 페이징바의 시작수 
			int endPage;		// 페이지 하단에 보여질 페이징바의 끝수
			
			listCount = new PetsitterService().calculateDetailCount();
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			
			pageLimit = 5;
			boardLimit = 10;
			
			maxPage = (int)Math.ceil((double)listCount / boardLimit);
			
			
			startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
			
			endPage = startPage + pageLimit - 1;
			
			if(endPage > maxPage) {
				endPage = maxPage;
			}
			
			PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
			
			ArrayList<Petsitter> list = new PetsitterService().selectCalculateDetail(pi);
			
			request.setAttribute("pi", pi);
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("views/admin/calculateDetailView.jsp").forward(request, response);	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
