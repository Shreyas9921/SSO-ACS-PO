package com.acs.Test.controller;

import java.io.IOException;
import java.util.*;
import javax.validation.Valid;
import com.acs.Test.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.acs.common.enums.DeviceType;
import com.acs.common.exception.BaseException;
import com.acs.common.utils.Constant;
import com.acs.Test.service.UserService;
import com.acs.Test.commons.utils.RestResponse;
import com.acs.Test.commons.utils.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/user")
@Api(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, tags = {
		"User Api's" }, hidden = false)
@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 401, message = "Not Authorized"), @ApiResponse(code = 403, message = "Not Authenticated"),
		@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal Server Error") })
public class UserController {

	@Value("${acs.application.ui.session.expired}")
	private String userAccountSessionExpired;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "User Login", response = Users.class, notes = "User Login", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully LogIn", response = Users.class) })
	@PostMapping(path = "/userLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse<Users>> userLogin(
			@RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
			@RequestHeader(name = Constant.APP_VERSION) String appVersion,
			@ApiIgnore @RequestHeader Map<String, String> headers, @RequestBody @Valid LoginRequestDto loginData)
			throws BaseException, IOException {
		return RestUtils
				.successResponse(userService.userLogin(loginData, deviceType, headers.get("User-Agent"), appVersion));

	}

}