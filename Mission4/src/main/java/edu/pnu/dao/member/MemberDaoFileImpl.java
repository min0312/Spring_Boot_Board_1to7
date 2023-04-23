package edu.pnu.dao.member;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import edu.pnu.domain.MemberVO;

public class MemberDaoFileImpl implements MemberInterface {
	private List<MemberVO> list;

	public MemberDaoFileImpl() {
		list = new ArrayList<>();
		// 파일 읽어오기
		try (BufferedReader br = new BufferedReader(new FileReader("list.txt"))) {
			String str;
			while ((str = br.readLine()) != null) {
//				list.add(generateMemberByString(str));
				List<String> slist = Collections.list(new StringTokenizer(str, ",")).stream()
						.map(token -> (String) token).collect(Collectors.toList());

				// 실제 작업 시에는 slist 갯수에 따른 에러처리 필요

				list.add(new MemberVO(Integer.parseInt(slist.get(0)), slist.get(1), slist.get(2), new Date()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
//	private MemberVO generateMemberByString(String str) {
//		StringTokenizer st = new StringTokenizer(str, ",");
//		String id = st.nextToken();
//		String pass = st.nextToken();
//		String name = st.nextToken();
//		return new MemberVO(Integer.parseInt(id), pass, name, new Date());
//	}

	@Override
	public Map<String, Object> getMembers() {
		Map<String, Object> map = new HashMap<>();
		map.put("sql", "from list getMembers()");
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> getMember(Integer id) {
		for (MemberVO m : list) {
			if (m.getId() == id) {
				Map<String, Object> map = new HashMap<>();
				map.put("sql", "from list getMember()");
				map.put("data", m);
				return map;
			}
		}
		return null;
	}

	private int getNextId() {
		return list.size() + 1;
	}

	@Override
	public Map<String, Object> addMember(MemberVO member) {
		member.setId(getNextId());
		member.setRegidate(new Date());
		list.add(member);

		Map<String, Object> map = new HashMap<>();
		map.put("sql", "from list addMember");
		map.put("data", member);

		return map;
	}

	@Override
	public Map<String, Object> updateMember(MemberVO member) {
		for (MemberVO m : list) {
			if (m.getId() == member.getId()) {
				m.setName(member.getName());
				m.setPass(member.getPass());

				Map<String, Object> map = new HashMap<>();
				map.put("sql", "from list updateMember");
				map.put("data", m);

				return map;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> deleteMember(Integer id) {
		for (MemberVO m : list) {
			if (m.getId() == id) {
				list.remove(m);

				Map<String, Object> map = new HashMap<>();
				map.put("sql", "from list deleteMember");
				map.put("data", m);

				return map;
			}
		}
		return null;
	}
}
