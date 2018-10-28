package board;

public class pagingAction {
	
	private int currentPage;//현재페이지
	private int totalCount;//전체 게시물 수
	private int totalPage;//전체 페이지 수
	private int blockCount;//한 페이지의 게시물의 수(10)
	private int blockPage;//한 화면에 보여줄 페이지 수(5)
	private int startCount;//한 페이지에서 보여줄 게시글의 시작 번호
	private int endCount;//한 페이지에서 보여줄 게시글의 끝 번호
	private int startPage;//시작페이지
	private int endPage;//마지막 페이지
	
	private StringBuffer pagingHtml;//연산된 모든 결과는 스트링버퍼에 넣어서 보여줄 것!
	
	//페이징 생성자
	public pagingAction(int currentPage,int totalCount,int blockCount,int blockPage,int searchNum,String isSearch) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
	
		//전체 페이지 수 ex)게시물수 101개->totalPage는 11개 , 게시물수 30개 ->totalPage는 3개 ...
		totalPage = (int)Math.ceil((double)totalCount/blockCount);
		
		if(totalPage==0) {//totalPage가 0이면->totalCount가 0, 즉 게시물이 없으면 
			totalPage=1;//totalPage는 1로 지정
		}
	
		//현재 페이지가 전체 페이지수보다 크면 전체 페이지 수로 설정! 이런일이 가능한가?
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
	
		//1페이지면 0 2페이지면 10 3페이지면 20 ...
		startCount = (currentPage-1) * blockCount;
		//1페이지면9 2페이지면19 3페이지면 29 ...
		endCount = startCount + blockCount -1;
	
		//1,2,3,4,5페이지 => 1  6,7,8,9,10페이지=>6 ...
		startPage = (int)((currentPage-1)/blockPage)*blockPage + 1;
		//1,2,3,4,5페이지 => 5  6,7,8,9,10페이지=>10 ...
		endPage = startPage + blockPage - 1;
	
		//마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정! 이런일이 가능한가?
		if(endPage>totalPage) {
			endPage = totalPage;
		}
	
		//이전 block 페이지
		pagingHtml = new StringBuffer();//스트링버퍼 객체 생성!
		if(currentPage>blockPage) { //현재 페이지가 블락페이지(5)보다 크면 이전을 표시할거야!
			if(isSearch != "")
				pagingHtml.append("<a href=listAction.action?currentPage=" + (startPage - 1) + "&searchKeyword="+isSearch+"&searchNum="+searchNum+">");
			else
				pagingHtml.append("<a href=listAction.action?currentPage="+(startPage-1)+">");
			pagingHtml.append("이전");
			pagingHtml.append("</a>");
		}
		pagingHtml.append("&nbsp;|&nbsp;");
	
		//페이지 번호,현재 페이지는 빨간색으로 강조하고 링크를 제거
		for(int i=startPage;i<=endPage;i++) {
			if(i>totalPage) {
				break;
			}
			if(i==currentPage) {//현재페이지에만 빨강색으로 표시
				pagingHtml.append("&nbsp;<b><font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append("</font></b>");
			}else {//나머지 페이지는 그냥 표시!
				pagingHtml.append("&nbsp;<a href=listAction.action?currentPage=");
				pagingHtml.append(i);
				if(isSearch != "")
					pagingHtml.append("$searchKeyword=" + isSearch);
				pagingHtml.append(">");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
			pagingHtml.append("&nbsp;");
		}
		
		pagingHtml.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
	
		//다음 block페이지
		if(totalPage - startPage >= blockPage) { //
			pagingHtml.append("&nbsp;<a href='list.action?currentPage=");
			pagingHtml.append((endPage+1));
			if(isSearch != "")
				pagingHtml.append("&searchKeyword=" + isSearch);
			pagingHtml.append("'>");
			pagingHtml.append("다음");
			pagingHtml.append("</a>");
		}
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
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

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public StringBuffer getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(StringBuffer pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

}
