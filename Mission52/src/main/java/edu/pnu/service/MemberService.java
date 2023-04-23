package edu.pnu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.pnu.dao.log.LogDao;
import edu.pnu.dao.member.MemberInterface;
import edu.pnu.domain.MemberVO;

@Service
public class MemberService {

	private MemberInterface memberDao;
	private LogDao logDao;
	private LogDao logDaoF;

	@Autowired
	public MemberService(MemberInterface memberDao, 
			@Qualifier("LogDaoH2Impl") LogDao logDao,
			@Qualifier("LogDaoFileImpl") LogDao logDaoF) {
		this.memberDao = memberDao;
		this.logDao = logDao;
	}

	private void addLog(Map<String, Object> map, String method, boolean success) {
		logDao.addLog(method, (String)map.get("sql"), success);
		logDaoF.addLog(method, (String)map.get("sql"), success);
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberVO> getMembers() {
		Map<String, Object> map = memberDao.getMembers();
		List<MemberVO> list = (List<MemberVO>) map.get("data");
		if (list != null)
			addLog(map,"get", true);
		else
			addLog(map,"get", false);
		return list;
	}

	public MemberVO getMember(Integer id) {
		Map<String, Object> map = memberDao.getMember(id);
		MemberVO member = (MemberVO) map.get("data");
		if (member != null)
			addLog(map,"get", true);
		else
			addLog(map,"get", false);
		return member;
	}

	public MemberVO addMember(MemberVO member) {
		Map<String, Object> map = memberDao.addMember(member);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			addLog(map,"post", true);
		else
			addLog(map,"post", false);
		return m;
	}

	public MemberVO updateMember(MemberVO member) {
		Map<String, Object> map = memberDao.updateMember(member);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			addLog(map,"put", true);
		else
			addLog(map,"put", false);
		return m;
	}

	public MemberVO deleteMember(Integer id) {
		Map<String, Object> map = memberDao.deleteMember(id);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			addLog(map,"delete", true);
		else
			addLog(map,"delete", false);
		return m;
	}
}
