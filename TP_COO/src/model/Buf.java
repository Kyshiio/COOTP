package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Buf extends AtomicComponent{
	
	private int q;
	
	public Buf(String name){
		super(name);
		
		integer_varnames_var = new HashMap<String,Integer>();
		outputs.add("req");
		inputs.add("done");
		inputs.add("job");
	}
	
	public void init() {
		q = 0;
		integer_varnames_var.put("q",q);
		current_state = 0;
	}

	public void delta_int(){
		if(current_state == 1){
			q--;
			integer_varnames_var.put("q",q);
			changeState(2);
		}
		current_state = next_state;
	}

	public void delta_ext(ArrayList<String> inputs){
		if(current_state == 0 && inputs.contains("job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(1);
		}
		else if(current_state == 1 && inputs.contains("job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(1);
		}
		else if(current_state == 2 && inputs.contains("done")){
			if(q>0)
				changeState(1);
			if(q==0)
				changeState(0);
		}
		else if(current_state == 2 && inputs.contains("job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(2);
		}
		
		current_state = next_state;
	}
	
	public void delta_con(ArrayList<String> inputs){
		current_state = next_state;
	}

	public ArrayList<String> lambda(){
		ArrayList<String> outputs = new ArrayList<String>();
		if(current_state == 1){
			outputs.add("req");
		}
		return outputs;
	}

	public double getTa(){

		if(current_state == 0){
			return Double.POSITIVE_INFINITY;
		}
		else if(current_state == 1){
			return 0.0;
		}
		else if(current_state == 2){
			return Double.POSITIVE_INFINITY;
		}
		
		return 0;
	}
}