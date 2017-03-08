package encoder;

import java.util.ArrayList;

import model.Room;
import model.Subject;

public class Encoder {
	public String encode(Subject subject)
	{
		Queryer queryer = new Queryer();
		String courseTerm;
		String resourceTerm;
		String innerterm;
		String term="";
		term+="*(";
		for(int i=0;i<queryer.getSubjects().size();i++)
		{
			courseTerm = encodeCourse(queryer.getSubjects(),subject);
			resourceTerm = encodeResource(queryer.getRooms());
			innerterm = "*("+courseTerm+" "+resourceTerm+")";
			term+=innerterm+" ";
		}
		term+=")";
		
		String output = "";
		// TODO: call SAT solver
		// output = SATsolver.solve
		
		return output;
	}
	public String encodeCourse(ArrayList<Subject> allSubjs,Subject subj)
	{
		// pick only courses that are not that subject
		// format 0ssssDtttt
		allSubjs.remove(subj);
		
		String result="+("+subj.getId()+" ";
		result += "+(";
		for(int i = 0;i< allSubjs.size();i++)
		{
			result += "0"+allSubjs.get(i).getId()+"date"+"slot"+" ";
		}
		result+= "))";
		return result;
		
	}
	public String encodeResource(ArrayList<Room> rooms)
	{
		// format for room = 1rrrrDtttt
		// format for fit = ??
		String result="+(";
		for(int i=0;i<rooms.size();i++)
		{
			result+="*("+"1"+rooms.get(i).getID()+"date"+"slot"+"fit"+" "+")";
		}
		result+=")";
		return result;
	}
}
