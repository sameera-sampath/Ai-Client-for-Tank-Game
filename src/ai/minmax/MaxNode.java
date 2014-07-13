package ai.minmax;

/**
 *
 * @author AMILA
 */

public class MaxNode {

    private Integer[] maxPos;
    private MinNode prevNode;
    private MinNode[] nextNodes;

    public MaxNode(Integer[] maxPos, int count, MinNode prev) {
        this.prevNode = prev;
        this.maxPos = maxPos;
        if (count != 0) {
            Integer[] minPlayersPrevMoves = prevNode.getPos();
            Integer[][] nextMoves = MoveGenerator.getMinMoves(minPlayersPrevMoves);
            this.nextNodes = new MinNode[nextMoves.length];
            for (int i = 0; i < nextMoves.length; i++) {
                nextNodes[i] = new MinNode(nextMoves[i], count - 1, this);
            }
        }
    }

    public MaxNode(Integer[] maxPos,Integer[] minPlayerPos, int count) {
        this.maxPos = maxPos;
        Integer[][] nextMoves = MoveGenerator.getMinMoves(minPlayerPos);
        this.nextNodes = new MinNode[nextMoves.length];
        for (int i = 0; i < nextMoves.length; i++) {
            nextNodes[i] = new MinNode(nextMoves[i], count - 1, this);
        }
    }

    public MinNode getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(MinNode prevNode) {
        this.prevNode = prevNode;
    }

    public MinNode[] getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(MinNode[] nextNodes) {
        this.nextNodes = nextNodes;
    }

    public Integer[] getPos() {
        return maxPos;
    }

    public void setPos(Integer[] pos) {
        this.maxPos = pos;
    }

    public int getVal(int alpha, int beeta) {
        if (this.nextNodes == null) {
            return EvaluationFunction.getEvaluationValue(maxPos[0], maxPos[1]);
        } else {
            int length = nextNodes.length;
            for (int i = 0; i < length; i++) {
                int val = nextNodes[i].getVal(alpha, beeta);
                if (val > beeta) {
                    return beeta;
                } else {
                    beeta = val;
                }
            }
            return beeta;
        }
    }
}
