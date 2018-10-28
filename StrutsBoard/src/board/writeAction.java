
package board;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class writeAction extends ActionSupport {
	public static Reader reader;//파일 스트림을 위한 reader
	public static SqlMapClient sqlMapper;//SqlMapClient API를 사용하기 위한 sqlMapper객체
	
	private boardVO paramClass;//파라미터를 저장할 객체
	private boardVO resultClass;//쿼리 결과값을 저장할 객체
	
	//boardList에서 넘어온 currentPage선언!
	private int currentPage;//현재 페이지
	
	//자바빈 9개중 8개 선언!
	private int no;//글번호
	private String subject;//제목
	private String name;//이름
	private String password;//비밀번호
	private String content;//내용
	private String file_orgName;//업로드 파일의 원래이름
	private String file_savName;//서버에 저장할 업로드 파일의 이름. 고유 번호로 구분한다.
	Calendar today = Calendar.getInstance();//오늘 날짜 구하기
	
	//upload할 준비! file태그 name과 동일한 upload로 지정! 
	private File upload;//파일객체
	private String uploadContentType;//컨텐트 타입
	private String uploadFileName;//파일 이름
	private String fileUploadPath = "C:\\myjava2\\upload\\";
	
	private int ref;
	private int re_step;
	private int re_level;
	
	boolean reply = false;
	
	//생성자에서 sqlMapConfig.xml사용할 준비!
	public writeAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");//sqlMapConfig.xml파일의 설정내용을 가져온다.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);//sqlMapConfig.xml의 내용을 적용한 sqlMapper객체 생성
		reader.close();
	}
	
	public String form() throws Exception{
		//등록폼!
		return SUCCESS;
	}
	
	public String reply() throws Exception{
		reply = true;
		resultClass = new boardVO();
		
		resultClass = (boardVO)sqlMapper.queryForObject("selectOne",getNo());
		resultClass.setSubject("[답변]" + resultClass.getSubject());
		resultClass.setPassword("");
		resultClass.setName("");
		resultClass.setContent("");
		resultClass.setFile_orgname(null);
		resultClass.setFile_savname(null);
		
		return SUCCESS;
	}
	
	//게시판 WRITE액션!
	public String execute() throws Exception{
		
		paramClass = new boardVO();
		resultClass = new boardVO();
		
		if(ref==0) {//일반 글쓰기 일때
			paramClass.setRe_step(0);
			paramClass.setRe_level(0);
		}
		else {//답변글일때
			paramClass.setRef(getRef());
			paramClass.setRe_step(getRe_step());
			sqlMapper.update("updateReplyStep",paramClass);
			
			paramClass.setRe_step(getRe_step()+1);
			paramClass.setRe_level(getRe_level()+1);
			paramClass.setRef(getRef());
			
		}
		//자바빈을 채운다.(params 인터셉터가 setter메서드로 폼 내용을 받아와서) 5개
		paramClass.setSubject(getSubject());
		paramClass.setName(getName());
		paramClass.setPassword(getPassword());
		paramClass.setContent(getContent());
		paramClass.setRegdate(today.getTime());
		
		if(ref==0)
			sqlMapper.insert("insertBoard",paramClass);
		else
			sqlMapper.insert("insertBoardReply",paramClass);
		
		//ex) AirplainIcon.png를 업로드 했다면 
		if(getUpload() != null) { //첨부파일을 선택했다면 업로드한다.
			resultClass = (boardVO)sqlMapper.queryForObject("selectLastNo"); //글번호 최대값 가져와
			
			String file_name = "file_" + resultClass.getNo(); //실제 서버에 저장될 파일 이름과(file_숫자)
			//png가 뽑힌다.
			String file_ext = getUploadFileName().substring(getUploadFileName().lastIndexOf('.')+1, getUploadFileName().length());//확장자 설정
			
			//file_숫자.png로 upload하려는 경로(fileUploadPath)로 저장
			File destFile = new File(fileUploadPath + file_name + "." +file_ext);
			FileUtils.copyFile(getUpload(), destFile); //알멩이 넣기
			
			//3개더 자바빈을 채운다.
			paramClass.setNo(resultClass.getNo());
			paramClass.setFile_orgname(getUploadFileName());//AirplainIcon.png
			paramClass.setFile_savname(file_name + "." +file_ext);//file_숫자.png
			
			//나머지 등록 쿼리 수행!
			sqlMapper.update("updateFile",paramClass);
		}
		
		return SUCCESS;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_orgName() {
		return file_orgName;
	}

	public void setFile_orgName(String file_orgName) {
		this.file_orgName = file_orgName;
	}

	public String getFile_savName() {
		return file_savName;
	}

	public void setFile_savName(String file_savName) {
		this.file_savName = file_savName;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getRe_step() {
		return re_step;
	}

	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}

	public int getRe_level() {
		return re_level;
	}

	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}

	public boardVO getResultClass() {
		return resultClass;
	}

	public void setResultClass(boardVO resultClass) {
		this.resultClass = resultClass;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}
	
	

}
