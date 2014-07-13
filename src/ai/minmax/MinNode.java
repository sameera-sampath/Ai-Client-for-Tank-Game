package ai.minmax;

/**
 *
 * @author AMILA
 */

public class MinNode {

    private Integer[] pos;
    private MaxNode prevNode;
    private MaxNode[] nextNodes;

    public MinNode(Integer[] pos, int count, MaxNode prev) {
        this.prevNode = prev;
        this.pos = pos;
        Integer[] maxPrevMove = prevNode.getPos();
        Integer[][] nextMoves = MoveGenerator.getMinMoves(maxPrevMove);
        this.nextNodes = new MaxNode[nextMoves.length];
        for (int i = 0; i < nextMoves.length; i++) {
            nextNodes[i] = new MaxNode(nextMoves[i], count - 1, this);
        }
    }

    public Integer[] getPos() {
        return pos;
    }

    public void setPos(Integer[] pos) {
        this.pos = pos;
    }

    public MaxNode getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(MaxNode prevNode) {
        this.prevNode = prevNode;
    }

    public MaxNode[] getNextDodes() {
        return nextNodes;
    }

    public void setNextDodes(MaxNode[] nextDodes) {
        this.nextNodes = nextDodes;
    }

    public int getVal(int alpha, int beeta) {
        int length = nextNodes.length;
        for (int i = 0; i < length; i++) {
            int val = nextNodes[i].getVal(alpha, beeta);
            if (alpha > val) {
                return alpha;
            } else {
                alpha = val;
            }
        }
        return alpha;
    }
}
