install.packages("igraph")
library(xlsx)
library(igraph)
http <- read.xlsx("http.xlsx", sheetName="Sheet1")
ping <- read.xlsx("ping.xlsx", sheetName="Sheet1")
gizmo01.status <- read.xlsx("status-01.xlsx", sheetName="status")
gizmo02.status <- read.xlsx("status-02.xlsx", sheetName="status")
gizmo03.status <- read.xlsx("status-03.xlsx", sheetName="status")
gizmo04.status <- read.xlsx("status-04.xlsx", sheetName="status")
gizmo05.status <- read.xlsx("status-05.xlsx", sheetName="status")
gizmo06.status <- read.xlsx("status-06.xlsx", sheetName="status")
gizmo01.links <- read.xlsx("status-01.xlsx", sheetName="links")
gizmo02.links <- read.xlsx("status-02.xlsx", sheetName="links")
gizmo03.links <- read.xlsx("status-03.xlsx", sheetName="links")
gizmo04.links <- read.xlsx("status-04.xlsx", sheetName="links")
gizmo05.links <- read.xlsx("status-05.xlsx", sheetName="links")
gizmo06.links <- read.xlsx("status-06.xlsx", sheetName="links")
gizmo01.routes <- read.xlsx("status-01.xlsx", sheetName="routes")
gizmo02.routes <- read.xlsx("status-02.xlsx", sheetName="routes")
gizmo03.routes <- read.xlsx("status-03.xlsx", sheetName="routes")
gizmo04.routes <- read.xlsx("status-04.xlsx", sheetName="routes")
gizmo05.routes <- read.xlsx("status-05.xlsx", sheetName="routes")
gizmo06.routes <- read.xlsx("status-06.xlsx", sheetName="routes")

plotTopology<-function(){
	percentage=c(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0)
	#windows()
	pdf("task1.pdf", paper="A4")
	par( mfrow = c( 3, 2 ),oma=c(0,0,4,0),mar=c(1, 1, 1, 1))
	for(i in 1:10){
		if(i==7 ){
			title(main="Network Topology",col.main="#484848",outer=T)
			#windows()
			par( mfrow = c( 3, 2 ),oma=c(2,2,4,2),mar=c(1, 1, 1, 1))
		}	
		g1Timestamp.per = gizmo01.status[floor(nrow(gizmo01.status)*percentage[i]),1]
		g2Timestamp.per = gizmo02.status[floor(nrow(gizmo02.status)*percentage[i]),1]
		g3Timestamp.per = gizmo03.status[floor(nrow(gizmo03.status)*percentage[i]),1]
		g4Timestamp.per = gizmo04.status[floor(nrow(gizmo04.status)*percentage[i]),1]
		g5Timestamp.per = gizmo05.status[floor(nrow(gizmo05.status)*percentage[i]),1]
		g6Timestamp.per = gizmo06.status[floor(nrow(gizmo06.status)*percentage[i]),1]
		g1negihbors = subset(gizmo01.links[gizmo01.links[,1]==g1Timestamp.per,])
		g2negihbors = subset(gizmo02.links[gizmo02.links[,1]==g2Timestamp.per,])
		g3negihbors = subset(gizmo03.links[gizmo03.links[,1]==g3Timestamp.per,])
		g4negihbors = subset(gizmo04.links[gizmo04.links[,1]==g4Timestamp.per,])
		g5negihbors = subset(gizmo05.links[gizmo05.links[,1]==g5Timestamp.per,])
		g6negihbors = subset(gizmo06.links[gizmo06.links[,1]==g6Timestamp.per,])
		data.per<-rbind(g1negihbors, g2negihbors, g3negihbors, g4negihbors, g5negihbors, g6negihbors)
		net<-graph_from_data_frame(data.per[,2:3], directed = TRUE)
		plot(net,edge.arrow.size=0.5, vertex.color="#ffc135",vertex.frame.color="#ffc135",vertex.label.color="#2f2f2f", vertex.label.cex=1.2)
		t<-100*percentage[i]
		title(main=paste(t,"% of the time",sep=""),col.main="#484848")
	}
	title(main="Network Topology",col.main="#484848",outer=T)
	dev.off()
}

plotLinkQuality<-function(){
	nodes=c("10.0.0.1","10.0.0.2","10.0.0.3","10.0.0.4","10.0.0.5","10.0.0.6")
	windows()
	#pdf("task1.pdf", paper="a4")
	par(mfrow = c( 6, 6 ),oma=c(4,2, 4, 2),mar=c(1.2, 1.2, 1.2, 1.2))
	for(i in 1:6){
		variableName=paste("gizmo0",".links",sep=toString(i))
		node<-eval(as.name(variableName))
		for(j in 1:6){
			if(i==j){
				plot(c(0, 1), c(0, 1), ann = F, bty = 'n', type = 'n', xaxt = 'n', yaxt = 'n')
				text(x = 0.5, y = 0.5, paste("gizmo-0",i,sep=""), cex = 1.6, col = "black")	
			}
			else{
				nodePair<-subset(node[node[,3]==nodes[j],])
				if(nrow(nodePair)!=0){
					plot(x=nodePair[,1], y=nodePair[,5])
				}else{
					plot(x=c(0),y=c(0),col="white", xaxt='n', yaxt='n')
				}
			}
		}
	}
	title(main="Link Quality vs Time",outer=T,line=1,xlab="Timestamp [ms]", ylab="Link Quality [%]",cex.main=2,col.main="#3e3e3e")
	#dev.off()
}

plotRoutes<-function(){
	nodes=c("10.0.0.1","10.0.0.2","10.0.0.3","10.0.0.4","10.0.0.5","10.0.0.6")
	windows()
	pdf("task1.pdf", paper="a4")
	par(mfrow = c( 6, 6 ),oma=c(4,2, 4, 2),mar=c(1.2, 1.2, 1.2, 1.2))
	for(i in 1:6){
		variableName=paste("gizmo0",".routes",sep=toString(i))
		node<-eval(as.name(variableName))
		for(j in 1:6){
			if(i==j){
				plot(c(0, 1), c(0, 1), ann = F, bty = 'n', type = 'n', xaxt = 'n', yaxt = 'n')
				text(x = 0.5, y = 0.5, paste("gizmo-0",i,sep=""), cex = 1.6, col = "black")	
			}
			else{
				nodePair<-subset(node[node[,2]==nodes[j],])
				if(nrow(nodePair)!=0){
					plot(x=nodePair[,1], y=substring(nodePair[,4],8), yaxt='n')
					axis(2, at=c(1,2,3,4,5,6), labels=c("g-01","g-02","g-03","g-04","g-05","g-06"))
				}else{
					plot(x=c(0),y=c(0),col="white", xaxt='n', yaxt='n')
				}
			}
		}
	}
	title(main="Gateways vs Time",outer=T,line=1,xlab="Timestamp [ms]", ylab="Gateway [id]",cex.main=2,col.main="#3e3e3e")
	dev.off()
}

taskOne<-function(){
	plotLinkQuality()
	plotRoutes()	
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
	plot(x = ping[,1], y=ping[,4], xlab="Timestamp [ms]", ylab="Average Round Trip Time [ms]",cex.lab=1.4)
	title(main="Round Trip Time",col.main="#484848",cex.main=2)
	abline(h=median(ping[,4]), col="#e60047", lwd=3)
	abline(h=mean(ping[,4]), col="#00e69f", lwd=3)
	abline(h=quantile(ping[,4],probs = c(0.25, 0.5, 0.75, 1)), col="#e69f00")	
	legend("bottom", inset=c(0,-0.3), c("Median","Mean","Quantils"), xpd = TRUE, lty=c(1,1,1),lwd=c(2,2,2),col=c("#e60047","#00e69f","#e69f00"), bty = "n",cex = 0.75, horiz=TRUE)
}

taskOne()
taskTwo()
taskThree()