package com.bs.rest.modules.sc.base;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.config.annotation.Reference;

import com.bs.api.core.entity.PageData;
import com.bs.api.core.entity.ResultData;
import com.bs.api.modules.sc.dto.DictionaryDTO;
import com.bs.api.modules.sc.service.IDictionaryService;

import com.bs.rest.modules.sc.vo.DictionaryVO;
import com.bs.rest.core.entity.ReturnData;
import com.bs.rest.core.group.Id;
import com.bs.rest.core.group.Update;
import com.bs.rest.core.group.Remove;
import com.bs.rest.core.group.Save;
import com.bs.rest.core.group.Detail;
import com.bs.rest.core.group.PageList;
import com.bs.rest.core.utils.StringUtil;
import com.bs.rest.core.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "/dictionary", description = "字典信息存储表接口")
@RestController
@EnableAutoConfiguration
@RequestMapping(value="/dictionary")
public class BaseDictionaryController extends BaseController{

	public static Logger logger = Logger.getLogger(BaseDictionaryController.class);

	@Reference(version = "1.0.0",timeout=50000,retries=5)
	private IDictionaryService dictionaryApi;
	
	@ApiOperation(value = "字典信息存储表-数据添加(保存)")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ReturnData save(
			@ApiParam(name = "Dictionary实体", value = "存储数据")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {Save.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<DictionaryDTO> resultData = dictionaryApi.save(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				DictionaryDTO data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}

	@ApiOperation(value = "字典信息存储表-数据修改")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody ReturnData update(
			@ApiParam(name = "Dictionary实体", value = "根据主键Id修改数据，id不能为空！")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {Update.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<DictionaryDTO> resultData = dictionaryApi.updateById(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				DictionaryDTO data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}

	@ApiOperation(value = "字典信息存储表-根据主键Id删除")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
	public @ResponseBody ReturnData remove(
			@ApiParam(name = "Dictionary主键ID", value = "根绝主键id删除一条数据")
			@PathVariable("id") Long id,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			DictionaryVO dictionaryVO = new DictionaryVO();
			dictionaryVO.setId(id);
			Class<?> groupCls [] = {Id.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			ResultData<Long> resultData = dictionaryApi.removeById(id);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				Long data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}
	
	@ApiOperation(value = "字典信息存储表-动态条件获取单条数据")
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody ReturnData remove(
			@ApiParam(name = "Dictionary实体", value = "动态条件删除删除一条数据（包括根绝主键Id进行删除）")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {Remove.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<List<DictionaryDTO>> resultData = dictionaryApi.listCriteria(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				List<DictionaryDTO> data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}
	
	@ApiOperation(value = "字典信息存储表-根据主键Id获取单条数据")
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
	public @ResponseBody ReturnData detail(
			@ApiParam(name = "Dictionary主键ID", value = "根据主键Id查询一条数据明细")
			@PathVariable("id") Long id,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			DictionaryVO dictionaryVO = new DictionaryVO();
			dictionaryVO.setId(id);
			Class<?> groupCls [] = {Id.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			ResultData<DictionaryDTO> resultData = dictionaryApi.getOneById(id);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				DictionaryDTO data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}
	

	@ApiOperation(value = "字典信息存储表-动态条件获取单条数据")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public @ResponseBody ReturnData detail(
			@ApiParam(name = "Dictionary实体", value = "动态条件查询一条数据明细（包括根绝主键Id进行查询）")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {Detail.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<List<DictionaryDTO>> resultData = dictionaryApi.listCriteria(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				List<DictionaryDTO> data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}

	@ApiOperation(value = "字典信息存储表-动态条件列表数据")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody ReturnData list(
			@ApiParam(name = "Dictionary实体", value = "动态条件查询列表数据")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {List.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<List<DictionaryDTO>> resultData = dictionaryApi.listCriteria(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				List<DictionaryDTO> data = resultData.getData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}

	@ApiOperation(value = "字典信息存储表-动态条件分页列表")
	@RequestMapping(value = "/paginate", method = RequestMethod.POST)
	public @ResponseBody ReturnData paginate(
			@ApiParam(name = "Dictionary实体", value = "动态条件进行分页列表查询，当前页[page]，每页数量[rows]，为必传数据")
			@RequestBody DictionaryVO dictionaryVO,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ReturnData returnData = new ReturnData();
		try {
			//验证参数合法性
			Class<?> groupCls [] = {PageList.class};
			String validMsg = super.validator(dictionaryVO, groupCls);
			if (!StringUtil.isEmpty(validMsg)) {
				returnData.setCode(ReturnData.RCODE_PARAM_VALID);
				returnData.setMessage(validMsg);
				return returnData;
			}
			//调用dubbo 业务
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			//参数赋值
			BeanUtils.copyProperties(dictionaryVO, dictionaryDTO);
			ResultData<List<DictionaryDTO>> resultData = dictionaryApi.paginated(dictionaryDTO);
			if (resultData != null) {
				String code = resultData.getCode();
				String message = resultData.getMessage();
				List<DictionaryDTO> data = resultData.getData();
				PageData pageData = resultData.getPageData();
				returnData.setCode(code);
				returnData.setMessage(message);
				returnData.setData(data);
				returnData.setPageData(pageData);
				return returnData;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.RCODE_SYSTEM_ERROR);
			returnData.setMessage(e.getMessage());
			return returnData;
		}
		return returnData;
	}
}
