package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.GoodsIssueItem;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

public interface GoodsIssueItemRepository extends JpaRepository<GoodsIssueItem, Integer> {
    List<GoodsIssueItem> findByGoodsIssueId(int goodsIssueId);
=======
public interface GoodsIssueItemRepository extends JpaRepository<GoodsIssueItem, Integer> {
>>>>>>> origin/develop
}
