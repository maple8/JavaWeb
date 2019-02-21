package com.maple.transfer.web;

import com.maple.transfer.service.TransferService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Maple
 * @date 2019.02.01 11:05
 */
@WebServlet(name = "TransferServlet", urlPatterns = "/TransferServlet")
public class TransferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //获取表单提交的数据（转账的参数）
        String outAccount = request.getParameter("out");
        String inAccount = request.getParameter("in");
        double money = Double.parseDouble(request.getParameter("money"));

        //调用业务层的逻辑处理方法
        TransferService transferService = new TransferService();
        boolean isTransferSuccess = transferService.transfer(outAccount, inAccount, money);

        //根据逻辑业务方法的返回值显示对应的页面

        if (isTransferSuccess) {
            response.getWriter().write("转账成功！！！");
        } else {
            response.getWriter().write("转账失败！！！");
        }
    }
}
