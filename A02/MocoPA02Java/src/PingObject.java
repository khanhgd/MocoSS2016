
public class PingObject {
	
	private long timestamp;
	private double Packetloss;
	private double RTTmin;
	private double RTTavg;
	private double RTTmax;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getPacketloss() {
		return Packetloss;
	}
	public void setPacketloss(double packetloss) {
		Packetloss = packetloss;
	}
	public double getRTTmin() {
		return RTTmin;
	}
	public void setRTTmin(double rTTmin) {
		RTTmin = rTTmin;
	}
	public double getRTTavg() {
		return RTTavg;
	}
	public void setRTTavg(double rTTavg) {
		RTTavg = rTTavg;
	}
	public double getRTTmax() {
		return RTTmax;
	}
	public void setRTTmax(double rTTmax) {
		RTTmax = rTTmax;
	}

}
