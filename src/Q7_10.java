public class Q7_10 {
	public static void main(String args[]) {
		MyTv t = new MyTv();
		t.turnOnTV();
		t.setChannel(10); 
		System.out.println("CH:"+t.getChannel());
		t.setVolume(20);
		t.setChannel(50);
		System.out.println("CH:"+t.getChannel());
		System.out.println("VOL:"+t.getVolume());
		t.gotoPrevChannel();
		System.out.println("CH:"+t.getChannel());
	}
}

class MyTv {
	final int MAX_VOLUME = 100;
	final int MIN_VOLUME = 0; 
	final int MAX_CHANNEL = 100; 
	final int MIN_CHANNEL = 1;
	
	private boolean isPowerOn;
	private int channel;
	private int volume;
	private int prevchannel;
	
	public void gotoPrevChannel() {
		setChannel(prevchannel);
	}
	public void turnOnTV() {
		setPowerOn(true);
	}
	public void turnOffTV() {
		setPowerOn(false);
	}
	public void setChannel(int channel) {
		if(channel < MIN_CHANNEL || channel > MAX_CHANNEL) {
			System.out.println("No Channel");
			return;
		}
		prevchannel = this.channel;
		this.channel = channel;
		
	}
	public void setVolume(int volume) {
		if(volume < MIN_VOLUME || volume > MAX_VOLUME) {
			System.out.println("WRONG VOLUME");
		}
		this.volume = volume;
	}
	public int getChannel() {
		return channel;
	}
	
	public int getVolume() {
		return volume;
	}
	public boolean isPowerOn() {
		return isPowerOn;
	}
	public void setPowerOn(boolean isPowerOn) {
		this.isPowerOn = isPowerOn;
	}
}