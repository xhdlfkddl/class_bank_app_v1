<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-8">
	<h2>계좌 상세 보기</h2>
	<h5>${principal.username }님 환영합니다.</h5>
	<div class="bg-li p-md-5 h-75">
		<div class="user--box">
			${principal.username }님의 계좌 &nbsp;
			계좌번호: ${account.number} &nbsp;
			잔액: ${account.balance }원 &nbsp;
		</div>
		<br>
		<div>
			<a href="/account/detail/${account.id }">전체</a>&nbsp;
			<a href="/account/detail/${account.id }?type=deposit">입금</a>&nbsp;
			<a href="/account/detail/${account.id }?type=withdraw">출금</a>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>날짜</th>
					<th>보내신 분</th>
					<th>받으신 분</th>
					<th>입/출금금액</th>
					<th>잔액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="history" items="${historyList}">
					<tr>
						<td>${history.formatCreatedAt() }</td>
						<td>${history.sender }</td>
						<td>${history.receiver }</td>
						<td>${history.amount }</td>
						<td>${history.formatBalance() }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>