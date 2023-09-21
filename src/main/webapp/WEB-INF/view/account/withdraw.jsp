<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp"%>


<div class="col-sm-8">
    <h2>출금 페이지(인증)</h2>
    <h5>어서오세요 환영합니다</h5>
    <div class="bg-light p-md-5 h-75">
        
        <div class="form-group">
            <form action="/account/withdraw" method="post">
                <div class="form-group">
                    <label for="amount">출금 금액:</label>
                    <input type="text" id="amount" class="form-control" placeholder="출금금액을 입력하시오" name="amount">
                </div>
                <div class="form-group">
                    <label for="wAccountNumber">출금계좌번호:</label>
                    <input type="text" id="wAccountNumber" class="form-control" placeholder="출금금액을 입력하시오" name="wAccountNumber">
                </div>
                <div class="form-group">
                    <label for="wAccountPassword">출금계좌비밀번호:</label>
                    <input type="text" id="wAccountPassword" class="form-control" placeholder="출금금액을 입력하시오" name="wAccountPassword">
                </div>
                <button type="submit" class="btn btn-primary">출금</button>

            </form>

        </div>
               

    </div>
    
  </div>
</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp"%>