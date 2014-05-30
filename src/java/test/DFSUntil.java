package test;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * DFS
 * 
 * @author wenjie
 */
public class DFSUntil {
	/**
	 * Get a FileSystem instance as specified user in a doAs block.
	 */
	static public FileSystem getFileSystemAs(UserGroupInformation ugi,
			final Configuration conf) throws IOException, InterruptedException {
		return ugi.doAs(new PrivilegedExceptionAction<FileSystem>() {
			@Override
			public FileSystem run() throws Exception {
				return FileSystem.get(conf);
			}
		});
	}
	
	static public Job getJobAs(UserGroupInformation ugi,
			final Configuration conf) throws IOException, InterruptedException {
		return ugi.doAs(new PrivilegedExceptionAction<Job>() {
			@Override
			public Job run() throws Exception {
				return new Job(conf);
			}
		});
	}
	
	public static void main(String[] args) throws Exception {
		long time = 1257087600000l;
		
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(date));
		
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.parse("2013-11-21 13:25:39").getTime());
	}
}
