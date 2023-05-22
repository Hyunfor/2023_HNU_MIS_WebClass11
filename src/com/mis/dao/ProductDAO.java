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
	
	public static ProductDAO getInstance(){
		
		return instance;
		
	}
	
	// 전체 상품 목록 가져오기
	public List<ProductVO> selectAllProducts(){
		
		String sql = "SELECT * FROM PRODUCT ORDER BY CODE DESC";
		List<ProductVO> list = new ArrayList<ProductVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				ProductVO pVo = new ProductVO();
				pVo.setCode(rs.getInt("code"));
				pVo.setName(rs.getString("name"));
				pVo.setPrice(rs.getInt("price"));
				pVo.setPictureUrl(rs.getString("pictureurl"));
				pVo.setDescription(rs.getString("description"));
				
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

}
