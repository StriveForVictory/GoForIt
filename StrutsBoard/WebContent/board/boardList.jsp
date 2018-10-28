<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="EUC-KR">
	<title>스트럿츠 2 게시판</title>
	<link rel="stylesheet" href="/StrutsBoard/board/common/css/css.css" type="text/css">
</head>
<body>

<table width="600" border="0" cellspacing="0" cellpadding="2">
<tr>
	<td align="center"><h2>스트럿츠 2 게시판</h2></td>
</tr>
<tr>
	<td height="20"></td>
</tr>
</table>

<table width="600" border="0" cellspacing="0" cellpadding="2">
<tr align="center" bgcolor="#F3F3F3">
	<td width="50"><strong>번 호</strong></td>
	<td width="350"><strong>제 목</strong></td>
	<td width="70"><strong>글쓴이</strong></td>
	<td width="80"><strong>날 짜</strong></td>
	<td width="50"><strong>조 회</strong></td>
</tr>
<tr bgcolor="#777777">
	<td height="1" colspan="5"></td>
</tr>

<s:iterator value="list" status="stat"> <!-- ArrayList인 list를 반복하는데 status에는 반복되는 인스턴스가 들어간다 ex)#stat.index,#stat.first... -->
	<s:url id="viewURL" action="viewAction">
		<s:param name="no">
			<s:property value="no"/>
		</s:param>
		<s:param name="currentPage">
			<s:property value="currentPage"/>
		</s:param>
	</s:url>
	
	<tr bgcolor="#FFFFFF" align="center">
		<td><s:property value="no"/></td> <!-- 번호 -->
		<td align="left">
			<s:if test="re_level != 0">
				<c:forEach var = "i" begin="0" end="${re_level}">&nbsp;&nbsp;</c:forEach>→
			</s:if>
			<s:a href="%{viewURL}"><s:property value="subject"/></s:a>
		</td>
		<td><s:property value="name"/></td> <!-- 글쓴이 -->
		<td><s:property value="regdate"/></td> <!-- 날짜 -->
		<td><s:property value="readhit"/></td> <!-- 조회수 -->
	</tr>
	<tr bgcolor="#777777">
		<td height="1" colspan="5"></td>
	</tr>
</s:iterator>

<s:if test="list.size()<=0"> <!-- 등록된 게시물이 없으면(리스트에 자바빈(게시물)이 들어있지 않으면 -->
	<tr bgcolor="#FFFFFF" align="center">
		<td colspan="5">등록된 게시물이 없습니다.</td>
	</tr>
	<tr bgcolor="#777777">
		<td height="1" colspan="5"></td>
	</tr>
</s:if>

	<tr align="center">
		<td colspan="5"><s:property value="pagingHtml" escape="false"/></td>
	</tr>
	
	<tr align="right">
		<td colspan="5">
			<input type="button" value="글쓱기" class="inputb" onclick="javascript:location.href='writeForm.action?currentPage=<s:property value="currentPage"/>';">
		</td>
	</tr>
	
	<tr align="center">
		<td colspan="5">
			<form>
				<select name="searchNum">
					<option value="0">작성자</option>
					<option value="1">제목</option>
					<option value="2">내용</option>
				</select>
				<s:textfield name="searchKeyword" theme="simple" value="" cssStyle="width:120px" maxlength="20"/>
				<input type="submit" name="submit" value="검색" class="inputb"/>
			</form>
		</td>
	</tr>
</table>

</body>
</html>