<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>

<div class="col-sm-8">
	<h2>계좌 생성</h2>
	<h5>새로운 계좌를 생성해보세요!</h5>
	<div class="bg-li p-md-5 h-75">
		<form action="" method="post">
			<div class="form-grup">
				<label for="number">계좌번호:</label>
				<input type="text" class="form-control" placeholder="생성 계좌번호 입력" name="number">
			</div>
			<br>
			<div class="form-grup">
				<label for="password">계좌 비밀번호:</label>
				<input type="password" class="form-control" placeholder="계좌 비밀번호 입력" name="password">
			</div>
			<br>
			<div class="form-grup">
				<label for="balance">입금 금액:</label>
				<input type="text" class="form-control" placeholder="입금하고자 하는 금액" name="balance">
			</div>
			<br>
			<button type="submit" class="btn btn-primary">계좌 생성</button>
		</form>
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>