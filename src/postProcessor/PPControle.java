package postProcessor;

import project.ProjectFile;
import setup.SetupControle;

public class PPControle {

	private final SetupControle setup;
	private final ProjectFile project;
	
	public PPControle(SetupControle set, ProjectFile pro){
		setup = set;
		project = pro;
	}
}
