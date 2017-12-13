package com.llvision.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@Transactional
public class PushLocalFaceSet {

    private final Logger log = LoggerFactory.getLogger(PushLocalFaceSet.class);

    public final JdbcTemplate jdbcTemplate;

    public PushLocalFaceSet(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int updateTime(String localFaseSetId, String userId){
        String sql = "UPDATE local_face_set_user SET created_date = ?  WHERE local_face_sets_id = ? AND users_id = ?";
        return jdbcTemplate.update(sql,
            new Object[]{Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()), localFaseSetId, userId},
            new int[]{Types.TIMESTAMP, Types.BIGINT, Types.BIGINT});
    }

    public ZonedDateTime getTime(String localFaseSetId, String userId){
        String sql = "select created_date from local_face_set_user where local_face_sets_id = ? AND users_id = ?";
        return (ZonedDateTime) jdbcTemplate.queryForObject(sql,
            new Object[]{localFaseSetId, userId},
            new int[]{Types.INTEGER, Types.INTEGER},
            new RowMapper() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getTimestamp("created_date").toInstant().atZone(ZoneId.systemDefault());
                }
            }
        );
    }

}
