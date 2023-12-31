package com.sist.mento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class MentoServiceImpl implements MentoService{
    private MentoDAO dao;
    @Autowired
    public MentoServiceImpl(MentoDAO dao) {
        this.dao = dao;
    }

    public List<MentoVO> MentoListData(int page, String column, String fd, String user_id){
        int rowSize=12;
        int start=(rowSize*page)-(rowSize-1);
        int end=rowSize*page;

        Map<String, Object> map=new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        map.put("column", column);
        map.put("fd", fd);

        List<MentoVO> list = dao.MentoListData(map);
        for(MentoVO vo:list) {
            double star = Math.round(((double)vo.getSum_star()/vo.getCnt_star())*10)/10.0;
            vo.setAvg_star(star);
        }

        // �ȷο� ���� �߰�
        for(MentoVO vo : list)
            vo.setFollowed(false);

        if(user_id != null) {
            List<Integer> noList = new ArrayList<>();
            for(MentoVO vo : list){
                noList.add(vo.getMento_no());
            }
            HashMap<String, Object> params = new HashMap<>();
            params.put("user_id", user_id);
            params.put("list", noList);

            List<Integer> followCheckList = dao.getfollowCheckListByUserId(params);

            for(MentoVO vo : list){
                for(int no : followCheckList){
                    if(vo.getMento_no() == no){
                        vo.setFollowed(true);
                        break;
                    }
                }
            }
        }

        return list;
    }

    public int mentoTotalPage(Map map) {
        return dao.mentoTotalPage(map);
    }

    public MentoVO mentoDetailData(int mento_no) {
        return dao.mentoDetailData(mento_no);
    }

    public List<MentoVO> mentoEdit(){
        return dao.mentoEdit();
    }
    public void mentoEditUpdate(Map map){
        dao.mentoEditUpdate(map);
    }

    public void deleteMentoCounselByMentoNo(int mento_no){
        dao.deleteMentoCounselByMentoNo(mento_no);
    }
    public void deleteMentoReviewByMentoNo(int mento_no){
        dao.deleteMentoReviewByMentoNo(mento_no);
    }
    public void deleteMentoFollowByMentoNo(int mento_no){
        dao.deleteMentoFollowByMentoNo(mento_no);
    }
    public int deleteMentoByMentoNo(int mento_no){
        return dao.deleteMentoByMentoNo(mento_no);
    }

    public void mentoRegist(MentoVO vo) {
        dao.mentoRegist(vo);
    }

    public int regist_usercheck(String id) {
        return dao.regist_usercheck(id);
    }
    
    // ���� �ȷο� ����
    @Override
    @Transactional
    public int followMentor(int mento_no, String user_id) {
        dao.insertMentoFollow(user_id, mento_no);
        dao.modifyMentoFollowerCount(mento_no, 1);
        return dao.getFollowerCountByMentoNo(mento_no);
    }

    @Override
    @Transactional
    public int unFollowMentor(int mento_no, String user_id) {
        dao.deleteMentoFollow(user_id, mento_no);
        dao.modifyMentoFollowerCount(mento_no, -1);
        return dao.getFollowerCountByMentoNo(mento_no);
    }

	@Override
	public void mentoContact(ContactVO vo) {
		dao.mentoContact(vo);
	}

	//멘토 정보 수정 및 삭제
	@Override
	public MentoVO getMentoByID(String id) {
		MentoVO vo = dao.getMentoByID(id);
		return vo;
	}

	@Override
	public void mentoUpdate(MentoVO vo) {
		dao.mentoUpdate(vo);
	}

	@Override
	@Transactional
	public void mentoDelete(int mento_no) {
		dao.deleteMentoFollowByMentoNo(mento_no);
		dao.deleteMentoCounselByMentoNo(mento_no);
		dao.deleteMentoByMentoNo(mento_no);
	}

    // 팔로우 많은 3명
    @Override
    public List<MentoVO> getMostFollowedMento(){
        return dao.getMostFollowedMento();
    }
    
  //멘토 정보 수정 및 삭제
  	@Override
  	public boolean validateMento(String id) {
  		MentoVO vo = dao.getMentoByID(id);
  		return vo != null;
  	}
  	
  	@Override
	public List<CounselVO> MentoringListData(int page, String column, String user_id) {
		
		int rowSize=9;
        int start=(rowSize*page)-(rowSize-1);
        int end=rowSize*page;

        Map<String, Object> map=new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        map.put("column", column);
        map.put("user_id", user_id);

        List<CounselVO> list = dao.MentoringListData(map);
        
		/*
		 * for(CounselVO vo:list) { double star =
		 * Math.round(((double)vo.getSum_star()/vo.getCnt_star())*10)/10.0;
		 * vo.setAvg_star(star); }
		 */
        
		return list;
	}
	
	public int mentoringTotalPage(Map map) {
        return dao.mentoringTotalPage(map);
    }
	
	@Override
	public List<CounselVO> mentoMentoringListData(int page, String column, int mento_no) {
		
		int rowSize=9;
        int start=(rowSize*page)-(rowSize-1);
        int end=rowSize*page;

        Map<String, Object> map=new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        map.put("column", column);
        map.put("mento_no", mento_no);

        List<CounselVO> list = dao.mentoMentoringListData(map);
        
		/*
		 * for(CounselVO vo:list) { double star =
		 * Math.round(((double)vo.getSum_star()/vo.getCnt_star())*10)/10.0;
		 * vo.setAvg_star(star); }
		 */
        
		return list;
	}
	
	public int mentomentoringTotalPage(Map map) {
        return dao.mentomentoringTotalPage(map);
    }
	
	@Override
	public void counselStateChange(int counsel_no, int val) {
		dao.counselStateChange(counsel_no, val);
		
	}
	
	//멘토링 리뷰
	@Override
	@Transactional
	public void mentoReview(ReviewVO vo) {
		dao.mentoReview(vo);
        dao.updateMentoStar(vo.getMento_no(), vo.getStar());
		dao.rep_stateChange(vo);
	}

	@Override
	public List<ReviewVO> getReviewByMentoNo(int mento_no) {
		return dao.getReviewByMentoNo(mento_no);
	}
	
	
	
}
