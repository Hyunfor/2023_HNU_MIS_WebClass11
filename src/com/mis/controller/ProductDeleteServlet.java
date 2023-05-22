package com.mis.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mis.dao.ProductDAO;
import com.mis.dto.ProductVO;

/**
 * Servlet implementation class ProductDeleteServlet
 */
@WebServlet("/productDelete.do")
public class ProductDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 사용자가 선택한 상품 코드를 이용해서 -> 데이터 베이스에 정보를 조회 - >
		// 상품정보를 가져와 상품 삭제 화면으로 이동
		String code = request.getParameter("code");

		// ProductDAO를 통해 상품 정보(ProductVO) 받기
		ProductDAO pDao = ProductDAO.getInstance();
		ProductVO pVo = pDao.selectProductByCode(code);

		// request 객체에 ProductVO값 설정
		request.setAttribute("product", pVo);

		// 상품 삭제 페이지 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/productDelete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = request.getParameter("code");
		
		// ProductDAO를 통해 상품 삭제
		ProductDAO pDao = ProductDAO.getInstance();
		pDao.deleteProduct(code);
		
		response.sendRedirect("productList.do");
		
	}

}
