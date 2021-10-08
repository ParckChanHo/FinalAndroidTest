package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class boardDAO { //�����ͺ��̽��� �����Ͽ��� �����͸� �������� ������ �Ѵ�.!!!
	private Connection conn;
	private ResultSet rs;
	
	public boardDAO() {
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
		//NOW �Լ��� ���� MySQL ������ �ð� ���� �������� �Լ�
		String sql="SELECT NOW()";  // 2018-01-11 11:21:36  �� ������ ��¥�� �ð��� ��ȯ���ش�!!
		//The date and time is returned as "YYYY-MM-DD HH-MM-SS" (string) or as YYYYMMDDHHMMSS.uuuuuu (numeric).
		//YYYY(�⵵)-MM(�� 01~12)-DD(��) HH(��, 0��~23�� �Ϲ����� 24�ð� ǥ���� ex)���� 2�� >>>> 14��) -MM-SS
		
		//list.get(i).getBbsDate().substring(0,11) ===> 2021-07-25 �̷��� ����!!! �׳� �̷��� ����!!!
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
		return ""; //�����ͺ��̽� ����
	}
	
	public int getNext() {
		//NOW �Լ��� ���� MySQL ������ �ð� ���� �������� �Լ�
		String sql="SELECT boardId from board ORDER BY boardId DESC";  //������������  �� ū �� >>> ���� ��
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; 
				//boardId int >> ��ñ��� ��ȣ�� �ο��� 
				// �� ���� ��ù��� ��ȣ�� ��ȯ�ϱ� ���ؼ� �̷��� ��!! ���� �� 1�� ������ 2��
			}
			return 1; //���� �ϳ��� �Խù��� ���� ��
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int write(String boardTitle,String boardContent) { 
		String sql="insert into board VALUES(?,?,?,?,?,?)";  //�Խù� �ۼ��ϱ�
		int nextNickname = getNext();
		String boardNickname = "nickName"+nextNickname;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()); //boardId
			pstmt.setString(2, boardTitle); // boardTitle
			pstmt.setString(3, boardNickname); //boardNickname
			pstmt.setString(4, getDate()); // boardDate
			pstmt.setString(5, boardContent); // boardContent
			pstmt.setInt(6, 1); //������ ���� �ʾƼ� ===> boardAvailable
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public ArrayList<board> getAllList(){ //��� �Խù��� ��ȯ�Ѵ�.
		String sql="select * from board where boardAvailable = 1 order by boardId desc";  
		ArrayList<board> list = new ArrayList<board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board bbs = new board(); //1���� �Խù����� ��� ���ο� board��ü���ٰ� ������ 
				bbs.setBoardId(rs.getInt(1));
				bbs.setBoardTitle(rs.getString(2));
				bbs.setBoardNickname(rs.getString(3));
				bbs.setBoardDate(rs.getString(4));
				bbs.setBoardContent(rs.getString(5));
				bbs.setBoardAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public board getBoard(int boardId) { // bbsID�� �ش��ϴ� �Խù��� ��ȯ�Ѵ�.
		String sql="select * from board where boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);//���ڷ� ���޹��� boardId�� ���� �ش��ϴ� �Խù��� �����Ѵ�!!!
			rs = pstmt.executeQuery();
			if(rs.next()) {// �ش�Ǵ� �Խù��� ������
				board bbs = new board(); //1���� �Խù����� ��� ���ο� board��ü���ٰ� ������ 
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
	}
	
	public int update(int boardId, String boardTitle, String boardContent) {
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
	}
	
}
