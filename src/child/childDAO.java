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
		//NOW �Լ��� ���� MySQL ������ �ð� ���� �������� �Լ�
		String sql="SELECT NOW()";  // 2018-01-11 11:21:36  �� ������ ��¥�� �ð��� ��ȯ���ش�!!
		//The date and time is returned as "YYYY-MM-DD HH-MM-SS" (string) or as YYYYMMDDHHMMSS.uuuuuu (numeric).
		//YYYY(�⵵)-MM(�� 01~12)-DD(��) HH(��, 0��~23�� �Ϲ����� 24�ð� ǥ���� ex)���� 2�� >>>> 14��) -MM-SS
		
		//list.get(i).getBbsDate().substring(0,11) ===> 2021-07-25 �̷��� ����!!! �׳� �̷��� ����!
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
		String sql="SELECT childId from childBoard ORDER BY childId DESC";  //������������  �� ū �� >>> ���� ��
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; 
				//childId int >> ��ñ��� ��ȣ�� �ο��� 
				// �� ���� ��ù��� ��ȣ�� ��ȯ�ϱ� ���ؼ� �̷��� ��!! ���� �� 1�� ������ 2��
			}
			return 1; //���� �ϳ��� �Խù��� ���� ��
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int write(String childContent,int parentId) { 
		String sql="insert into childBoard VALUES(?,?,?,?,?,?)";  //��� �ۼ��ϱ�
		int nextNickname = getNext();
		String childNickname = "nickName"+nextNickname;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()); //childId
			pstmt.setString(2, childNickname); //childNickname
			pstmt.setString(3, getDate()); // childDate
			pstmt.setString(4, childContent); // childContent
			pstmt.setInt(5, 1); //������ ���� �ʾƼ� ===> childAvailable
			pstmt.setInt(6, parentId);
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public ArrayList<child> getAllList(int parentId){ //��� �Խù��� ��ȯ�Ѵ�.
		String sql="select * from childBoard where childAvailable = 1 and parentId = ? order by childId desc";  
		ArrayList<child> list = new ArrayList<child>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentId);//���ڷ� ���޹��� parentId�� ���� �ش��ϴ� �Խù��� �����Ѵ�!!! ==> �� �ش� �Խù��� �ش�Ǵ� ��۵鸸 ���õȴ�!!
			rs = pstmt.executeQuery();
			while(rs.next()) {
				child child = new child(); //1���� �Խù����� ��� ���ο� child ��ü���ٰ� ������ �Ѵ�. 
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
	
	
	/*public board getBoard(int boardId) { // bbsID�� �ش��ϴ� �Խù��� ��ȯ�Ѵ�.
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
