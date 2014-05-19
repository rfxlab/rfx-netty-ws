package rfx.wordpress.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class WordPressClient {
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "qazxsw@123";
	private static final int BLOG_ID = 1;
	private static final String URL = "http://localhost/wordpress/xmlrpc.php";

	public static void main(String[] args) throws Exception {
		System.out.println("Connecting to: " + URL);

		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(URL));

		final XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		final Hashtable<String, Object> content = new Hashtable<>();
		content.put("post_status", "publish");
		content.put("post_title", "place 3");
		content.put("post_content","<img src='http://maps.googleapis.com/maps/api/staticmap?center=10.7539993,106.7405375&zoom=11&size=200x200&sensor=false'> <b>some HTML markup</b>.");
		
		List<Hashtable<String, Object>> customFields = new ArrayList<>(2);		
		
		Hashtable<String, Object> customField1 = new Hashtable<>(2);
		customField1.put("key", "image1");
		customField1.put("value", "http://farm8.staticflickr.com/7314/14162948341_7e39c6eff8.jpg");
		customFields.add(customField1);
		
		Hashtable<String, Object> customField2 = new Hashtable<>(2);
		customField2.put("key", "position");
		customField2.put("value", "10.7539993,106.7405375");
		customFields.add(customField2);
		
		content.put("custom_fields", customFields);
		final boolean publish = true;

		final Object[] params = new Object[] { BLOG_ID, USERNAME, PASSWORD,
				content, publish };
		final Object result = client.execute("wp.newPost", params);
		final int pageId = Integer.parseInt(result.toString());

		System.out.println("Successful! New page ID is " + pageId);
	}
}
