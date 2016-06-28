setwd('D:\\pa02\\sampledata')

st1 = read.csv(file = 'status-01.csv',header = TRUE)
st2 = read.csv(file = 'status-02.csv',header = TRUE)
st3 = read.csv(file = 'status-03.csv',header = TRUE)
st4 = read.csv(file = 'status-04.csv',header = TRUE)
st5 = read.csv(file = 'status-05.csv',header = TRUE)
st6 = read.csv(file = 'status-06.csv',header = TRUE)

ip = c("10.0.0.1","10.0.0.2","10.0.0.3","10.0.0.4","10.0.0.5","10.0.0.6")
df = c("st1","st2","st3","st4","st5","st6")
data = c("data01","data02","data03","data04","data05","data06")

for(i in 1:6){
	colnames(df[i])<-c("SystemTime","IpNeighbor","symmetric","multiPointRelay","multiPointRelaySelector","willingness","twoHopNeighborCount")	
	df[i]$NodeIp<-ip[i]
	df[i]<-df[i][,c("SystemTime","NodeIp","IpNeighbor","symmetric","multiPointRelay","multiPointRelaySelector","willingness","twoHopNeighborCount")]
	df[i]<-fd[i][,1:4]
}


#Link quality data
lq1<-read.csv('lq-01.csv',header = TRUE)
lq2<-read.csv('lq-02.csv',header = TRUE)
lq3<-read.csv('lq-03.csv',header = TRUE)
lq4<-read.csv('lq-04.csv',header = TRUE)
lq5<-read.csv('lq-05.csv',header = TRUE)
lq6<-read.csv('lq-06.csv',header = TRUE)
lq = c("lq1","lq2","lq3","lq4","lq5","lq6")

for(i in 1:6){
	data[i]<-merge(st[i],lq[i], by.x = c("SystemTime","IpNeighbor"),by.y = c("SystemTime","destinationIP"))
}

data<-rbind(data01,data02,data03,data04,data05,data06)
data<-data[,c(1,3,2,6)]
data<-aggregate( data[,-c(1,2,3)], by = list(data$SystemTime, data$NodeIp, data$IpNeighbor), mean, na.rm=FALSE )
colnames(data)<-c('SystemTime','IpAddress','Neighbor','meanLinkQuality')
data <- data[order(data$SystemTime),]

