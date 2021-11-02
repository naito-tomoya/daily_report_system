package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.FollowConverter;
import actions.views.FollowView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Follow;
import models.Report;

/**
 * 従業員テーブルの操作に関わる処理を行うクラス
 */
public class FollowService extends ServiceBase {

    public void follow(FollowView fv) {

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        fv.setCreatedAt(now);
        fv.setUpdatedAt(now);

        //データを登録する
        create(fv);

    }

    public void delete(Integer myId, Integer followId) {

        // 削除処理
        em.getTransaction().begin();
        
        em.createNativeQuery(JpaConst.S_REP_FOL_DEL_DEF)
                .setParameter(JpaConst.FOL_COL_MY_ID, myId)
                .setParameter(JpaConst.FOL_COL_FOL_ID, followId)
                .executeUpdate();

        em.getTransaction().commit();

    }

    //    public void delete(EmployeeView follow_v, EmployeeView follower_v) {
    //        
    //        // モデルインスタンスへ変換
    //        Employee follow = EmployeeConverter.toModel(follow_v);
    //        Employee follower = EmployeeConverter.toModel(follower_v);
    //        // 削除するFollowインスタンスを取得
    //        Follow f = (Follow) em.createNativeQuery("getFollowByFollowANDFollower", Follow.class).setParameter("follow", follow).setParameter("follower", follower).getSingleResult();
    //        // 削除処理
    //        em.getTransaction().begin();
    //        em.remove(f);
    //        em.getTransaction().commit();
    //
    //    }

    private void create(FollowView fv) {
        System.out.println("今から登録を行います");

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();

    }

    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }

    /**
     * followsテーブルからfollowIDを全件取得する
     * @param reportId レポートID
     * @return　従業員ID
     */
    public List<Integer> getAllFollowId(Integer my_id) {
        @SuppressWarnings("unchecked")
        List<Integer> followIdList = em
                .createNativeQuery(JpaConst.S_FOL_GET_FOL_ID)
                .setParameter(JpaConst.FOL_COL_MY_ID, my_id)
                .getResultList();
        return followIdList;
    }

    public Integer getFollowByMyIdAndFollowId(Integer myId, Integer employeeId) {
        @SuppressWarnings("unchecked")
        List<Follow> fs = (List<Follow>) em.createNativeQuery(JpaConst.S_FOL_GET_MY_ID_AND_FOL_ID, Follow.class)
                .setParameter(JpaConst.FOL_COL_MY_ID, myId)
                .setParameter(JpaConst.FOL_COL_FOL_ID, employeeId)
                .getResultList();
        if (fs.size() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * employeeテーブルからemployeeIDを全件取得する
     * @param reportId レポートID
     * @return　従業員ID
     */
    public List<Integer> getAllEmployeeId() {
        @SuppressWarnings("unchecked")
        List<Integer> followIdList = em
                .createNativeQuery(JpaConst.S_GET_EMP_ID)
                .getResultList();
        return followIdList;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }
}