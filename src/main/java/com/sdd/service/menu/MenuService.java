package com.sdd.service.menu;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sdd.util.WeixinUtil;

import net.sf.json.JSONObject;

@Service("menuService")
public class MenuService {
	private Logger logger = LogManager.getLogger(MenuService.class);
	// 菜单创建（POST） 限1000（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 菜单查询（POST） 限10000（次/天）
	public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	// 菜单删除（POST） 限1000（次/天）
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 查询菜单
	 *
	 * @param accessToken
	 *            有效的access_token
	 * @return
	 */
	public JSONObject getMenu(String accessToken) {

		// 拼装创建菜单的url
		String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口查询菜单
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);

		return jsonObject;
	}

	/**
	 * 创建菜单(替换旧菜单)
	 *
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public int createMenu(Map<String, Object> menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				logger.error("****" + jsonMenu + "****");
			}
		}
		return result;
	}

	/**
	 * 删除菜单
	 *
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public int deleteMenu(String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);

		// 调用接口创建菜单
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
}
