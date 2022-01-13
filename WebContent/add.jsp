<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.GuestbookVo" %>
<%@ page import="com.javaex.dao.GuestbookDao" %>
<%@ page import="java.util.List" %>
<%
	//주소에서 파라미터값을 가져온다
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String content = request.getParameter("content");
	
	//파라미터 값을 vo로 만든다
	GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
	
	//GuestbookDao를 메모리에 올린다
	GuestbookDao guestbookDao = new GuestbookDao();
	
	//insert
	guestbookDao.insert(guestbookVo);
	
	//전체 리스트를 가져온다
	List<GuestbookVo> guestbookList = guestbookDao.getList();
	
	//리다이렉트
	response.sendRedirect("./addList.jsp");

%>