<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>

<div class="col-sm-8">
	<h2>출금</h2>
	<h5>인증을 통해 출금이 가능합니다.</h5>
	<div class="bg-li p-md-5 h-75">
		<form action="">
			<div class="form-grup">
				<label for="amount">출금 금액:</label>
				<input type="text" id="amount" class="form-control" placeholder="출금 금액" name="amount">
			</div>
			<br>
			<div class="form-grup">
				<label for="wAccountNumber">출금 계좌번호:</label>
				<input type="text" class="form-control" placeholder="출금 계좌번호" name="wAccountNumber">
			</div>
			<br>
			<div class="form-grup">
				<label for="wAccountPassword">출금 계좌 비밀번호:</label>
				<input type="password" class="form-control" placeholder="출금 계좌 비밀번호" name="wAccountPassword">
			</div>
			<br>
			<button type="submit" class="btn btn-primary">출금하기</button>
		</form>
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>