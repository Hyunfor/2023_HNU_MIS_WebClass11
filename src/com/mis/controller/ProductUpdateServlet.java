package com.mis.controller;

import java.io.IOException;

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
 * Servlet implementation class ProductUpdateServlet
 */
@WebServlet("/productUpdate.do")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductUpdateServlet() {
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
		// 상품정보를 가져와 상품 수정화면으로 이동
		String code = request.getParameter("code");

		// ProductDAO를 통해 상품 정보(ProductVO) 받기
		ProductDAO pDao = ProductDAO.getInstance();
		ProductVO pVo = pDao.selectProductByCode(code);

		// request 객체에 ProductVO값 설정
		request.setAttribute("product", pVo);

		// 상품 수정 페이지 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/productUpdate.jsp");
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

		// MultipartRequest를 사용하여 사용자로 부터 입력받은 상품 정보 파라미터 가져오기
		String code = mulit.getParameter("code");
		String name = mulit.getParameter("name");
		int price = Integer.parseInt(mulit.getParameter("price"));
		String description = mulit.getParameter("description");
		// <input type="file"> 인 경우 -> getFIlesystemName 이름 가져오기
		String pictureUrl = mulit.getParameter("pictureUrl");

		// 기존 이미지를 사용할 경우 nonmakeImg에 저장된 과거의 이미지로 설정
		if (pictureUrl == null) {
			pictureUrl = mulit.getParameter("nonmakeImg");
		}

		// 상품정보 VO 담기
		ProductVO pVo = new ProductVO();
		pVo.setCode(Integer.parseInt(code));
		pVo.setName(name);
		pVo.setPrice(price);
		pVo.setPictureUrl(pictureUrl);
		pVo.setDescription(description);

		// 데이터베이스 상품 정보 수정
		ProductDAO pDao = ProductDAO.getInstance();
		pDao.updateProduct(pVo);

		// 상품 목록 페이지 이동
		response.sendRedirect("productList.do");

	}

}
