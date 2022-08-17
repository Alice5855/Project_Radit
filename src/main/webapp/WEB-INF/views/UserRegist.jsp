<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<section>
    <form name="/regist" action="/regist" method="post">
      아이디확인<br>
      <input type="text" name="u_Email" placeholder="이메일" >
     
      이름<br>  <input type="text" name="u_Name" value=""> <br>
      <br>
      비밀번호<br> 
      <input type="text" name="u_pw" value=""><br>
      비밀번호 재확인<br> 
      <br><br>
      <br><br>
      휴대전화<br>  
      <input type="text" name="u_Address" placeholder="010-****-****">
      성별<br>  
      <label for="man">남자</label>
      <input type="radio" name="u_gender" value="m" id="man">
      <label for="woman">여자</label>
      <input type="radio" name="u_gender" value="m" id="woman"> <br><br>
      이메일<br><input type="email" name="u_profile_path" placeholder="email@gmail.com"><br><br>
      <input type="submit" name="" value="제출">
      
      </form>
      
      </section>
	

</body>
</html>