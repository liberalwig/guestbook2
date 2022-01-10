//2022.01.10(월)17:42 상선 감독 하에 주석 달아 파악하기
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GuestbookController");//예외처리 한 후 request와 response받아지는 지 출력해서 확인한다--접속되면 프린트로 찍힘

		GuestbookDao guestbookDao = new GuestbookDao(); //Dao를 메모리에 올린다 (Dao <==> Oracle Database)
		List<GuestbookVo> guestList = guestbookDao.getList(); // GuestbookVo에서 받은 걸 Dao 폼에 넣되 배열화 해서 넣는다--Dao에 getList()(jdbc)=>배열을 리턴해줘요

		String action = request.getParameter("action"); //GuestbookVo의 파라미터를 뽑아 쓴다 -- 주소의 action 파라미터 값을 문자열로 가져온다.
		//ex /gbc?action=addlist&name=유재석
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		if ("addlist".equals(action)) { //유저가 addList페이지를 원했다면(= value 입력할 페이지를 보여 주려면)
			System.out.println("action=addlist");

			request.setAttribute("gList", guestList); //forward방식으로 guestList.jsp한테 일을 넘긴다
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp"); //그 일을 할 jsp파일의 위치를 찍어준다
			rd.forward(request, response); //forward방식 거쳐서 request에 대한 response를 유저에게 내놓는다
			//forward 할때, rd에서 지정한 위치로 일을 넘긴다.
			//포워드 할때 특정한 값을 같이 넘겨주고 싶을때 어트리뷰트에 값을 저장해서 보내 줄 수 있다 (자료형은 상관 없이 다 가능)
			//이 값을 뽑아낼때, 형변환을 해야한다.
			
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////

		} else if ("add".equals(action)) { //유저가 add페이지 원했다면(= 유저가 value를 입력해서 다음으로 넘겨 줘야 한다면)
			System.out.println("action=add");

			String name = request.getParameter("name"); //유저가 입력하는 값을 파라미터로 받는다
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo vo = new GuestbookVo(name, password, content); //유저가 입력한 값(values)을 Vo그릇 형태 유지해서 메모리에 올린다
			int count = guestbookDao.insert(vo); //vo에 입력한 걸 건수(int)로 파악하고
			System.out.println(count + "건 등록되었습니다"); //위의 건수를 출력해준다. '음,유저 둘이 등록한 대로 두 건이 잘 올라갔군'
			response.sendRedirect("/guestbook2/gbc?action=addlist");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		} else if ("deleteform".equals(action)) { //유저가 deleteform페이지 원했다면
			System.out.println("action=deleteform");

			String stringNo = request.getParameter("no"); //no파라미터 하나에 다른 파라미터들이 묶이게 한다?????????
			request.setAttribute("stringNo", stringNo); //forward방식으로 deleteForm.jsp한테 일을 넘긴다

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp"); //그 일을 할 jsp파일의 위치를 찍어준다
			rd.forward(request, response); //forward방식 거쳐서 request에 대한 request를 유저에게 내놓는다

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		} else if ("delete".equals(action)) {
			System.out.println("action=delete");

			int no = Integer.parseInt(request.getParameter("no")); //no파라미터가 받은 스트링을 Int로 형변환해주고 그 형변환해준 걸 메모리에 올린다
			String password = request.getParameter("password"); 

			GuestbookVo vo = new GuestbookVo();
			vo.setNo(no);
			vo.setPassword(password);
			int count = guestbookDao.delete(vo);

			System.out.println(count + "건 삭제되었습니다.");
			
			//리다이렉트
			response.sendRedirect("/guestbook2/gbc?action=addlist");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
			
		} else {
			System.out.println("파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}