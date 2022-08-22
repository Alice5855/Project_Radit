package org.zerock.service;

import org.zerock.domain.UserVO;

public interface UserService {
	
	public void regist(UserVO user);
	
	public boolean update(UserVO user);
	
	public boolean deleteAccount(String u_Email);
	
	public UserVO getINFO(String u_Email);
	
<<<<<<< HEAD

=======
>>>>>>> 233283eb66073c4dad79b5184e1040c9dff4d52c
}
