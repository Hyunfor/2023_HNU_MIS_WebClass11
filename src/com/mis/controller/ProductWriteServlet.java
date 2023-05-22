package com.mis.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.management.loading.MLet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mis.dao.ProductDAO;
import com.mis.dto.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class ProductWriteServlet
 */
@WebServlet("/productWrite.do")
public class ProductWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductWriteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 상품 등록 페이지로 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/productWrite.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 상품 등록

		request.setCharacterEncoding("UTF-8");

		// 첨부파일 경로 설정
		String savePath = "upload";

		// 첨부파일 사이즈 5MB
		int uploadFileSizeLimit = 20 * 1024 * 1024;

		// 첨부파일 인코딩
		String encType = "UTF-8";

		// 첨부파일 경로 확인 - server.xml에서 context 받아오기
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);

		// MultipartRequest 객체 생성
		// 순서 request , 실제 첨부파일 경로, 첨부파일 사이즈, 인코딩, 중복되는 첨부 파일 이름 재설정
		MultipartRequest mulit = new MultipartRequest(request, uploadFilePath, uploadFileSizeLimit, encType,
				new DefaultFileRenamePolicy());
		
		String name = mulit.getParameter("name");
		int price = Integer.parseInt(mulit.getParameter("price"));
		String description = mulit.getParameter("description");
		String pictureUrl = mulit.getParameter("pictureUrl");
		
		// 상품정보 VO 담기
		ProductVO pVo = new ProductVO();
		pVo.setName(name);
		pVo.setPrice(price);
		pVo.setPictureUrl(pictureUrl);
		pVo.setDescription(description);
		
		// 데이터베이스 상품 정보 등록하기
		ProductDAO pDao = ProductDAO.getInstance();
		pDao.insertProduct(pVo);
		
		// 상품 목록 페이지 이동
		response.sendRedirect("productList.do");

	}

}
