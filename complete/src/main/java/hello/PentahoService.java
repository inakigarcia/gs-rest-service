package hello;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class PentahoService {

	public String call(String webPage) {
		String result = "";

		try {
			
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", getAuth());
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();

			System.out.println(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getAuth() {
		String authStringEnc = "";
		try {
			String name = "attg_pentaho1";
			String password = "$shiro1$SHA-512$100000$UhC7g1APohTMS5YMkNGKWA==$RCKQnXgnWIj+b3t/RdLCkZdbyMa7kDEMTst/T+PQ8G6ccDLe9PwvE9DGByGXQ6EHJD5G1k33rFmHiWsSIZXGuw==";

			String authString = name + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes("UTF-8"));
			authStringEnc = new String(authEncBytes);
			

			authStringEnc = "Basic " + authStringEnc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(authStringEnc);
		return authStringEnc;
	}
}