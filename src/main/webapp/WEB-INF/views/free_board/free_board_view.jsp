<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  
	작성자	: 이상원
	작성일	: 2016-11-21
	목 적  	: 자유게시판 상세보기 view
-->
<div class="normalheader transition animated fadeIn">
	<div class="hpanel">
		<div class="panel-body">
			<a class="small-header-action" href="">
				<div class="clip-header">
					<i class="fa fa-arrow-up"></i>
				</div>
			</a>
			<h2 class="font-light m-b-xs">자유 게시판</h2>
		</div>
	</div>
</div>



<div class="content animate-panel">
	<div class="row">
		<div class="col-lg-12">

			<div class="hpanel forum-box">
				<div class="panel-heading">
					<span class="pull-right"> <i class="fa fa-clock-o"> </i> :
						${list.regdate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-eye">
					</i> ${list.hit}
					</span> 
						<span class="text-success"> ${list.title} </span>
				</div>


				<div class="panel-body">
					<div class="media">
						<div class="media-image pull-left">
							<img src="${pageContext.request.contextPath}/images/default.jpg" alt="profile-picture">
							<div class="author-info">
								<strong>${list.userid}</strong><br> ${list.regdate}
							</div>
						</div>
						<div class="media-body" style="text-align: left">
							<c:if test="${not empty list.filename}">
								<span class="pull-right">첨부파일 : <a href=freeBoard_fileDown.do?name=${list.filename}">${list.filename}</a>
								</span>
								<br>
							</c:if>
							${list.content}

							<div class="forum-comments">
								<c:forEach var="re_list" items="${re_list}">
									<div class="media">
										<a class="pull-left"> <img src="${pageContext.request.contextPath}/images/default.jpg" alt="profile-picture"></a>

										<div class="media-body">
											<span class="font-bold">${re_list.userid}</span> <small class="text-muted">${re_list.regdate }</small>
											<div class="social-content">${re_list.content}</div>
										</div>
										<div style="text-align: right;">
										<c:if test="${re_list.credential_id == loginData.credential_id }">
											<input type="submit" class="btn  btn-default" value="삭제하기">
										</c:if>
										</div>
									</div>
								</c:forEach>

								<form action="free_reply_insert.do" method="post">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Your comment" name="content">
										<input type="hidden" name="currentpage" value="${currentpage}">
										<input type="hidden" name="pagesize" value="${pagesize}">
										<input type="hidden" name="free_no" value="${list.free_no}"> 
										<span class="input-group-btn"> 
										<input type="submit" class="btn  btn-default" value="댓글 달기 ">
										</span>
									</div>
								</form>

							</div>
							<br>
							<div class="pull-right">
								<a href="freeboard.do?currentpage=${currentpage}&pagesize=${pagesize}" class="btn btn-sm btn-success" style="padding-right: 15px; padding-left: 15px; font-weight: 600; font-size: 13px">뒤로 가기</a> 
								<a href="answerfree.do?free_no=${list.free_no}&currentpage=${currentpage}&pagesize=${pagesize}" class="btn btn-sm btn-success" style="padding-right: 15px; padding-left: 15px; font-weight: 600; font-size: 13px">답글쓰기</a>
								
								<c:if test="${list.credential_id==loginData.credential_id}">
									<a href="free_board_update.do?free_no=${list.free_no}" class="btn btn-sm btn-success" style="padding-right: 15px; padding-left: 15px; font-weight: 600; font-size: 13px">수정하기</a>
									<input type="text" hidden="hidden" id="listno" value="${list.free_no}">
									<a class="btn btn-sm btn-default" style="padding-right: 15px; padding-left: 15px; font-weight: 600; font-size: 13px" onclick="delete_event()" href=javascript:void(0)>삭제하기</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

</div>
