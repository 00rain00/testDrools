package comment;

public class Highlight {
	public double hlScore;
	//public double hlScoreK;
	public double damageScore;//re
	public double getHlScore() {
		return hlScore;
	}
	public void setHlScore(double hlScore) {
		this.hlScore = hlScore;
	}
	public double getDamageScore() {
		return damageScore;
	}
	public void setDamageScore(double damageScore) {
		this.damageScore = damageScore;
	}
	public double getDifDis() {
		return difDis;
	}
	public void setDifDis(double difDis) {
		this.difDis = difDis;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getActionScore() {
		return actionScore;
	}
	public void setActionScore(double actionScore) {
		this.actionScore = actionScore;
	}
	public int getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	public double getP1DistanceToCenter() {
		return p1DistanceToCenter;
	}
	public void setP1DistanceToCenter(double p1DistanceToCenter) {
		this.p1DistanceToCenter = p1DistanceToCenter;
	}
	public double getP2DistanceToCenter() {
		return p2DistanceToCenter;
	}
	public void setP2DistanceToCenter(double p2DistanceToCenter) {
		this.p2DistanceToCenter = p2DistanceToCenter;
	}
	public double getP1Damage() {
		return p1Damage;
	}
	public void setP1Damage(double p1Damage) {
		this.p1Damage = p1Damage;
	}
	public double getP2Damage() {
		return p2Damage;
	}
	public void setP2Damage(double p2Damage) {
		this.p2Damage = p2Damage;
	}
	public double getP1Energy() {
		return p1Energy;
	}
	public void setP1Energy(double p1Energy) {
		this.p1Energy = p1Energy;
	}
	public double getP2Energy() {
		return p2Energy;
	}
	public void setP2Energy(double p2Energy) {
		this.p2Energy = p2Energy;
	}
	public double getP1Hits() {
		return p1Hits;
	}
	public void setP1Hits(double p1Hits) {
		this.p1Hits = p1Hits;
	}
	public double getP2Hits() {
		return p2Hits;
	}
	public void setP2Hits(double p2Hits) {
		this.p2Hits = p2Hits;
	}
	public double difDis; // distance to each other
	public double time;
	public double actionScore;
	public int frameNumber;
	public double p1DistanceToCenter;
	public double p2DistanceToCenter;
	public double p1Damage;
	public double p2Damage;
	public double p1Energy;
	public double p2Energy;
	public double p1Hits;
	public double p2Hits;
	public Highlight() {
		super();
		this.hlScore = -1;
		this.damageScore = -1;
		this.difDis = -1;
		this.time = -1;
		this.actionScore = -1;
		this.frameNumber = -1;
		this.p1DistanceToCenter = -1;
		this.p2DistanceToCenter = -1;
		this.p1Damage = -1;
		this.p2Damage = -1;
		this.p1Energy = -1;
		this.p2Energy = -1;
		this.p1Hits = -1;
		this.p2Hits = -1;
	}
	
}
