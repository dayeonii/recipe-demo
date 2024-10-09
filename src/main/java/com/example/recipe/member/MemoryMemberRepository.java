package com.example.recipe.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * 회원을 저장할 로컬 저장소 -> sql 문법으로 데이터베이스에 저장
 * */

@Repository
public class MemoryMemberRepository implements MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Member 객체로부터 정보를 불러와서 sql 쿼리에 변수 적용 후 쿼리 요청으로 사용자 저장
    @Override
    public void save(Member member) {
        String sql = "INSERT INTO MEMBERS (id, password, name, email, phone, grade) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, member.getId(), member.getPassword(), member.getName(), member.getEmail(), member.getPhone(), member.getGrade().name());
    }

    @Override
    public Member findById(String memberID) {

        String sql = "SELECT * FROM MEMBERS WHERE id = ?";  //사용자 존재 여부 쿼리

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{memberID}, (rs, rowNum) -> {
                return new Member(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        Grade.valueOf(rs.getString("grade"))
                );
            });
        } catch (EmptyResultDataAccessException e) {    //사용자가 존재하지 않을 경우
            return null;
        }


    }
}
