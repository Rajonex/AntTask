package util;

public class MetalinkFile {
	private String name;
	private long size;
	private String MD5;
	private String url;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public MetalinkFile() {}
	
	public MetalinkFile(String name, long size, String mD5, String url) {
		super();
		this.name = name;
		this.size = size;
		MD5 = mD5;
		this.url = url;
	}
	
	
	
}
