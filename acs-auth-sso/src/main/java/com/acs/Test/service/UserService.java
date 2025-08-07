package com.acs.Test.service;

import com.acs.common.dto.UsersAuthDto;
import com.acs.Test.dto.*;
import com.acs.common.exception.BaseException;
import com.acs.common.enums.DeviceType;
import java.io.IOException;

public interface UserService {

	UsersAuthDto getUserAuthDetailsByUserName(String userName);

	Users userLogin(LoginRequestDto loginData, DeviceType deviceType, String userAgent, String appVersion)
			throws BaseException, IOException;
}