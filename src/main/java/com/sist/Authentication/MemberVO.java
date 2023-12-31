package com.sist.Authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MemberVO {
    private String user_id, password, user_name, nickname, gender, phone, email, birthday, address, detail_address, dbday;
    private Date regdate;
}
