package board;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class listAction extends ActionSupport {
	public static Reader reader;//파일 스트림을 위한 reader.
	public static SqlMapClient sqlMapper;//SqlMapClientAPI를 사용하기 위한 sqlMapper객체.
	
	private List<boardVO> list = new ArrayList<boardVO>();//게시물 자바빈(boardVO)를 담기위한 ArrayList객체 생성!
	
	private String searchKeyword;//검색 키워드!
	private int searchNum;//검색 인덱스(0:작성자,1:제목,2:내용)
	
	private int currentPage = 1; //현재 페이지
	private int totalCount; //총 게시물의 수
	private int blockCount = 10; //한 페이지의 게시물 수
	private int blockPage = 5; //한 화면에 보여줄 페이지 수
	private String pagingHtml; //페이징을 구현한 HTML
	private pagingAction page; //페이징 클래스 선언
	private int num=0;//검색이 아닌 일반 list를 보여줄때 searchNum=0이라 선언! 
	
	//생성자(준비작업:sqlMapConfig.xml를 사용하기 위한)
	public listAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");//sqlMapConfig.xml파일의 설정내용을 가져온다.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);//sqlMapConfig.xml의 내용을 적용한 sqlMapper객체 생성
		reader.close();
	}
	//ActionSupport의 execute()메소드 오버라이딩
	public String execute() throws Exception{
		
		if(getSearchKeyword() != null) {//검색 결과가 있으면
			return search();//search()메소드에서 처리
		}
		
		list = sqlMapper.queryForList("selectAll");//테이블에 있는 모든 레코드 list에 집어 넣자!
		
		totalCount = list.size();//전체 게시글의 개수를 구한다.
		
		//ex(현재페이지1,총게시물수11개,한 화면에 보여줄 게시물10개,한 화면에 보여줄 페이지수5개,searchNum에 0보내고,isSearch에 ""보내보자)
		page = new pagingAction(currentPage,totalCount,blockCount,blockPage,num,"");//pagingAction 객체 생성
		//"&nbsp;<b><font color='red'>1</font></b>&nbsp;<a href=listAction.action?currentPage=2>2</a>"가 넘어옴
		pagingHtml = page.getPagingHtml().toString();//페이지 HTML 생성.
		int lastCount = totalCount;//현재 페이지에서 보여 줄 마지막 글의 번호 설정.
		
		//9<11이기 때문에
		if(page.getEndCount()<totalCount)//현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면
			lastCount = page.getEndCount()+1;//lastCount를 +1번호로 설정 (lastCount는 10이됨)
		
		list = list.subList(page.getStartCount(), lastCount);//전체 리스트에서 현재 페이지만큼의 리스트만 가져온다. (
		
		return SUCCESS;
	}
	public String search() throws Exception{
		
		if(searchNum == 0) { //검색 인덱스가 작성자이면 검색 키워드가 포함된 레코드 가져와서 리스트에 저장!
			list = sqlMapper.queryForList("selectSearchW","%"+getSearchKeyword()+"%");
		}
		if(searchNum == 1) {//검색 인덱스가 제목이면 검색키워드가 포함된 레코드 가져와서 리스트에 저장!
			list = sqlMapper.queryForList("selectSearchS","%"+getSearchKeyword()+"%");
		}
		if(searchNum == 2) {//검색 인덱스가 내용이면 검색키워드가 포함된 레코드 가져와서 리스트에 저장!
			list = sqlMapper.queryForList("selectSearchC","%"+getSearchKeyword()+"%");
		}
		
		totalCount = list.size();//검색된 게시글의 개수를 구한다.
		//검색된 페이징 결과를 받아온다.
		page = new pagingAction(currentPage,totalCount,blockCount,blockPage,searchNum,getSearchKeyword());
		pagingHtml = page.getPagingHtml().toString();
		
		int lastCount = totalCount;
		
		if(page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		
		list = list.subList(page.getStartCount(), lastCount);
		return SUCCESS;
	}

	public List<boardVO> getList() {
		return list;
	}

	public void setList(List<boardVO> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public String getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	public pagingAction getPage() {
		return page;
	}

	public void setPage(pagingAction page) {
		this.page = page;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public int getSearchNum() {
		return searchNum;
	}
	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}
	
	
}
