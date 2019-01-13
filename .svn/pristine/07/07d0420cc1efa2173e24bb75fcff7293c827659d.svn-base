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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.MyApp;

public class ServiceClient {

	private static ThreadPoolExecutor executor = null;

	private static JSONObject _call(String method,Map<String, Object> arguments, String callid,boolean isToken) throws Exception {
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
		// System.out.println("MyApp.token   :"+MyApp.token);
		
		if (isToken) {	//判断是否传递这个字段，只有在验证码获取的时候才有可能不加该字段
			params.add(new BasicNameValuePair("_token_", MyApp.token));
		}else {
			
		}
		
		if (arguments != null) {
			for (Map.Entry<String, Object> pair : arguments.entrySet()) {
				params.add(new BasicNameValuePair(pair.getKey(), (String) pair
						.getValue()));
			}
		}

		JSONObject responseObject;
		HttpURLConnection conn = (HttpURLConnection) new URL(Consts.SERVICE_URL).openConnection();
		conn.setReadTimeout(5000);
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		String body = URLEncodedUtils.format(params, "UTF-8");
		
		Log.i("入参：", body);

		OutputStream os = conn.getOutputStream();
		os.write(body.getBytes());
		os.close();

		conn.connect();
		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
		String result = getStreamString(in);
		
		 Log.i("test1", result);
		
//		int len = result.indexOf("{");
		
		// Log.i("test2", len+"");
		
//		String temp2 = result.substring(len, result.length());
		
		responseObject = new JSONObject(new String(result.getBytes(),HTTP.UTF_8));
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

	public static void call(String method, Callback cb) {
		call(method, null, null, cb);
	}

	public static void call(String method, Map<String, Object> arguments,
			Callback cb) {
		call(method, arguments, null, cb);
	}
	
	//临时加的方法
	public static void call(String method, Map<String, Object> arguments,Callback cb,boolean isToken) {
		call(method, arguments, null, cb,isToken);
	}

	public static void call(String method, Map<String, Object> arguments,
			String callid, Callback cb) {
		if (executor == null) {
			executor = new ThreadPoolExecutor(1, 5, 60L, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		executor.execute(new ClientCommand(method, arguments, callid, cb,true));
	}
	
	//临时加的方法
	public static void call(String method, Map<String, Object> arguments,String callid, Callback cb,boolean isToken) {
		if (executor == null) {
			executor = new ThreadPoolExecutor(1, 5, 60L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
		}
		executor.execute(new ClientCommand(method, arguments, callid, cb,isToken));
	}

	public static interface Callback {
		public void callback(JSONObject data);
	}

	public static class ClientCommand implements Runnable {
		private String method;
		private Map<String, Object> arguments;
		private String callid;
		private Callback cb;
		private Handler mHandler;
		
		private boolean isToken;

		// private INoNetWorkListener listener;
		//
		// public void setNoNetWorkListener(INoNetWorkListener listener){
		// this.listener = listener;
		// }

		public ClientCommand(String method, Map<String, Object> arguments,String callid, Callback cb,boolean isToken) {
			this.method = method;
			this.arguments = arguments;
			this.callid = callid;
			this.cb = cb;
			this.isToken = isToken;
			mHandler = new Handler();
		}

		@Override
		public void run() {
			try {
				final JSONObject data = ServiceClient._call(method, arguments,callid,isToken);
				if (data == null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MyApp.context, "获取数据失败，未连接到网络", 1).show();
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							cb.callback(data);
						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
