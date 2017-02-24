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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		boolean result = false;

		try {
			String mail = String.valueOf(request.getParameter("mail") );
			String passwd = String.valueOf(request.getParameter("passwd") );
			String safetyPasswd = PasswordUtil.getSafetyPassword(passwd, "");


			UserDAO dao = new UserDAO();
			User user = new User(mail, safetyPasswd, true);
			result = dao.insert(user);
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		String url;
		if(result) {
			url = "/member_register_complete.html";

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

		} else {
			//エラーページ未実装
			url = "/index.html";
			System.out.println("デバッグ用：ユーザ登録に失敗しました。");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}

