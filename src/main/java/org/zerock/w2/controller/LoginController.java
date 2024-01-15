package org.zerock.w2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

import java.io.IOException;

@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get...");
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login post....");
        String mid = req.getParameter("mid"); //입력한 ID
        String mpw = req.getParameter("mpw"); //입력한 PW

        String str = mid + mpw;

        HttpSession session = req.getSession();
        session.setAttribute("loginInfo", str); //Session 쿠키에 loginInfo / ID+PW형식으로 저장

        resp.sendRedirect("/todo/list");
    }
}
