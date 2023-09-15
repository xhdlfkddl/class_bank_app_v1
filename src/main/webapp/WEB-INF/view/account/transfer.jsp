<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>

<div class="col-sm-8">
	<h2>이체</h2>
	<h5>인증을 통해 이체가 가능합니다.</h5>
	<div class="bg-li p-md-5 h-75">
		<form action="">
			<div class="form-grup">
				<label for="wAccountNumber">보내시는 분:</label>
				<input type="text" id="wAccountNumber" class="form-control" placeholder="보내시는 분의 계좌번호" name="wAccountNumber">
			</div>
			<br>
			<div class="form-grup">
				<label for="dAccountNumber">받으시는 분:</label>
				<input type="text" id="dAccountNumber" class="form-control" placeholder="받으시는 분의 계좌번호" name="dAccountNumber">
			</div>
			<br>
			<div class="form-grup">
				<label for="amount">이체할 금액:</label>
				<input type="text" id="amount" class="form-control" placeholder="이체할 금액" name="amount">
			</div>
			<br>
			<div class="form-grup">
				<label for="pwd">계좌 비밀번호:</label>
				<input type="password" id="wAccountPassword" class="form-control" placeholder="계좌 비밀번호" name="wAccountPassword">
			</div>
			<br>
			<button type="submit" class="btn btn-primary">입금하기</button>
		</form>
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>