package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.domain.projection.Top10MoreConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    @Query(value = "SELECT T.firstName AS fistName, T.lastName AS lastName T.totalKvh AS totalKvh"+
            "FROM( SELECT C.firstName, C.lastName, MAX(M.measurementKwh) - MIN(M.measurementKwh) AS totalConsume" +
            "FROM MEASUREMENT M" +
            "INNER JOIN METER ME " +
            "ON M.id_meter = ME.id_meter" +
            "INNER JOIN RESIDENCE R" +
            "ON R.residenceId = ME.residenceId" +
            "INNER JOIN CLIENT C" +
            "ON C.userId = R.userId" +
            "WHEN M.dateMeasurement BETWEEN ?1 AND ?2" +
            "GROUP BY C.firstName, C.lastName) AS T " +
            "GROUP BY T.firstName, T.lastName, T.totalKvh " +
            "ORDEN BY SUM(totalKvh) ASC" +
            "LIMIT 10", nativeQuery = true)
    List<Top10MoreConsumption> getTop10(LocalDateTime from, LocalDateTime to);

    Client findByUsername(String username);
}
