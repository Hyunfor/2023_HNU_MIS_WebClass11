package com.mis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mis.dto.ProductVO;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_OVERLAYPeer;

import util.DBManager;

public class ProductDAO {

	// 싱글톤의 기본 패턴
	private static ProductDAO instance = new ProductDAO();

	public static ProductDAO getInstance() {

		return instance;

	}

	// 전체 상품 목록 가져오기
	public List<ProductVO> selectAllProducts() {

		String sql = "SELECT * FROM PRODUCT ORDER BY CODE DESC";
		List<ProductVO> list = new ArrayList<ProductVO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 결과 값으로 리스트에 담기
			while (rs.next()) {

				ProductVO pVo = new ProductVO();
				pVo.setCode(rs.getInt("code"));
				pVo.setName(rs.getString("name"));
				pVo.setPrice(rs.getInt("price"));
				pVo.setPictureUrl(rs.getString("pictureurl"));
				pVo.setDescription(rs.getString("description"));

				// 리스트에 담기
				list.add(pVo);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBManager.close(conn, pstmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;

	}

	// 상품 등록하기
	public void insertProduct(ProductVO pVo) {

		String sql = "INSERT INTO PRODUCT VALUES(PRODUCT_SEQ.nextval, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, pVo.getName());
			pstmt.setInt(2, pVo.getPrice());
			pstmt.setString(3, pVo.getPictureUrl());
			pstmt.setString(4, pVo.getDescription());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 리소스 해제
				DBManager.close(conn, pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 상품 상세페이지
	public ProductVO selectProductByCode(String code) {

		String sql = "SELECT * FROM PRODUCT WHERE CODE=?";
		ProductVO pVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			// code 변수 바인딩
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductVO();
				pVo.setCode(rs.getInt("code"));
				pVo.setName(rs.getString("name"));
				pVo.setPrice(rs.getInt("price"));
				pVo.setPictureUrl(rs.getString("pictureUrl"));
				pVo.setDescription(rs.getString("description"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBManager.close(conn, pstmt, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pVo;
	}

	// 상품 수정
	public void updateProduct(ProductVO pVo) {

		String sql = "UPDATE PRODUCT SET NAME=?, PRICE=?, PICTUREURL=?, DESCRIPTION=? WHERE CODE=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			// ProductVO 변수 바인딩
			pstmt.setString(1, pVo.getName());
			pstmt.setInt(2, pVo.getPrice());
			pstmt.setString(3, pVo.getPictureUrl());
			pstmt.setString(4, pVo.getDescription());
			pstmt.setInt(5, pVo.getCode());

			// 쿼리 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBManager.close(conn, pstmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	// 상품 삭제
	public void deleteProduct(String code){
		String sql = "DELETE PRODUCT WHERE CODE=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// code 변수 바인딩
			pstmt.setString(1, code);
			//쿼리 실행
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				DBManager.close(conn, pstmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
	
	// 상품 등록시 이미지를 등록하면 파일에 업로드가 되나 상세페이지에선
	// 업로드한 이미지와 관계 없는 (자바의 신)이미지가 지속해서 나오는 문제.
	// 수정시에 이미지를 등록하면 파일에는 업로드가 되나 
	// 화면에선 바뀌지 않는 문제를 해결해야함.
	// 그외에는 전부 정상 작동함. 콘솔창에 에러는 뜨지 않음.
}
