// mail-sender configure
grails{
	mail{
		host="smtp.exmail.sina.com"
		port=25
		username="monitor@helome.com"
		password="he10me.c0m"
		props=["mail.smtp.auth":"true",
	      "mail.smtp.socketFactory.port":"25"
//	      "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
//	      "mail.smtp.socketFactory.fallback":"false"
		  ]
		disabled=false
	}
	
	sms{
		enable=true
		host="http://sms.hcsdsms.com:8080/smsservice/smsservice.asmx/SendEx"
		userId="3FDA05A1EB"
		password="135790"
		destNumber="18683712095"
		bizType=16
		msgtpl="服务器 [#ip#:#port#] 无法连接.请相关人员亲测以确认.#time#"
	}
}