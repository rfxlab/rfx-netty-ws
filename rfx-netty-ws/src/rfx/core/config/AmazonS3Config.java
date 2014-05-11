package rfx.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



import com.amazonaws.auth.BasicAWSCredentials;

public class AmazonS3Config {
	private String accessKeyId;
	private String secretAccessKey;
	private String defaultBucketName;
	
	private AmazonS3Config() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getSecretAccessKey() {
		return secretAccessKey;
	}
	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
	public String getDefaultBucketName() {
		return defaultBucketName;
	}
	public void setDefaultBucketName(String defaultBucketName) {
		this.defaultBucketName = defaultBucketName;
	}
	
	public BasicAWSCredentials getBasicAWSCredentials(){
		return new BasicAWSCredentials(accessKeyId, secretAccessKey);
	}
	
	private static AmazonS3Config instance = null;
	public static AmazonS3Config load(){
		if(instance == null){
			instance = new AmazonS3Config();
			Properties prop = new Properties();
			InputStream input = null;			 
			try {			 
				input = new FileInputStream("configs/amazon-s3-config.properties");
		 
				// load a properties file
				prop.load(input);
				
				instance.setAccessKeyId(prop.getProperty("accessKeyId"));
				instance.setSecretAccessKey(prop.getProperty("secretAccessKey"));
				instance.setDefaultBucketName(prop.getProperty("defaultBucketName"));
		  
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		}
		return instance;
	}
}
