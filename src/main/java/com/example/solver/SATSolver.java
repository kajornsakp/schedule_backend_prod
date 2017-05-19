package com.example.solver;

import com.example.JacksonModel.TimetableWrapper;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.maxsat.reader.WDimacsReader;
import org.sat4j.pb.IPBSolver;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.TimeoutException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Jrphapa on 5/10/2017 AD.
 */
public class SATSolver implements Solver{
    private Encoder encoder;
    private Decoder decoder;

    public SATSolver(){
        this.encoder = new Encoder();

    }
    
    @Override
	public void encode() {
		//leave it for refactor code
		
	}



	@Override
	public void decode() {
		//leave it for refactor code
		
	}

    public TimetableWrapper solve() {
        this.encoder.encode();
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
