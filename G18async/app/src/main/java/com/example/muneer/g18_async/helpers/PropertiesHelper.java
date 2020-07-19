package com.example.muneer.g18_async.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
	
	Properties properties;
	InputStream propertiesFileStream;

	public PropertiesHelper(InputStream propertiesFileStream) {
		this.propertiesFileStream = propertiesFileStream;
		properties = new Properties();
		try {
			properties.load(propertiesFileStream);
			System.out.println("properties-file loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	public String getAsyncIdentificationBucketName() {
		return properties.getProperty("async-identification-bucket");
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
	
	public String getG18UsersTableName() {
		return properties.getProperty("g18users-table-name");
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

	public String getG18DataTableName() {
		return properties.getProperty("g18data-table-name");
	}

	public String getIdentityPoolId() {
		return properties.getProperty("aws.identity-pool-id");
	}
}	
