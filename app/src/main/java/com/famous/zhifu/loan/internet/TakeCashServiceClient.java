package com.famous.zhifu.loan.internet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.MyApp;

public class TakeCashServiceClient {

	private static ThreadPoolExecutor executor = null;

	private static JSONObject _call(String method,
			Map<String, Object> arguments, String callid) throws Exception {
		JSONObject args = new JSONObject();
		args.put("_deviceid", MyApp._deviceId);
		if (callid != null) {
			args.put("_callid", callid);
		}
		args.put("_method", method);
		if (arguments != null) {
			for (Map.Entry<String, Object> pair : arguments.entrySet()) {
				args.put(pair.getKey(), pair.getValue());
			}
		}

		String command = args.toString();
		command = command
				+ "               ".substring(0,
						(16 - command.getBytes("UTF-8").length % 16) % 16);

		String client = Consts.CONFIG_CLIENT;
		String version = Consts.CONFIG_VERSION;
		String key = Consts.CONFIG_KEY;

		byte[] keyBytes = new byte[(key.getBytes().length + 15) / 16 * 16];
		System.arraycopy(key.getBytes(), 0, keyBytes, 0, key.getBytes().length);
		byte[] iv = new byte[16];
		SecureRandom rnd = new SecureRandom();
		rnd.nextBytes(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"),
				new IvParameterSpec(iv));
		byte[] paramsBytes = cipher.doFinal(command.getBytes("UTF-8"));
		byte[] commandBytes = new byte[iv.length + paramsBytes.length];
		System.arraycopy(iv, 0, commandBytes, 0, iv.length);
		System.arraycopy(paramsBytes, 0, commandBytes, iv.length,
				paramsBytes.length);

		// command = Base64.encodeBase64String(commandBytes);
		// String sign = DigestUtils.sha1Hex(client + "\n" + key + "\n" +
		// command);
		String sign = Md5Util.getMD5Str(client + "|" + key + "|" + method);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("_cmd_", method));
		params.add(new BasicNameValuePair("_deviceid_", MyApp._deviceId));
		params.add(new BasicNameValuePair("_client_", client));
		params.add(new BasicNameValuePair("_sign_", sign));
		params.add(new BasicNameValuePair("_token_", MyApp.token));
		if (arguments != null) {
			for (Map.Entry<String, Object> pair : arguments.entrySet()) {
				params.add(new BasicNameValuePair(pair.getKey(), (String) pair
						.getValue()));
			}
		}

		JSONObject responseObject;
		HttpURLConnection conn = (HttpURLConnection) new URL(Consts.SERVICE_URL)
				.openConnection();
		conn.setReadTimeout(50000);
		conn.setConnectTimeout(50000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		String body = URLEncodedUtils.format(params, "UTF-8");

		OutputStream os = conn.getOutputStream();
		os.write(body.getBytes());
		os.close();

		conn.connect();
		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
		String result = getStreamString(in);
		responseObject = new JSONObject(new String(result.getBytes(),
				HTTP.UTF_8));
		return responseObject;
	}

	/**
	 * 将输入流变为字符串
	 * 
	 * @param tInputStream
	 * @return
	 */
	public static String getStreamString(InputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static JSONObject call(String method, Map<String, Object> arguments) {
		return call(method, arguments, null);
	}

	public static JSONObject call(String method, Map<String, Object> arguments,
			String callid) {
		try {
			return _call(method, arguments, callid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static interface Callback {
		public void callback(JSONObject data);
	}
}
