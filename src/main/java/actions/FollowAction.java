package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import services.FollowService;
import services.ReportService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class FollowAction extends ActionBase {

    private FollowService service;
    private ReportService reportService;
    private ReportAction reportAction;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();
        reportService = new ReportService();
        reportAction = new ReportAction();
        

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void follow() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        //if (checkToken()) { //追記

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //レポートのidを条件に日報データを取得する
        //ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));
        //EmployeeView ev = rv.getEmployee();
        //Integer employeeId = ev.getId();

        //EntityManager em = DBUtil.createEntityManager();

        //Employee employee = em.createNamedQuery(JpaConst.Q_REP_GET_EMP, Employee.class)
        //.setParameter(JpaConst.JPQL_PARM_REP_ID, toNumber(getRequestParam(AttributeConst.REP_ID)))
        //.getSingleResult();

        //        Integer employeeId = (Integer) em
        //                .createNativeQuery(JpaConst.S_REP_GET_EMP_DEF)
        //                .setParameter(JpaConst.SQL_PARM_REP_ID, toNumber(getRequestParam(AttributeConst.REP_ID)))
        //                .getSingleResult();
        //レポートのidを条件に従業員IDを取得する
        Integer followId = toNumber(getRequestParam(AttributeConst.FOLLOW_ID));

        FollowView fv = new FollowView(
                null,
                loginEmployee.getId(),
                followId,
                null,
                null);

        service.follow(fv);
        reportAction.index();
    }

    //    public void follow() throws ServletException, IOException {
    //
    //        //CSRF対策 tokenのチェック
    //        //if (checkToken()) { //追記
    //
    //            //セッションからログイン中の従業員情報を取得
    //            EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
    //
    //            //idを条件に日報データを取得する
    //            ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));
    //            EmployeeView ev = rv.getEmployee();
    //            Integer employeeId = ev.getId();
    //
    //            FollowView fv = new FollowView(
    //                    null,
    //                    loginEmployee.getId(),
    //                    employeeId,
    //                    null,
    //                    null);
    //
    //            service.follow(fv);

    //        }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得              
        //        int page = getPage();
        //        List<ReportView> reports = service.getAllPerPage(page);
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        
        List<Integer> followIdList = service.getAllFollowId(loginEmployee.getId());

        List<ReportView> sendReportList = new ArrayList<>();
        

        //followIdList.forEach(followId -> sendReportList.addAll(reportService.getReportByFollowId(followId)));
        
        List<Integer> flagList = new ArrayList<>();
        System.out.println("for文に入ります");
        
        for(Integer followId : followIdList) {
            sendReportList.addAll(reportService.getReportByFollowId(followId));
            
            System.out.println("フォローする従業員のIDは" + followId); 
            
            Integer flag = service.getFollowByMyIdAndFollowId(loginEmployee.getId(), followId);
            System.out.println("flagの値は" + flag);
            flagList.add(flag);
        }
        
        
        

        //全日報データの件数を取得
        //        long reportsCount = service.countAll();

        putRequestScope(AttributeConst.REPORTS, sendReportList); //取得した日報データ
        putRequestScope(AttributeConst.REP_FLAG_LIST, flagList);
        //putRequestScope(AttributeConst.REP_COUNT, reportsCount); //全ての日報データの件数
        //putRequestScope(AttributeConst.PAGE, page); //ページ数
        //putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        if (rv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_FOL_SHOW);
        }
    }
    


}

//}