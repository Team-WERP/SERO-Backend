package com.werp.sero.client.repository;

import java.util.List;

import org.springframework.data.repository.query.Param; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.werp.sero.client.entity.ClientItem;

@Repository
public interface ClientItemRepository extends JpaRepository<ClientItem, Integer>{


    @Query("SELECT ci FROM ClientItem ci " +
            "JOIN FETCH ci.material m " +
            "WHERE ci.client.id = :clientId")
    List<ClientItem> findByClientId(@Param("clientId") int clientId);

    // Material의 material, name으로 검색
    @Query("SELECT ci FROM ClientItem ci " +
           "JOIN FETCH ci.material m " +
           "WHERE ci.client.id = :clientId " +
           "AND (m.materialCode LIKE %:keyword% OR m.name LIKE %:keyword%) ") 
    List<ClientItem> findByClientIdAndKeyword(@Param("clientId") int clientId,
                                              @Param("keyword") String keyword);

    // status 필터
    @Query("SELECT ci FROM ClientItem ci " +
           "JOIN FETCH ci.material m " +
           "WHERE ci.client.id = :clientId " +
           "AND ci.status = :status")
    List<ClientItem> findByClientIdAndStatus(@Param("clientId") int clientId, 
                                              @Param("status") String status);    

    // 둘 다 필터
    @Query("SELECT ci FROM ClientItem ci " +
           "JOIN FETCH ci.material m " +
           "WHERE ci.client.id = :clientId " +
           "AND ci.status = :status " +
           "AND (m.materialCode LIKE %:keyword% OR m.name LIKE %:keyword%)")
    List<ClientItem> findByClientIdAndStatusAndKeyword(@Param("clientId") int clientId, 
                                                        @Param("status") String status,
                                                        @Param("keyword") String keyword);                                        
}
