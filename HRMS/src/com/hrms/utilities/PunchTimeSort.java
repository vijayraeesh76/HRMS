package com.hrms.utilities;

import java.util.Comparator;

import com.hrms.model.PunchTime;

public class PunchTimeSort implements Comparator<PunchTime>{

	@Override
	public int compare(PunchTime o1, PunchTime o2) {
		// TODO Auto-generated method stub
		return o1.getpTime().compareTo(o2.getpTime());
	}

}
