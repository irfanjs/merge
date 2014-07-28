package org.jenkinsci.plugins.mergeoptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;



import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.ParameterDefinition.ParameterDescriptor;
import hudson.plugins.perforce.PerforceSCM;


public class MergeOptionParameterDefinition extends ParameterDefinition {
	
	
	 @DataBoundConstructor
	    public MergeOptionParameterDefinition(String name) {
	        super("MergeOption");        
	    }
	 
	 @Override
	    public ParameterValue createValue(StaplerRequest req, JSONObject json) {
	    
	    	Option option = new MergeUsingHeadRevesion ();
	    	  try {
	              option = req.bindJSON(Option.class, req.getSubmittedForm().getJSONObject("option"));
	              setSelectedOption(option);
	          } catch (ServletException ex) {
	            //  Logger.getLogger(ChoiceSyncOptions.class.getName()).log(Level.SEVERE, null, ex);
	          }
	          return new MergeOptionParameterValue("MergeOption", option);
	      }
	 
	 public DescriptorExtensionList<Option,Descriptor<Option>> getOptionDescriptors() {
	        return Hudson.getInstance().<Option,Descriptor<Option>>getDescriptorList(Option.class);
	    }
	 
	 @Override
	    public ParameterValue createValue(StaplerRequest sr) {
	        throw new UnsupportedOperationException("Not supported yet.");
	    }
	 
	 private Option selectedOption = null;
	 
	 public Option getSelectedOption() {
	        return selectedOption;
	    }
	    
	    public void setSelectedOption(Option selectedOption) {
	        this.selectedOption = selectedOption;
	    }
	 
	 @Extension
	    public static class DescriptorImpl extends ParameterDescriptor {

	        @Override
	        public String getDisplayName() {
	            return "Enable Perforce sync options at runtime";
	        }
	        
	        
	    }
	 
	    public static abstract class Option implements ExtensionPoint, Describable<Option> {
	        protected String name;
	        protected Option(String name) { this.name = name; }
	        
	        public Descriptor<Option> getDescriptor() {
	            return Hudson.getInstance().getDescriptor(getClass());
	        }
	        
	        public String getOptionName(){
	        	return this.name;
	        }
	        
	        public abstract void applyPerforceOptions();
	    }

	    public static class OptionDescriptor extends Descriptor<Option> {
	        public OptionDescriptor(Class<? extends Option> clazz) {
	            super(clazz);
	        }
	        public String getDisplayName() {
	            return clazz.getSimpleName();
	        }
	    }
	    
	    public static class MergeUsingHeadRevesion extends Option {
	        @DataBoundConstructor public MergeUsingHeadRevesion() {
	            super("MergeUsingHeadRevesion");
	        }
	       
	        @Extension public static final OptionDescriptor D = new OptionDescriptor(MergeUsingHeadRevesion.class);

	        @Override
	        public void applyPerforceOptions() { 
	        }
	    }
	    
	    public static class MergeUsingChangelists extends Option {
	        //private String changelist = null;
	        List<String> myList = new ArrayList<String>();
	        
	        @DataBoundConstructor public MergeUsingChangelists(List<String> myList) {
	            super("MergeUsingChangelists");
	           // this.changelist = changelist;
	            this.myList = myList;
	        }
	        public List<String> getChangelists() {
	            return myList;
	        }
	        @Extension public static final OptionDescriptor D = new OptionDescriptor(MergeUsingChangelists.class);
			@Override
			public void applyPerforceOptions() {
				
				Logger.getLogger("").warning("changelist is :" + myList);				
				
			}

}
}
