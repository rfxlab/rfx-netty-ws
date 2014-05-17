package rfx.wordpress.test;

import java.net.URL;
import java.util.Hashtable;

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
		content.put("post_title", "place 2");
		content.put("post_content","<img src='http://maps.googleapis.com/maps/api/staticmap?center=-15.800513,-47.91378&zoom=11&size=200x200&sensor=false'> <b>some HTML markup</b>.");
		final boolean publish = true;

		final Object[] params = new Object[] { BLOG_ID, USERNAME, PASSWORD,
				content, publish };
		final Object result = client.execute("wp.newPost", params);
		final int pageId = Integer.parseInt(result.toString());

		System.out.println("Successful! New page ID is " + pageId);
	}
}
