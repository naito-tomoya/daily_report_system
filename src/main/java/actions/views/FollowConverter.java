package actions.views;

import models.Follow;

/**
 * フォローデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class FollowConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fv FollowViewのインスタンス
     * @return Employeeのインスタンス
     */
    public static Follow toModel(FollowView fv) {

        return new Follow(
                fv.getId(),
                fv.getMyId(),
                fv.getFollowId(),
                fv.getCreatedAt(),
                fv.getUpdatedAt());
    }

}