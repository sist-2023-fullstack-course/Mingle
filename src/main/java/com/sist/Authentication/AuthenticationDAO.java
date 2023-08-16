package com.sist.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthenticationDAO {
    private AuthenticationMapper mapper;

    @Autowired
    AuthenticationDAO(AuthenticationMapper mapper){
        this.mapper = mapper;
    }

    // login check
    public List<MemberVO> getMembersByID(String id){
        return mapper.getMembersByID(id);
    }

    public MemberVO getMemberByID(String id){
        return mapper.getMemberByID(id);
    }
}
