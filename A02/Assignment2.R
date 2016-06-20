library(xlsx)
http <- read.xlsx("http.xlsx", sheetName="Sheet1")

taskOne<-function(){
}

taskTwo<-function(){
	par( mfrow = c( 2, 1 ),oma=c(0,0,4,0))
	plot(http[,1:2], axes=FALSE, xlab="Timestamp [milliseconds]", ylab="Bytes Downloaded [MB]")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey", at=http[,2] ,labels=round(http[,2]/1048576, digits=2))
	title(main="Data Throughput",col.main="#484848")
	plot(http[,1:2], axes=FALSE, xlab="Timestamp [milliseconds]", ylab="Bytes Downloaded [MB]")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey", at=http[,2] ,labels=round(http[,2]/1048576, digits=2))
	title(main="Transferred Bytes",col.main="#484848")
}

taskThree<-function(){
}