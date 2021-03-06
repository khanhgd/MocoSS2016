install.packages("igraph")
library(scales)
library(igraph)

nodePairLqVsTime<-function(ip,neighbor){
	nodePair<-subset(lq[lq[,1]==ip & lq[,2]==neighbor,])
	plot(nodePair[,3:4], cex = 0.8, axes=FALSE, col="#484848")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey")
	abline(h=median(nodePair[,4]), col="#e60047", lwd=3)
	abline(h=mean(nodePair[,4]), col="#00e69f", lwd=3)
	abline(h=quantile(nodePair[,4],probs = c(0.1, 0.8, 0.9, 1)), col="#e69f00")
	title(main=paste(ip,neighbor,sep=" - "), ,col.main="#484848")	
	legend("bottom", inset=c(0,-0.3), c("Median","Mean","Quantils"), xpd = TRUE, lty=c(1,1,1),lwd=c(2,2,2),col=c("#e60047","#00e69f","#e69f00"), bty = "n",cex = 0.75, horiz=TRUE)
}
nodeLqVsTime<-function(ip){
	colors=c("#e60047","#E69F00","#00e69f", "#0047e6","#9f00e6","#00bae6","#e62c00","#bae600","#ffff00")
	nodes=c("192.168.2.101","192.168.2.102","192.168.2.103","192.168.2.105","192.168.2.106","192.168.2.107","192.168.2.108","192.168.2.109","192.168.2.110")
	for(i in 1:length(nodes)){
		if(nodes[i]!=ip){
			nodePair<-subset(lq[lq[,1]==ip & lq[,2]==nodes[i],])
			if(nrow(nodePair)!=0){
				plot(nodePair[,3:4],col=alpha(colors[i],0.6),type="l", axes=FALSE,lty="dotted",ann=FALSE)
				abline(h=median(nodePair[,4]), col=alpha(colors[i],0.6), lwd=1.5)
				abline(h=mean(nodePair[,4]), col=alpha(colors[i],0.6), lwd=1.5,lty="dashed")
				abline(h=quantile(nodePair[,4],probs = c(0.1, 0.8, 0.9, 1)), col=alpha(colors[i],0.6), lwd=0.5, lty="dotted")
				par(new=TRUE)
			}
		}
	}
	title(main=ip,col.main="#484848")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey")	
}
taskOne<-function(){
	colors=c("#e60047","#E69F00","#00e69f", "#0047e6","#9f00e6","#00bae6","#e62c00","#bae600","#ffff00")
	nodes=c("192.168.2.101","192.168.2.102","192.168.2.103","192.168.2.105","192.168.2.106","192.168.2.107","192.168.2.108","192.168.2.109","192.168.2.110")
	#windows()
	pdf("task1.pdf", paper="letter")
	par(mfrow = c( 3, 3 ),oma=c(18,3, 4, 2),mar=c(2, 2, 2, 0))
	for(i in 1:length(nodes)){
		nodeLqVsTime(nodes[i])
	}
	title(main="Link Quality vs Time",outer=T,line=1,xlab="Timestamp", ylab="Link Quality",cex.main=2,col.main="#3e3e3e")
	par(fig = c(0, 1, 0, 1), oma = c(0, 0, 5, 2), mar = c(0, 0, 0, 0), new = TRUE)
	plot(0, 0, type = "n", bty = "n", xaxt = "n", yaxt = "n")
	legend("bottom", nodes, xpd = TRUE, inset = c(0, 0.1), bty = "n", lty=c(1,1,1),lwd=c(2,2,2), col=colors, text.col="#484848")
	legend("bottom", c("Median","Mean","Quantils"), xpd = TRUE, inset = c(0, 0.05), bty = "n", lty=c("solid","dashed","dotted"),lwd=c(2,2,2), col=colors[1], text.col="#484848", horiz=TRUE)
	dev.off()
}
taskTwo<-function(){
	nodes=c("192.168.2.101","192.168.2.102","192.168.2.103","192.168.2.105","192.168.2.106","192.168.2.107","192.168.2.108","192.168.2.109","192.168.2.110")
	percentage=c(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0)
	#windows()
	pdf("task2.pdf", paper="A4")
	par( mfrow = c( 2, 2 ),oma=c(0,0,4,0))
	for(i in 1:10){
		if(i==5 || i==9 ){
			par( mfrow = c( 2, 2 ),oma=c(2,2,4,2))
			title(main="Network Topology",col.main="#484848",outer=T)
		}
		data.per<-lq[1:floor(nrow(lq)*percentage[i]),]
		data.temp<-data.frame(ip=character(),neighbor=character(),lq=double())
		for(j in 1:length(nodes)){
			node<-subset(data.per[data.per[,1]==nodes[j],])
			if(median(node[,4])>=0.85){
				data.temp<-rbind(data.temp,node[,c(1,2,4)])
			}
		}
		deduped.data.per<-unique(data.temp[,c(1,2)])
		net<-graph_from_data_frame(deduped.data.per, directed = TRUE)
		plot(net,edge.arrow.size=.05, vertex.color="#E69F00",vertex.frame.color="#E69F00",vertex.label.color="#484848")
		t<-100*percentage[i]
		title(main=paste(t,"% data",sep=""),col.main="#484848")
	}
	title(main="Network Topology",col.main="#484848",outer=T)
	dev.off()
}
taskThree<-function(){
	ips=c("192.168.2.101","192.168.2.101","192.168.2.103","192.168.2.106")
	neighbors=c("192.168.2.108","192.168.2.109","192.168.2.110","192.168.2.110")
	pdf("task3.pdf", paper="letter")
	#windows()
	par( mfrow = c( 2, 2 ),oma=c(4,2,4,2))
	for(i in 1:length(ips)){
		nodePair<-subset(lq[lq[,1]==ips[i] & lq[,2]==neighbors[i],])
		plot(nodePair[,3:4], cex = 0.8, axes=FALSE, col="#484848")
		axis(1, col = "grey", col.ticks="grey", col.axis="grey")
		axis(2, col = "grey", col.ticks="grey", col.axis="grey")
		abline(h=median(nodePair[,4]), col="#e60047", lwd=3)
		abline(h=mean(nodePair[,4]), col="#00e69f", lwd=3)
		abline(h=quantile(nodePair[,4],probs = c(0.1, 0.8, 0.9, 1)), col="#e69f00")
		title(main=paste(ips[i],neighbors[i],sep=" - "), ,col.main="#484848")	
	}
	title(main="Abnormalities",col.main="#484848",outer=T)
	par(fig = c(0, 1, 0, 1), oma = c(0, 0, 5, 2), mar = c(0, 0, 0, 0), new = TRUE)
	plot(0, 0, type = "n", bty = "n", xaxt = "n", yaxt = "n")
	legend("bottom", inset=c(-0.05,0), c("Median","Mean","Quantils"), xpd = TRUE, lty=c(1,1,1),lwd=c(2,2,2),col=c("#e60047","#00e69f","#e69f00"), bty = "n",cex = 0.75, horiz=TRUE)
	dev.off()
}
taskFour<-function(){
	ips=c("192.168.2.101","192.168.2.102","192.168.2.103","192.168.2.106")
	neighbors=c("192.168.2.109","192.168.2.107","192.168.2.106","192.168.2.110")
	pdf("task4.pdf", paper="letter")
	#windows()
	par( mfrow = c( 2, 2 ),oma=c(4,2,4,2))
	for(i in 1:4){
		nodePair1<-subset(lq[lq[,1]==ips[i] & lq[,2]==neighbors[i],])
		sizeNP<-nrow(nodePair1)
		nodePair2<-subset(lq[lq[,1]==neighbors[i] & lq[,2]==ips[i],])[1:sizeNP,]
		sb<-cor(nodePair1[,4],nodePair2[,4], use="complete")
		print(sb)
		plot(nodePair1[,4],nodePair2[,4], axes=FALSE, col="#484848", ann=FALSE)
		title(main=paste(ips[i],neighbors[i],sep=" - "), ,col.main="#484848")
		axis(1, col = "grey", col.ticks="grey", col.axis="grey")
		axis(2, col = "grey", col.ticks="grey", col.axis="grey")
	}
	title(main="Correlation Analysis of Link Quality",col.main="#484848",outer=T)
	dev.off()
}
taskFive<-function(){
	#windows()
	ips=c("192.168.2.101","192.168.2.102","192.168.2.107")
	neighbors=c("192.168.2.102","192.168.2.108","192.168.2.110")
	pdf("task5.pdf", paper="letter")
	for(i in 1:3){
		par( mfrow = c( 1, 2 ), oma=c(4,2,4,2))
		boxplot(subset(lq[lq[,1]==ips[i] & lq[,2]==neighbors[i],])[,4], ylab="lq", axes=FALSE,border="#484848")
		title(main=paste(ips[i],neighbors[i],sep=" - ") ,col.main="#484848")
		axis(2, col = "grey", col.ticks="grey", col.axis="grey")
		nodePairLqVsTime(ips[i],neighbors[i])
		#windows()
	}
	dev.off()
}
taskSix<-function(){
	data.102<-lq[lq$ip=="192.168.2.102" & lq$neighbor=="192.168.2.108",]
	data.103<-lq[lq$ip=="192.168.2.103" & lq$neighbor=="192.168.2.108",]
	pdf("task6.pdf", paper="letter")
	#windows()
	hist(data.102$lq, xlim=c(0,1), main ="", xlab="Link quality", axes=FALSE,border="#484848")
	title(main ="192.168.2.102 - 192.168.2.108" ,col.main="#484848")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey")
	#windows()
	hist(data.103$lq, xlim=c(0,1), main ="", xlab="Link quality", axes=FALSE,border="#484848")
	title(main ="192.168.2.103 - 192.168.2.108" ,col.main="#484848")
	axis(1, col = "grey", col.ticks="grey", col.axis="grey")
	axis(2, col = "grey", col.ticks="grey", col.axis="grey")
	dev.off()
}

taskOne()
taskTwo()
taskThree()
taskFour()
taskFive()
taskSix()