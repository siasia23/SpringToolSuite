<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	
	function getDeptName(pDeptno) {
		
		console.log(pDeptno);
		
		$.ajax({
			
			url : "<%=request.getContextPath()%>/getDeptName",
				
			// pamameter 저장 형식 {key : value}
			data : {deptno : pDeptno},
				
			// return type : text (String)
			dataType : 'text',		
				
			// 성공하면 콜백함수 실행, 파라미터는 String
			success : function(deptName) {
					
				$('#deptName').val(deptName);
				$('#msg').html(deptName);
					
			}
				
		})
		
	};
	
	function getDept(pDeptno) {
		
		$.ajax({
			
			url : "sample/sendVO2",
				
			// parameter 저장 형식 {key : value}
			data : {deptno : pDeptno},
				
			// return type : json (Instance)
			dataType : 'json',
				
			// 성공하면 콜백함수 실행, 파라미터는 Instance
			success : function(sampleVo) {
					
				resultStr = sampleVo.firstName + " " + sampleVo.lastName + " " +sampleVo.mno;
				alert("Ajax getDept resultStr : " + resultStr);
					
				// id=RestDept 의 value = resultStr
				$('#RestDept').val(resultStr);
					
			}
				
		})
		
	};
	
	function empWriteBtn() {
		
		var empno = $('#empno').val();
		var sendData = $('#empTrans').serializeArray();
		alert('sendData serializeArray : ' + sendData);
		
		sendData3 = JsonParse(sendData);
		
		alert('sendData3 : ' + sendData3);
		console.log('sendData3 : ' + sendData3);
		
		$.ajax({
			
			url : 'empSerializeWrite',
			type : 'POST',
			contentType : 'application/json;charset=UTF-8',
			
			// json 객체를 String 형태로 변환하지만 String type은 아님. 
			// 여전히 객체 형태임.
			// 그래서 java 코드에서 전달 받으려면 @RequestBody 필요함.
			data : JSON.stringify(sendData3),
			
			dataType : 'json',
			
			success : function(response) {
				
				if(response.writeResult > 0) alert("성공!");
				
			}
			
		})
		
	};
	
	function JsonParse(sendData2) {
		
		// 배열임을 선언
		obj = {};
		
		if (sendData2) {
			
					// each = a set of 'key'+'value'
			jQuery.each(sendData2, function() {
				
				obj[this.name] = this.value;
				
				alert('this.name : ' + this.name);
				alert('this.value : ' + this.value);
				
			})
			
		}
		
		return obj;
		
	};

</script>
</head>
<body>

	<h2>회원 정보</h2>
	
	<table >
		
		<tr>
			<th>사번</th>
			<th>이름</th>
			<th>업무</th>
			<th>부서</th>
			<th>근무지</th>
		</tr>
		
		<c:forEach var="emp" items="${listEmp}">
			<tr>
				<td>${emp.empno }</td>
				<td>${emp.ename }</td>
				<td>${emp.job }</td>
				<td>${emp.deptno} 
					<input type="button" id="btn_idCheck" value="부서명" onmouseover="getDeptName(${emp.deptno })">
				</td>
				<td>${empDept.loc }</td>
			</tr>
		</c:forEach>
		
	</table>



	deptName:  <input type="text" id="deptName"  readonly="readonly"><p>
    Message :  <span id="msg"/><p>
    
    RestController sendVO2: <input type="text" id="RestDept"    readonly="readonly"><p>
	RestController sendVO2: sendVO2<input type="button" id="btn_Dept"  value="부서명"  onclick="getDept(10)"><p>
	                                      
	                                      
	                                      
	<h2>Serialize Test</h2>
    <form  name="empTrans"   id="empTrans">
    		<input type="hidden"  id="empno"  name="empno"    value="1234">
    		<input type="hidden"  id="ename"  name="ename"    value="시리얼">
    		<input type="hidden"  id="sal"    name="sal"      value="1000">
        <input type="button"  value="Ajax Serialize 확인" onclick="empWriteBtn()">
    </form>
	                                      
    
	 
</body>
</html>