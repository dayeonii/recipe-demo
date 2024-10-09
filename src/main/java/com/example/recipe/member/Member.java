package com.example.recipe.member;

/*
* 회원 관리
* - 필요한 속성 : 아이디, 비밀번호, 이름, 이메일, 전화번호, 등급 */

public class Member {

    // 속성 정의
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Grade grade;

    // 생성자
    public Member(String id, String password, String name, String email, String phone, Grade grade) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.grade = grade;
    }

    //Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
