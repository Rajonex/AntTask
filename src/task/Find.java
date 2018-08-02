package task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import util.FileXMLWriter;
import util.MetalinkFile;

public class Find extends Task {

	String file;
	String url;
    private ArrayList<FileSet> filesets = new ArrayList<>();
    
    


	public void setFile(String file) {
		this.file = file;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addFileset(FileSet fileset) {
        filesets.add(fileset);
    }
    
    protected void validate() {
        if(filesets.size()<1) throw new BuildException("fileset not set");
        if(url == null)
        	url = getProject().getProperty("server.files.url");
    }

    public void execute() {
    	validate();
    	
    	List<MetalinkFile> metalinks = new ArrayList<>();
    	
    	for(FileSet fileSet: filesets)
    	{
    		DirectoryScanner diretoryScanner = fileSet.getDirectoryScanner(getProject());
    		String[] filesNames = diretoryScanner.getIncludedFiles();
    		for(String name : filesNames)
    		{
    			File file = new File(name);
    			name = name.replace("\\", "/");
    			MetalinkFile metalinkFile = new MetalinkFile();
    			metalinkFile.setName(file.getName());
    			metalinkFile.setSize(file.length());
    			if(url.charAt(url.length()-1) != '/')
    				metalinkFile.setUrl(url + "/" + name);
    			else
    				metalinkFile.setUrl(url + name);


    			try {
    			String md5 = DigestUtils.md5Hex(Files.readAllBytes(file.toPath()));
    			metalinkFile.setMD5(md5);
    			} catch(IOException er)
    			{
    				er.printStackTrace();
    			}
    			
    			metalinks.add(metalinkFile);
    		}
//    		System.out.println(fileSet + ":" + fileSet.getDescription());
    	}
    	
//    	for(MetalinkFile meta : metalinks)
//    	{
//    		System.out.println(meta.getUrl() + ", size: " + meta.getSize() + " MD5: " + meta.getMD5());
//    	}
    	FileXMLWriter.createAndWriteFile(file, metalinks); 
    	


    }
}