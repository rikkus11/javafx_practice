package models.service;

import java.util.List;
import java.util.Optional;

import play.db.ebean.Model;

public interface ModelService<T extends Model> {

    /**
     * 指定したIDのモデルを取得します。
     *
     * @param id ID
     * @return nullの可能性があるモデルクラス
     */
    public Optional<T> findById(Long id);

    /**
     * 指定したモデルをDBに挿入します。
     *
     * @param model 挿入するモデル
     * @return 挿入結果
     */
    public boolean store(T model);

    /**
     * DBから全件取得します。
     *
     * @return 登録されているモデル全件のリスト
     */
    public List<T> getAll();
}
