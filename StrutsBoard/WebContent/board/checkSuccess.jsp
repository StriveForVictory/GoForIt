<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
	<title>비밀번호 확인</title>
	<link rel="stylesheet" href="/StrutsBoard/board/common/css/css.css" type="text/css">
	<script type="text/javascript">
		function locationURL() {
		
		if ( window.name == 'modify' ) {
			window.opener.parent.location.href='modifyForm.action?no=<s:property value="no" />&currentPage=<s:property value="currentPage" />';
		}
		else if ( window.name == 'delete' ) {
			alert('삭제되었습니다.');
			window.opener.parent.location.href='deleteAction.action?no=<s:property value="no" />&currentPage=<s:property value="currentPage" />';
		}else if( window.name =='cdelete'){
			alert('코멘트가 삭제 되었습니다.');
			window.opener.parent.location.href='deleteAction2.action?no=<s:property value="no"/>&originno=<s:property value="originno"/>&currentPage=<s:property value="currentPage"/>';
		}
		window.close();
		}
	</script>
	
</head>
<body>
<script>locationURL()</script>
</body>
</html>