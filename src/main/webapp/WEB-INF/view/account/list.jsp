<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-8">
	<h2>나의 계좌 목록</h2>
	<h5>${principal.username }님 환영합니다.</h5>
	<div class="bg-li p-md-5 h-75">
		<c:choose>
			<c:when test="${accountList != null }">
				<table class="table">
					<thead>
						<tr>
							<td>계좌번호</td>
							<td>잔액</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accountList}">
							<tr>
								<td><a href="/account/detail/${account.id}">${account.number}</a></td>
								<td><a href="">${account.balance}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>생성된 계좌가 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>
</div>


</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>