// 清空HDFS输出目录
outputPath = cfg.get('outputpath')
if(fsh.test(outputPath)){
	fsh.rmr(outputPath)
}