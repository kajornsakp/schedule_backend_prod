package com.example.solver;

import com.example.model.Room;
import com.example.model.Subject;
import com.example.model.TimeSlot;
import com.example.repository.RoomRepository;
import com.example.repository.ScheduleRepository;

import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.maxsat.reader.WDimacsReader;
import org.sat4j.pb.IPBSolver;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jrphapa on 5/10/2017 AD.
 */

public class SATSolver implements Solver{
	

	
    private Encoder encoder;
    private Decoder decoder;

    public SATSolver(List<Room> roomList, List<Subject> subjectList){
        this.encoder = new Encoder(roomList,subjectList);

    }
    
    @Override
	public void encode() {
		//leave it for refactor code
		
	}

	@Override
	public void decode() {
		//leave it for refactor code
		
	}

    public ArrayList<TimeSlot> solve() {
        this.encoder.encode();
        System.out.println("encoded");
        this.decoder = new Decoder(encoder.getReverseTermMap());

        ////////////////////////  SAT4J  /////////////////////////////////////

        IPBSolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        WeightedMaxSatDecorator decorator = new WeightedMaxSatDecorator(solver);
        WDimacsReader reader = new WDimacsReader(decorator);

        String ans = "";
        // CNF filename is given on the command line
        try {
            IProblem problem = reader.parseInstance("cnfInput.txt");
            if (problem.isSatisfiable()) {
            	System.out.println("Satisfiable!");
                ans = reader.decode(problem.findModel());
                //Ploy's note: just ignore '-null' or 'null' (bug)
                //& current solution : satisfy max clauses then max literals

            } else {
                System.out.println("Unsatisfiable !");
                System.out.println(reader.decode(problem.findModel()));
                reader = new WDimacsReader(decorator);
                problem = reader.parseInstance("cnfInput.txt");
                System.out.println(problem.isSatisfiable() + ":satisfiable");
                System.out.println(reader.decode(problem.findModel()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ParseFormatException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }

        try {
            PrintWriter out = new PrintWriter("cnfOutput.txt");
            out.print(ans);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return decoder.decode(ans);
    }

	
}
