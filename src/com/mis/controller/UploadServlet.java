package com.mis.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 이미지는 Post 방식
		// 파일 업로드 기능
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 결과화면 출력
		PrintWriter out = response.getWriter();
		
		// 첨부파일 경로 설정
		String savePath = "upload";
		
		// 첨부파일 사이즈 5MB
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		
		// 첨부파일 인코딩
		String encType = "UTF-8";
		
		// 첨부파일 경로 확인 - server.xml에서 context 받아오기
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		System.out.println("실제 첨푸파일 저장 경로: ");
		System.out.println(uploadFilePath);
		
		try {
			
			//MultipartRequest 객체 생성
			//순서 request , 실제 첨부파일 경로, 첨부파일 사이즈, 인코딩, 중복되는 첨부 파일 이름 재설정 
			MultipartRequest mulit = new MultipartRequest(request, uploadFilePath,
					uploadFileSizeLimit, encType, new DefaultFileRenamePolicy());
			
			// 첨부 파일 가져오기
			String fileName = mulit.getFilesystemName("uploadFile");
			
			// 예외처리 ( 이미지 업로드 없이 글 쓸때)
			if(fileName == null){
				System.out.println("첨부파일 없음");
			} else {
				out.println("<br> 글쓴이: " + mulit.getParameter("name"));
				out.println("<br> 제목: " + mulit.getParameter("title"));
				out.println("<br> 첨부파일: " + fileName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
