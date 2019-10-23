package comment;
import org.apache.maven.model.building.Result;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
public class CommentGenerate {
	
	public static void checkKie() {
		// TODO Auto-generated method stub
	System.out.println("commentgenerate called");
	KieServices ks = KieServices.Factory.get();
	KieContainer kContainer = ks.newKieClasspathContainer();
	try {
		kContainer.verify();
		
	}catch (Exception e) {
		System.out.println(e);
	}
	
		
		
		
	}
	
	
	
	
}
