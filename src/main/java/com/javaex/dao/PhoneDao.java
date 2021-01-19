package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "phonedb", "phonedb"); // db url, 아이디, 비밀번호

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void dbDisConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void PhoneInsert(PersonVo personVo) {
		dbConnect();
		try {

			String query = "";
			query += "insert into person values(seq_person_id.nextval, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			int count = pstmt.executeUpdate();
			System.out.println("[" + count + "건이 등록되었습니다.]");

		} catch (Exception e) {
			System.out.println("error:" + e);
		}
		dbDisConnect();
	}

	public void PhoneUpdate(PersonVo personVo) {
		dbConnect();
		try {

			String query = "";
			query +="update person ";
			query +="set name = ?, ";
			query +="    hp = ?, ";
			query +="    company = ? ";
			query +="where person_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPerson_id());
			
			int count = pstmt.executeUpdate();
			System.out.println("[" + count + "건이 수정되었습니다.]");

		} catch (Exception e) {
			System.out.println("error:" + e);
		}
		dbDisConnect();

	}

	public void PhoneDelete(int num) {
		dbConnect();
		
		try {
			String query = "";
			query += "delete from person where person_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			
			int count = pstmt.executeUpdate();
			System.out.println("[" + count + "건이 삭제되었습니다.]");
			
		} catch (Exception e) {
			System.out.println("error:" + e);
		}
		dbDisConnect();
	}

	public List<PersonVo> getPhoneList() {
		dbConnect();
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			String query = "";
			query += "select person_id, ";
			query += "		 name, ";
			query += "	     hp,";
			query += "		 company ";
			query += "from person";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PersonVo pVo = new PersonVo();
				pVo.setPerson_id(rs.getInt(1));
				pVo.setName(rs.getString(2));
				pVo.setHp(rs.getString(3));
				pVo.setCompany(rs.getString(4));
				
				personList.add(pVo);
			}
			
		}catch(Exception e) {
			System.out.println("error:" + e);
		}
		
		dbDisConnect();
		return personList;
	}
	public List<PersonVo> getSearchPhoneList(String word) {
		dbConnect();
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			String query = "";
			query += "select person_id, ";
			query += "		 name, ";
			query += "	     hp,";
			query += "		 company ";
			query += "from person ";
			query += "where name like ? ";
			query += "or hp like ? ";
			query += "or company like ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setString(2, "%"+word+"%");
			pstmt.setString(3, "%"+word+"%");
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				PersonVo pVo = new PersonVo();
				pVo.setPerson_id(rs.getInt("person_id"));
				pVo.setName(rs.getString(2));
				pVo.setHp(rs.getString(3));
				pVo.setCompany(rs.getString(4));
				
				personList.add(pVo);
			}
			
		}catch(Exception e) {
			System.out.println("error:" + e);
		}
		
		dbDisConnect();
		return personList;
	}
	public PersonVo getPerson(int id) {
		dbConnect();
		PersonVo pVo = new PersonVo();
		try {
			String query="";
			query+="select person_id, ";
			query+=		  "name, ";
			query+=		  "hp, ";
			query+=		  "company ";
			query+="from person ";
			query+="where person_id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pVo.setPerson_id(rs.getInt("person_id"));
				pVo.setName(rs.getString(2));
				pVo.setHp(rs.getString(3));
				pVo.setCompany(rs.getString(4));
			}
			
		}catch(Exception e) {
			System.out.println("error: "+e);
		}
		dbDisConnect();
		return pVo;
	}
}
