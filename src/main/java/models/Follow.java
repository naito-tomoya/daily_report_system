package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FOL)

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Follow {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FOL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 自分の個人id
     */
    @Column(name = JpaConst.FOL_COL_MY_ID, nullable = false)
    private Integer myId;

    /**
     * フォローした人のid
     */
    @Column(name = JpaConst.FOL_COL_FOL_ID , nullable = false)
    private Integer followId;
    
    /**
     *登録日時
     */
    @Column(name = JpaConst.FOL_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.FOL_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    public Employee getEmployee() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
}