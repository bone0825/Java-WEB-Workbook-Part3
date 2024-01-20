package org.zerock.w2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.java.Log;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get...");
        req.getRequestDispatcher("/WEB-INF/todo/login.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login post....");
        String mid = req.getParameter("mid"); //입력한 ID
        String mpw = req.getParameter("mpw"); //입력한 PW
        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");

        if(rememberMe){
            String uuid = UUID.randomUUID().toString(); //임의 번호 생성
        }

        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid,mpw);
            if(rememberMe){
                String uuid = UUID.randomUUID().toString();

                MemberService.INSTANCE.updateUuid(mid,uuid);
                memberDTO.setUuid(uuid);

                Cookie rememberCookie = new Cookie("remeber-me",uuid);
                rememberCookie.setMaxAge(60*60*24*7);
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }
            HttpSession session = req.getSession();
            session.setAttribute("loginInfo",memberDTO);
            resp.sendRedirect("/todo/list");
        } catch (Exception e){
            log.info("login Error..... Error....");
            resp.sendRedirect("/login?result=error");
        }
    }
}
