package com.example.muneer.g18_async.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3ObjectHelper {

	public List<S3Object> getS3ObjectsList(AmazonS3 s3Client, String bucketName) {
		HashSet<String> keysSet = getKeysInBucket(s3Client, bucketName);
		S3Object s3Object;
		List<S3Object> s3ObjectsList = new ArrayList<S3Object>();
		for (String key : keysSet) {
			s3Object = getS3Object(s3Client, bucketName, key);
			s3ObjectsList.add(s3Object);

		}
		return s3ObjectsList;
	}

	public S3Object getS3Object(AmazonS3 s3Client, String bucketName, String key) {
		S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
		return object;
		// InputStream objectData = object.getObjectContent();
		// // Process the objectData stream.
		// objectData.close();
	}

	public HashSet<String> getKeysInBucket(AmazonS3 s3client, String bucketName) {
		HashSet<String> keysList = new HashSet<String>();

		try {
			// System.out.println("Listing objects");
			final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
			ListObjectsV2Result result;
			do {
				result = s3client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
					keysList.add(objectSummary.getKey());
					// System.out.println(
					// " - " + objectSummary.getKey() + " " + "(size = " +
					// objectSummary.getSize() + ")");
				}
				// System.out.println("Next Continuation Token : " +
				// result.getNextContinuationToken());
				req.setContinuationToken(result.getNextContinuationToken());
			} while (result.isTruncated() == true);

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return keysList;
	}

	public boolean uploadFileToS3(AmazonS3 s3client, String bucketName, String uploadFilePath) {
		try {
			// System.out.println("Uploading a new object to S3 from a file");
			File file = new File(uploadFilePath);
			String keyName = file.getName();
			HashSet<String> keysSet = getKeysInBucket(s3client, bucketName);
			if (keysSet.contains(keyName)) {
				System.out.println("File: " + keyName + " already exists in bucket: " + bucketName);
				return false;
			}
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			return true;

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return false;
	}

}
