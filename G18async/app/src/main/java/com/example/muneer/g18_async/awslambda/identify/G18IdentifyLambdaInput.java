package com.example.muneer.g18_async.awslambda.identify;

public class G18IdentifyLambdaInput {
	private String accessKeyId;
	private String secretAccessKey;
	private String region;
	
	private String identifyBucket;
	private String key;
	private String collectionId;
	
	private String kiosk;
	
	private String jdbcDriver;
	private String databaseUrl;
	private String databaseUsername;
	private String databasePassword;
	private String databaseName;
	private String g18UsersTableName;
	private String g18DatatableName;
	
	private int maxFaces;
	private Float threshold;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getIdentifyBucket() {
		return identifyBucket;
	}
	public void setIdentifyBucket(String identifyBucket) {
		this.identifyBucket = identifyBucket;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getKiosk() {
		return kiosk;
	}
	public void setKiosk(String kiosk) {
		this.kiosk = kiosk;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getDatabaseUrl() {
		return databaseUrl;
	}
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	public String getDatabaseUsername() {
		return databaseUsername;
	}
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getG18UsersTableName() {
		return g18UsersTableName;
	}
	public void setG18UsersTableName(String g18UsersTableName) {
		this.g18UsersTableName = g18UsersTableName;
	}
	public String getG18DatatableName() {
		return g18DatatableName;
	}
	public void setG18DatatableName(String g18DatatableName) {
		this.g18DatatableName = g18DatatableName;
	}
	public int getMaxFaces() {
		return maxFaces;
	}
	public void setMaxFaces(int maxFaces) {
		this.maxFaces = maxFaces;
	}
	public Float getThreshold() {
		return threshold;
	}
	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public String toString() {
		return "G18IdentifyLambdaInput [accessKeyId=" + accessKeyId + ", secretAccessKey=" + secretAccessKey
				+ ", region=" + region + ", identifyBucket=" + identifyBucket + ", key=" + key + ", collectionId="
				+ collectionId + ", kiosk=" + kiosk + ", jdbcDriver=" + jdbcDriver + ", databaseUrl=" + databaseUrl
				+ ", databaseUsername=" + databaseUsername + ", databasePassword=" + databasePassword
				+ ", databaseName=" + databaseName + ", g18UsersTableName=" + g18UsersTableName + ", g18DatatableName="
				+ g18DatatableName + ", maxFaces=" + maxFaces + ", threshold=" + threshold + "]";
	}
}