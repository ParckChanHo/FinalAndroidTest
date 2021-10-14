package child;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import board.board;

public class childDAO {
	private Connection conn;
	private ResultSet rs;
	
	public childDAO() {
		try {
			String dbURL = "jdbc:mysql://101.101.209.108:3306/capstone";
			String dbID = "root";
			String dbPassword = "M4LL?*ATyd";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		//NOW 함수는 현재 MySQL 서버의 시간 값을 가져오는 함수
		String sql="SELECT NOW()";  // 2018-01-11 11:21:36  즉 현재의 날짜와 시간을 반환해준다!!
		//The date and time is returned as "YYYY-MM-DD HH-MM-SS" (string) or as YYYYMMDDHHMMSS.uuuuuu (numeric).
		//YYYY(년도)-MM(월 01~12)-DD(일) HH(시, 0시~23시 일반적인 24시간 표시임 ex)오후 2시 >>>> 14시) -MM-SS
		
		//list.get(i).getBbsDate().substring(0,11) ===> 2021-07-25 이렇게 나옴!!! 그냥 이렇게 하자!
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		//NOW 함수는 현재 MySQL 서버의 시간 값을 가져오는 함수
		String sql="SELECT childId from childBoard ORDER BY childId DESC";  //내림차순으로  즉 큰 것 >>> 작은 것
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; 
				//childId int >> 계시글의 번호를 부여함 
				// 즉 다음 계시물의 번호를 반환하기 위해서 이렇게 함!! 예를 들어서 1번 다음에 2번
			}
			return 1; //현재 하나도 게시물이 없을 때
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String childContent,int parentId) { 
		String sql="insert into childBoard VALUES(?,?,?,?,?,?)";  //댓글 작성하기
		int nextNickname = getNext();
		String childNickname = "nickName"+nextNickname;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()); //childId
			pstmt.setString(2, childNickname); //childNickname
			pstmt.setString(3, getDate()); // childDate
			pstmt.setString(4, childContent); // childContent
			pstmt.setInt(5, 1); //삭제가 되지 않아서 ===> childAvailable
			pstmt.setInt(6, parentId);
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<child> getAllList(int parentId){ //모든 게시물을 반환한다.
		String sql="select * from childBoard where childAvailable = 1 and parentId = ? order by childId desc";  
		ArrayList<child> list = new ArrayList<child>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentId);//인자로 전달받은 parentId의 값에 해당하는 게시물을 선택한다!!! ==> 즉 해당 게시물에 해당되는 댓글들만 선택된다!!
			rs = pstmt.executeQuery();
			while(rs.next()) {
				child child = new child(); //1개의 게시물들을 모두 새로운 child 객체에다가 저장을 한다. 
				child.setChildId(rs.getInt(1));
				child.setChildNickname(rs.getString(2));
				child.setChildDate(rs.getString(3));
				child.setChildContent(rs.getString(4));
				child.setChildAvailable(rs.getInt(5));
				child.setParentId(rs.getInt(6));
				
				list.add(child);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/*public board getBoard(int boardId) { // bbsID에 해당하는 게시물을 반환한다.
		String sql="select * from board where boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);//인자로 전달받은 boardId의 값에 해당하는 게시물을 선택한다!!!
			rs = pstmt.executeQuery();
			if(rs.next()) {// 해당되는 게시물이 있으면
				board bbs = new board(); //1개의 게시물들을 모두 새로운 board객체에다가 저장을 
				bbs.setBoardId(rs.getInt(1));
				bbs.setBoardTitle(rs.getString(2));
				bbs.setBoardNickname(rs.getString(3));
				bbs.setBoardDate(rs.getString(4));
				bbs.setBoardContent(rs.getString(5));
				bbs.setBoardAvailable(rs.getInt(6));
				
				return bbs;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/*public int update(int childId, String boardTitle, String boardContent) {
		String SQL = "UPDATE board SET boardTitle=?, boardContent=?, boardDate=? WHERE boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, getDate());
			pstmt.setInt(4, boardId);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
	public int delete(int boardId) {
		String SQL = "UPDATE board SET boardAvailable = 0 WHERE boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}*/
}
