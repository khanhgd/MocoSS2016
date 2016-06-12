
public class jsonObject {
	//status
	private long systemTime;
	private long timeSinceStartup;
	
	//neighbors
	private String ipAdress;
	private boolean symmetric;
	private boolean multiPointRelay;
	private boolean multiPointRelaySelector;
	private long willingness;
	private long twoHopNeighborCount;
	
	//links
    private String localIP;
	private String remoteIP;
	private long validityTime;
	private double linkQuality;
	private double neighborLinkQuality;
	private long linkCost;
	
	//routes    	
    private String destination;
	private long genmask;
	private String gateway;
	private long metric;
	private long rtpMetricCost;
	private String networkInterface;
	
	//Topology
	private String destinationIP;
	private String lastHopIP;
	//private double linkQuality;
	//private double neighborLinkQuality;
	private long tcEdgeCost;
	//private long validityTime;

	public long getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}
	
	public long getTimeSinceStartup() {
		return timeSinceStartup;
	}
	public void setTimeSinceStartup(long timeSinceStartup) {
		this.timeSinceStartup = timeSinceStartup;
	}
	
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	
	public boolean getSymmetric() {
		return symmetric;
	}
	public void setSymmetric(boolean symmetric) {
		this.symmetric = symmetric;
	}
	
	public boolean getMultiPointRelay() {
		return multiPointRelay;
	}
	public void setMultiPointRelay(boolean multiPointRelay) {
		this.multiPointRelay = multiPointRelay;
	}
	
	public boolean getMultiPointRelaySelector() {
		return multiPointRelaySelector;
	}
	public void setMultiPointRelaySelector(boolean multiPointRelaySelector) {
		this.multiPointRelaySelector = multiPointRelaySelector;
	}
	
	public long getWillingness() {
		return willingness;
	}
	public void setWillingness(long willingness) {
		this.willingness = willingness;
	}
	
	public long getTwoHopNeighborCount() {
		return twoHopNeighborCount;
	}
	public void setTwoHopNeighborCount(long twoHopNeighborCount) {
		this.twoHopNeighborCount = twoHopNeighborCount;
	}
	public String getLocalIP() {
		return localIP;
	}
	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public long getValidityTime() {
		return validityTime;
	}
	public void setValidityTime(long validityTime) {
		this.validityTime = validityTime;
	}
	public double getLinkQuality() {
		return linkQuality;
	}
	public void setLinkQuality(double linkQuality) {
		this.linkQuality = linkQuality;
	}
	public double getNeighborLinkQuality() {
		return neighborLinkQuality;
	}
	public void setNeighborLinkQuality(double neighborLinkQuality) {
		this.neighborLinkQuality = neighborLinkQuality;
	}
	public long getLinkCost() {
		return linkCost;
	}
	public void setLinkCost(long linkCost) {
		this.linkCost = linkCost;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public long getGenmask() {
		return genmask;
	}
	public void setGenmask(long genmask) {
		this.genmask = genmask;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public long getMetric() {
		return metric;
	}
	public void setMetric(long metric) {
		this.metric = metric;
	}
	public long getRtpMetricCost() {
		return rtpMetricCost;
	}
	public void setRtpMetricCost(long rtpMetricCost) {
		this.rtpMetricCost = rtpMetricCost;
	}
	public String getNetworkInterface() {
		return networkInterface;
	}
	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}
	public long getTcEdgeCost() {
		return tcEdgeCost;
	}
	public void setTcEdgeCost(long tcEdgeCost) {
		this.tcEdgeCost = tcEdgeCost;
	}
	public String getLastHopIP() {
		return lastHopIP;
	}
	public void setLastHopIP(String lastHopIP) {
		this.lastHopIP = lastHopIP;
	}
	public String getDestinationIP() {
		return destinationIP;
	}
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

}
	
	
	
	
	
	

