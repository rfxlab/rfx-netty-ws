package rfx.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import rfx.core.config.AmazonS3Config;



public class AmazonS3Util {
	static AmazonS3Config s3Config = AmazonS3Config.load();
	static AmazonS3 s3 = new AmazonS3Client(s3Config.getBasicAWSCredentials());
	
	public static void uploadFile(String filePath, String awsKey){
		String bucketName = s3Config.getDefaultBucketName();	
		File logfile = new File(filePath);
		s3.putObject(new PutObjectRequest(bucketName, awsKey,logfile));		
	}
	
	public static List<String> listObjects(){
		String bucketName = s3Config.getDefaultBucketName();				
		List<String> list = new ArrayList<>();
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("localserver_access.log"));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
			list.add(objectSummary.getKey());
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(AmazonS3Util.listObjects());
	}
}
