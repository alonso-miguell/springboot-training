package com.deloitte.spring.demo.dtos;

public class UserMapStructDto {

    private Long id;
    private String nickname;
    private String emailaddress;
    private String rolename;

    public UserMapStructDto() {

    }

    public UserMapStructDto(Long userid, String username, String emailaddress, String rolename) {
        super();
        this.id = userid;
        this.nickname = username;
        this.emailaddress = emailaddress;
        this.rolename = rolename;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmailaddress() {
        return emailaddress;
    }
    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }


    public String getRolename() {
        return rolename;
    }


    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}