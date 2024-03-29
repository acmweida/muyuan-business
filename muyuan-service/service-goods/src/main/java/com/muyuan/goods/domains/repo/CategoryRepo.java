package com.muyuan.goods.domains.repo;

import com.muyuan.common.bean.Page;
import com.muyuan.goods.domains.model.entity.Category;
import com.muyuan.goods.face.dto.CategoryQueryCommand;

import java.util.List;

/**
 * 商品分类对象 t_category
 *
 * @author ${author}
 * @date 2022-12-16T11:54:09.147+08:00
 */

public interface CategoryRepo {

    Page<Category> select(CategoryQueryCommand command);

    List<Category> selectByBrandId(Long... brandIds);

    Category selectCategory(Long id);

    Category selectCategoryByCode(Long code);

    Category selectCategory(Category.Identify identify);

    boolean addCategory(Category category);

    int count(CategoryQueryCommand command);

    /**
     * 更新信息
     * @param category
     * @return old value
     */
    Category updateCategory(Category category);

    /**
     * 更新信息
     * @param category
     * @return old value
     */
    Category updateCategoryAncestors(Category category);

    /**
     * 删除
     * @param ids
     * @return old value
     */
    List<Category> deleteBy(Long... ids);

}
