library(xlsx)
http <- read.xlsx("http.xlsx", sheetName="Sheet1")
ping <- read.xlsx("ping.xlsx", sheetName="Sheet1")

taskOne<-function(){
}

taskTwo<-function(){
	windows()
	#par( mfrow = c( 2, 1 ),oma=c(0,0,4,0))
	plot(x=http[,1],y=http[,2]/(http[,3]*1000), xlab="Timestamp [ms]", ylab="Throughput [Kbit/s]",cex.lab=1.4)
	title(main="Data Throughput",col.main="#484848",cex.main=2)
	windows()	
	plot(x = http[,1], y=http[,2]/1048576, xlab="Timestamp [ms]", ylab="Bytes Downloaded [MB]",cex.lab=1.4)
	title(main="Transferred Bytes",col.main="#484848",cex.main=2)
}

taskThree<-function(){
	windows()
	par( mar=c(8, 6, 6, 2))
	plot(x=ping[,1],y=ping[,2]*100, xlab="Timestamp [ms]", ylab="Packet Loss [%]",cex.lab=1.4)
	title(main="Packet Loss",col.main="#484848",cex.main=2)
	abline(h=median(ping[,2]*100), col="#e60047", lwd=3)
	abline(h=mean(ping[,2]*100), col="#00e69f", lwd=3)
	abline(h=quantile(ping[,2]*100,probs = c(0.25, 0.5, 0.75, 1)), col="#e69f00")	
	legend("bottom", inset=c(0,-0.3), c("Median","Mean","Quantils"), xpd = TRUE, lty=c(1,1,1),lwd=c(2,2,2),col=c("#e60047","#00e69f","#e69f00"), bty = "n",cex = 0.75, horiz=TRUE)
	windows()	
	par( mar=c(8, 6, 6, 2))
	plot(x = http[,1], y=http[,3], xlab="Timestamp [ms]", ylab="Average Round Trip Time [ms]",cex.lab=1.4)
	title(main="Round Trip Time",col.main="#484848",cex.main=2)
	abline(h=median(http[,3]), col="#e60047", lwd=3)
	abline(h=mean(http[,3]), col="#00e69f", lwd=3)
	abline(h=quantile(http[,3],probs = c(0.25, 0.5, 0.75, 1)), col="#e69f00")	
	legend("bottom", inset=c(0,-0.3), c("Median","Mean","Quantils"), xpd = TRUE, lty=c(1,1,1),lwd=c(2,2,2),col=c("#e60047","#00e69f","#e69f00"), bty = "n",cex = 0.75, horiz=TRUE)
}

taskOne()
taskTwo()
taskThree()