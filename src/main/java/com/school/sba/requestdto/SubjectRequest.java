package com.school.sba.requestdto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubjectRequest {

	private List<String> subjectNames;
	
	@Override
	public String toString()
	{
		StringBuilder builder=new StringBuilder("");
		
		for(String s:subjectNames)
		{
			builder.append(s+" ");
		}
		String ss=builder.toString();
		
		return ss;
	}
	
}
