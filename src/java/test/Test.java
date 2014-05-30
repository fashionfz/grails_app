package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Tool;

public class Test extends Configured implements Tool {
	
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>{
		
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			System.out.println(key);
			StringTokenizer tokenizer = new StringTokenizer(line);
			while(tokenizer.hasMoreTokens()){
				word.set(tokenizer.nextToken());
				context.write(word, one);
			}
		}
	}
	
	public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable>{
		
		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Context arg2)
				throws IOException, InterruptedException {
			int sum = 0;
			System.out.println("key  =" + arg0.toString());
			for (IntWritable val : arg1) {
				System.out.println("sum = " + sum);
				sum += val.get();
			}
			arg2.write(arg0, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception {
//		int ret = ToolRunner.run(new Test(), args);
//		System.out.println(ret);
//		List<String> foldernames = new ArrayList<String>();
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.set(Calendar.YEAR, 2014);
//		calendar1.set(Calendar.MONTH, 0);
//		long time = calendar1.getTimeInMillis() - new Date().getTime();
//		long days = Math.abs(time) / (1000 * 60 * 60 * 24);
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(time < 0 ? calendar1.getTimeInMillis() : new Date().getTime());
//		while(days > -1){
//			foldernames.add(DateStringHelper.date2String(calendar.getTime(), "yyyyMMdd"));
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
//			days--;
//		}
//		System.out.println(foldernames);
////		实例化SequenceInputStream
//		Vector<InputStream> ins = new Vector<InputStream>();
//		for(String foldername : foldernames){
////			for(FileStatus fileStatu : {fs.listStatus(new Path("...."+foldername+"/hostname/indicatorname/"))})
////				ins.add(fileStatu.getPath());
//		}
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Configuration cnf = getConf();
		UserGroupInformation userGroup = UserGroupInformation.createUserForTesting("hadoop", new String[]{"hadoop"});
		
		FileSystem fs = DFSUntil.getFileSystemAs(userGroup, cnf);
		System.out.println(fs.delete(new Path("hdfs://172.16.4.94:8020/monitor/output"), true));
		
//		FileStatus fileStatus[] = fs.listStatus(new Path("hdfs://172.16.4.94:8020/monitor/20140216/localhost"));
//		for(int i=0;i<fileStatus.length;i++){
//			System.out.println(fileStatus[i].getPath().getName());
//		}
		InputStream is = fs.open(new Path("hdfs://172.16.4.94:8020/monitor/20140216/localhost/portcount/"));
		InputStream is1 = fs.open(new Path("hdfs://172.16.4.94:8020/monitor/20140216/helomeinfo.com/portcount/"));
		SequenceInputStream sis = new SequenceInputStream(is,is1);
		OutputStream out = fs.create(new Path("hdfs://172.16.4.94:8020/monitor/output/test.dat"));
		IOUtils.copyBytes(sis, out, 2048);
		IOUtils.closeStream(sis);
		System.exit(0);
		Job job = DFSUntil.getJobAs(userGroup, cnf);
		job.setJarByClass(Test.class);
		job.setJobName("wordcount");
		
		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://172.16.4.94:8020/abc.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://172.16.4.94:8020/monitor/output"));
		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

}
