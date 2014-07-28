package org.jenkinsci.plugins.mergeoptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.logging.Logger;

import org.jenkinsci.plugins.mergeoptions.MergeOptionParameterDefinition.MergeUsingChangelists;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.model.ParameterValue;
import hudson.plugins.perforce.PerforceSCM;
import hudson.scm.SCM;

public class MergeOptionParameterValue extends ParameterValue {
	
private MergeOptionParameterDefinition.Option syncOption;
    
    public static String nameT;
    
    @DataBoundConstructor
    public MergeOptionParameterValue(String name, MergeOptionParameterDefinition.Option syncOption) {
        super(name);
        this.syncOption = syncOption;
        
        nameT = syncOption.name;
        Logger.getLogger(name).warning("in value constructor" + syncOption);
        
    }
    
    public Map<String,String> getLocalVarMap() {
        Map<String,String> map = new HashMap<String,String>(0);
        map.put("Merge Option", MergeOptionParameterValue.nameT );

        return map;
    }
    
    public String getSyncOption() {
        return syncOption.getOptionName();
    }

    public void setSyncOption(MergeOptionParameterDefinition.Option syncOption) {
        this.syncOption = syncOption;
    }
    
    
    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
    	// Get the root project
    	FreeStyleProject project = (FreeStyleProject) build.getProject();
    	
    	Map<String,String> localVarMap = getLocalVarMap();
    	
    	env.putAll(localVarMap);
    	/* List<String> myList = new ArrayList<String>();
    	
    	MergeUsingChangelists m = new MergeUsingChangelists(myList);
    	
    	myList = m.getChangelists();
    	*/// Get the SCM from the root project.
    	
    //	Logger.getLogger("").warning("changelist is :" + myList);
    	
    	SCM scm = project.getScm();
    	
    	
    	// Verify that the SCM attached to the project is actually a PerforceSCM. 
    	if (scm instanceof PerforceSCM) {    		
    		// Use perforceSCM variable to alter the current SCM.
    		PerforceSCM perforceSCM = (PerforceSCM) scm;
	    	
	    	syncOption.applyPerforceOptions();
	    	Exception e = new Exception();
	    	e.printStackTrace();
        }
    }
    
    


}
