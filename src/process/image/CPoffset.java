package process.image;

public class CPoffset {

	public final double cgXoffset;
	public final double cgYoffset;
	
	public final double rotation;
	
	public CPoffset(PartDetector pd, double scale){
		cgXoffset = (pd.cgX-pd.crX)/scale;
		cgYoffset = (pd.cgY-pd.crY)/scale;
		rotation = pd.rotation;
	}
	
}
