package com.example.home.taphackathon.helpers;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

	Context context;
	Properties properties;

	public PropertiesHelper(Context context) {
		InputStream inputStream = null;
		try {
			inputStream = context.getAssets().open("properties-file.properties");
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAwsAccesKeyId() {
		return properties.getProperty("aws.access-key-id");
	}

	public String getAwsSecretAccessKey() {
		return properties.getProperty("aws.secret-access-key");
	}

	public String getAwsRegion() {
		return properties.getProperty("aws.region");
	}

	public String getEnrollmentBucketName() {
		return properties.getProperty("enrollment-bucket");
	}

	public String getVerificationBucketName() {
		return properties.getProperty("verification-bucket");
	}

	public String getIdentificationBucketName() {
		return properties.getProperty("identification-bucket");
	}

	public String getJdbcDriverClass() {
		return properties.getProperty("jdbc-driver-class");
	}

	public String getDatabaseUrl() {
		return properties.getProperty("database-url");
	}

	public String getDatabaseUsername() {
		return properties.getProperty("database-username");
	}

	public String getDatabsePassword() {
		return properties.getProperty("database-password");
	}

	public String getDatabaseName() {
		return properties.getProperty("database-name");
	}

	public String getImageFormat() {
		return properties.getProperty("image-format");
	}

	public String getFacesCollectionId() {
		return properties.getProperty("faces-collection-id");
	}

	public String getMaxFaces() {
		return properties.getProperty("max-faces");
	}

	public String getThreshold() {
		return properties.getProperty("threshold");
	}

	public String getIdentityPoolId() {
		return properties.getProperty("aws.identity-pool-id");
	}
}	
