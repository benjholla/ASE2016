package pointsto;

import java.util.HashSet;
import java.util.LinkedList;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.xcsg.XCSG;

public class SimplePointsTo {

	private static final String P2ID = "points-to";
	private static int id = 0;
	
	// a graph of assignment relationships
    private static Q dataFlowEdges = Common.universe().edgesTaggedWithAny(XCSG.LocalDataFlow);
	
	// a worklist of nodes to propagate information from
	private static LinkedList<Node> worklist = new LinkedList<Node>();
	
	/**
	 * Performs a points to a analysis
	 */
	public static void run(){
		Q instantiations = Common.universe().nodesTaggedWithAny(XCSG.Instantiation); // new allocations
		AtlasSet<Node> instantiationNodes = instantiations.eval().nodes();
		
		// create a unique id for each allocation site and add
		// the allocation site to the worklist to propagate 
		// information forward from
		for(Node instantiation : instantiationNodes){
			HashSet<Integer> pointsToIds = getPointsToSet(instantiation);
			pointsToIds.add(id++);
			worklist.add(instantiation);
		}
		
		// keep propagating allocation ids forward along assignments
		// until there is nothing more to propagate
		while(!worklist.isEmpty()){
			Node from = worklist.removeFirst();
			propagatePointsTo(from);
		}
	}
	
	/**
	 * Propagates points-to information forward from the given node
	 * to all nodes that this node is directly assigned to. Each node
	 * that receives new information is added back to the worklist
	 * @param from
	 */
	private static void propagatePointsTo(Node from){
		AtlasSet<Node> toNodes = dataFlowEdges.successors(Common.toQ(from)).eval().nodes();
		for(Node to : toNodes){
			HashSet<Integer> fromPointsToSet = getPointsToSet(from);
			HashSet<Integer> toPointsToSet = getPointsToSet(to);
			// if the to set learned something from the from set,
			// then add the to node to the worklist because it may
			// have something to teach its children
			if(toPointsToSet.addAll(fromPointsToSet)){
				worklist.add(to);
			}
		}
	}

	/**
	 * Gets or creates the points to set for a graph element.
	 * Returns a reference to the points to set so that updates to the 
	 * set will also update the set on the graph element.
	 * @param ge
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private static HashSet<Integer> getPointsToSet(Node ge){
		if(ge.hasAttr(P2ID)){
			return (HashSet<Integer>) ge.getAttr(P2ID);
		} else {
			HashSet<Integer> pointsToIds = new HashSet<Integer>();
			ge.putAttr(P2ID, pointsToIds);
			return pointsToIds;
		}
	}
	
}
