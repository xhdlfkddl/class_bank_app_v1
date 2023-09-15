<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>

<div class="col-sm-8">
	<h2>입금</h2>
	<h5>인증을 통해 입금이 가능합니다.</h5>
	<div class="bg-li p-md-5 h-75">
		<form action="">
			<div class="form-grup">
				<label for="amount">입금 금액:</label>
				<input type="text" id="amount" class="form-control" placeholder="입금 금액" name="amount">
			</div>
			<br>
			<div class="form-grup">
				<label for="dAccountNumber">입금 계좌번호:</label>
				<input type="text" class="form-control" placeholder="입금 계좌번호" name="dAccountNumber">
			</div>
			<br>
			<button type="submit" class="btn btn-primary">입금하기</button>
		</form>
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>