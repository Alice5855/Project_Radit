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
      ���̵�Ȯ��<br>
      <input type="text" name="u_Email" placeholder="�̸���" >
     
      �̸�<br>  <input type="text" name="u_Name" value=""> <br>
      <br>
      ��й�ȣ<br> 
      <input type="text" name="u_pw" value=""><br>
      ��й�ȣ ��Ȯ��<br> 
      <br><br>
      <br><br>
      �޴���ȭ<br>  
      <input type="text" name="u_Address" placeholder="010-****-****">
      ����<br>  
      <label for="man">����</label>
      <input type="radio" name="u_gender" value="m" id="man">
      <label for="woman">����</label>
      <input type="radio" name="u_gender" value="m" id="woman"> <br><br>
      �̸���<br><input type="email" name="u_profile_path" placeholder="email@gmail.com"><br><br>
      <input type="submit" name="" value="����">
      
      </form>
      
      </section>
	

</body>
</html>