package jp.ac.hal.hew;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserAdd
 */
@WebServlet("/UserAdd")
public class UserAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
        //リクエストの文字コード設定
        request.setCharacterEncoding("UTF-8");
        //エラーフラグ設定
        boolean boolErrFlg = false;
        //エラーコード
        String strErr="";

        //登録処理
        if(request.getParameter("REGIST")!=null){
            //確認ボタン押下処理
            //入力されたメールアドレスとパスワードの取得
            String strAdd = request.getParameter("address");
            String strPasswd = request.getParameter("passwd");

            //パスワードのハッシュ化(ソルトは空文字)
            String hashPass = PasswordUtil.getSafetyPassword(strPasswd, "");

            try{
                HewDb db = new HewDb();
                db.userAdd(strAdd, hashPass);
            }catch(ClassNotFoundException cnfe) {
                // 未実装
            }catch(SQLException seqle) {
                // 未実装
            }
        }

        //転送処理
        String strDisp;
        if(boolErrFlg){
            //処理の失敗時（Error.jspに転送）
            strDisp = "Error.jsp";
            //エラーコードをリクエストに設定
            request.setAttribute("Err", strErr);
        }else{
            //処理の成功時（Result.jspに転送）
            strDisp = "Result.jsp";
        }

        //転送
        RequestDispatcher Disp = request.getRequestDispatcher(strDisp);
        Disp.forward(request, response);

    }
}

