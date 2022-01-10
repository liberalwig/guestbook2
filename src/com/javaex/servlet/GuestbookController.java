package com.javaex.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GuestbookController");

		GuestbookDao guestbookDao = new GuestbookDao();
		List<GuestbookVo> guestList = guestbookDao.getList();

		String action = request.getParameter("action");

		if ("addlist".equals(action)) {
			System.out.println("action=addlist");

			request.setAttribute("gList", guestList);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			rd.forward(request, response);

		} else if ("add".equals(action)) {
			System.out.println("action=add");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo vo = new GuestbookVo(name, password, content);
			int count = guestbookDao.insert(vo);

			System.out.println(count + "건 등록되었습니다");
			response.sendRedirect("/guestbook2/gbc?action=addlist");

		} else if ("deleteform".equals(action)) {
			System.out.println("action=deleteform");

			String stringNo = request.getParameter("no");
			request.setAttribute("stringNo", stringNo);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(action)) {
			System.out.println("action=delete");

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestbookVo vo = new GuestbookVo();
			vo.setNo(no);
			vo.setPassword(password);
			int count = guestbookDao.delete(vo);

			System.out.println(count + "건 삭제되었습니다.");
			response.sendRedirect("/guestbook2/gbc?action=addlist");

		} else {
			System.out.println("파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}