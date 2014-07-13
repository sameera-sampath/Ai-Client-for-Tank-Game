package ai;

import java.util.LinkedList;

/**
 *
 * @author AMILA
 */


public class Queue {
    private LinkedList<QueueNode>  nodeList = new LinkedList<QueueNode>();

    public void enqueue(QueueNode e) {
        nodeList.addLast(e);
    }

    public QueueNode dequeue() {
        return nodeList.pollFirst();
    }

    public int size() {
        return nodeList.size();
    }

    public boolean isEmpty() {
        return nodeList.isEmpty();
    }

    public void removeAll() {
        nodeList.clear();
    }
}
