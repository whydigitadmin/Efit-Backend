package com.efitops.basaesetup.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.ChangePasswordFormDTO;
import com.efitops.basaesetup.dto.LoginFormDTO;
import com.efitops.basaesetup.dto.RefreshTokenDTO;
import com.efitops.basaesetup.dto.ResetPasswordFormDTO;
import com.efitops.basaesetup.dto.ResponsibilityDTO;
import com.efitops.basaesetup.dto.RolesDTO;
import com.efitops.basaesetup.dto.SignUpFormDTO;
import com.efitops.basaesetup.dto.UserResponseDTO;
import com.efitops.basaesetup.entity.ResponsibilityVO;
import com.efitops.basaesetup.entity.RolesVO;
import com.efitops.basaesetup.entity.UserVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest);

	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles(Long orgId);

	public List<RolesVO> getAllActiveRoles(Long orgId);
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	public List<ResponsibilityVO> getAllResponsibility(Long orgId);

	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId);
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	

}
