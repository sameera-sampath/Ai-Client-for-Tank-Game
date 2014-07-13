package ai;

import gameengine.GameEngine;
import java.util.Observable;
import java.util.Observer;
import models.Coin;
import models.DeadPlayer;
import models.LifePack;
import models.MapObject;
import models.Obstacle;
import models.Player;

/**
 *
 * @author AMILA
 */


public class AIPlayer implements Observer {

    private MapObject[][] map;
    private Player thisPlayer;
    private QueueNode nodeMap[][];
    private Queue queue;
    private boolean previousShoot = false;

    public AIPlayer() {
        nodeMap = new QueueNode[GameEngine.SIZE][GameEngine.SIZE];
        queue = new Queue();
    }

    public void update(Observable o, Object arg) {
        if (o instanceof GameEngine) {
            if (((String) arg).equals("GLOBAL_UPDATE")) {
                map = GameEngine.getInstance().getMap();
                thisPlayer = GameEngine.getInstance().getThisPlayer();
                makeNextDecision();
            }
        }
    }

    private void makeNextDecision() {
        String path = getNextPath();
        int rotate = rotateAndKll();

        if (previousShoot && enemyAheadDead()) {
            if (enemyAheadDead()) {
                makeMove(thisPlayer.getDirection());
            }
            previousShoot = false;
        } else if (path.length() > 0 && path.length() < 10) {
            makeMove(Integer.parseInt(path.charAt(0) + ""));
        } else if (thisPlayer.getHealth() > 50 && enemyAhead(1)) {
            makeMove(99);//shoot
            previousShoot = true;
        } else if (rotate != -1) {
            makeMove(rotate);
        } else if (path.length() > 0) {
            makeMove(Integer.parseInt(path.charAt(0) + ""));
        } else {
            int[] arr ;
            if (thisPlayer.getX() != 2 && thisPlayer.getY() != 2) {
                arr = new int[]{2, 2};
            }else if(thisPlayer.getX() != 2 && thisPlayer.getY() != 3) {
                arr = new int[]{2, 3};
            }else if(thisPlayer.getX() != 2 && thisPlayer.getY() != 4) {
                arr = new int[]{2, 4};
            }else if(thisPlayer.getX() != 3 && thisPlayer.getY() != 2) {
                arr = new int[]{3, 4};
            }else if(thisPlayer.getX() != 3 && thisPlayer.getY() != 3) {
                arr = new int[]{3, 3};
            }else if(thisPlayer.getX() != 3 && thisPlayer.getY() != 4) {
                arr = new int[]{3, 2};
            }else if(thisPlayer.getX() != 4 && thisPlayer.getY() != 2) {
                arr = new int[]{4, 2};
            }else if(thisPlayer.getX() != 4 && thisPlayer.getY() != 3) {
                arr = new int[]{4, 3};
            }else if(thisPlayer.getX() != 4 && thisPlayer.getY() != 4) {
                arr = new int[]{4, 4};
            }else{
                arr = new int[]{0, 0};
            }
            
            String defPath = findpath(arr);
            makeMove(Integer.parseInt(defPath.charAt(0) + ""));
        }
    }

    private boolean enemyAheadDead() {
        int direction = this.thisPlayer.getDirection();
        int dist = 1;
        switch (direction) {
            case 0:
                if (isIn(thisPlayer.getX(), thisPlayer.getY() - dist)
                        && map[thisPlayer.getX()][thisPlayer.getY() - dist] instanceof Player
                        && (map[thisPlayer.getX()][thisPlayer.getY() - dist] instanceof DeadPlayer)) {
                    return true;
                } else {
                    return false;
                }
            case 1:
                if (isIn(thisPlayer.getX() + dist, thisPlayer.getY())
                        && map[thisPlayer.getX() + dist][thisPlayer.getY()] instanceof Player
                        && (map[thisPlayer.getX() + dist][thisPlayer.getY()] instanceof DeadPlayer)) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (isIn(thisPlayer.getX(), thisPlayer.getY() + dist)
                        && map[thisPlayer.getX()][thisPlayer.getY() + dist] instanceof Player
                        && (map[thisPlayer.getX()][thisPlayer.getY() + dist] instanceof DeadPlayer)) {
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (isIn(thisPlayer.getX() - dist, thisPlayer.getY())
                        && map[thisPlayer.getX() - dist][thisPlayer.getY()] instanceof Player
                        && (map[thisPlayer.getX() - dist][thisPlayer.getY()] instanceof DeadPlayer)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;

        }
    }

    private int rotateAndKll() {
        for (int i = 0; i < 4; i++) {
            if (thisPlayer.getDirection() == i) {
                continue;
            }
            if (enemyAhead(1, i)) {
                return i;
            }
        }
        return -1;
    }

    private String getNextPath() {
        int[] Tco = findTarget();
        String path = findpath(Tco);
        System.out.println("");
        return path;
    }

    private String findpath(int[] Tco) {
        String path = "";
        QueueNode curruntNode = nodeMap[Tco[0]][Tco[1]];

        while (curruntNode.getPreviousX() >= 0) {
            if (curruntNode.getPreviousX() == curruntNode.getX()) {
                if (curruntNode.getPreviousY() > curruntNode.getY()) {
                    path = "0" + path;
                } else {
                    path = "2" + path;
                }
            } else {
                if (curruntNode.getPreviousX() > curruntNode.getX()) {
                    path = "3" + path;
                } else {
                    path = "1" + path;
                }
            }
            curruntNode = nodeMap[curruntNode.getPreviousX()][curruntNode.getPreviousY()];
        }
        queue.removeAll();
        return path;
    }

    private int[] findTarget() {
        int Tco[] = new int[2];      
        int Sco[] = new int[2];      
        boolean targetFound = false;
        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap.length; j++) {
                nodeMap[i][j] = new QueueNode();
            }
        }

        Sco[0] = thisPlayer.getX();
        Sco[1] = thisPlayer.getY();

        Tco[0] = Sco[0];
        Tco[1] = Sco[1];

        nodeMap[Sco[0]][Sco[1]].setIsStart(true);
        nodeMap[Sco[0]][Sco[1]].setIsTarget(false);
        nodeMap[Sco[0]][Sco[1]].setState(1);
        nodeMap[Sco[0]][Sco[1]].setX(Sco[0]);
        nodeMap[Sco[0]][Sco[1]].setY(Sco[1]);

        queue.enqueue(nodeMap[Sco[0]][Sco[1]]);

        while (!targetFound && !queue.isEmpty()) {
            QueueNode current = queue.dequeue();

            int x = current.getX();
            int y = current.getY();
            int direction = thisPlayer.getDirection();
            switch (direction) {
                case 0:
                    if (isIn(x, y - 1)) {
                        if (nodeMap[x][y - 1].getState() == 0) {
                            targetFound = discoverNode(x, y - 1);
                            nodeMap[x][y - 1].setPreviousX(x);
                            nodeMap[x][y - 1].setPreviousY(y);
                            if (targetFound) {
                                Tco[0] = x;
                                Tco[1] = y - 1;
                                return Tco;
                            }
                        }
                    }
                    break;
                case 1:
                    if (isIn(x + 1, y)) {
                        if (nodeMap[x + 1][y].getState() == 0) {
                            targetFound = discoverNode(x + 1, y);
                            nodeMap[x + 1][y].setPreviousX(x);
                            nodeMap[x + 1][y].setPreviousY(y);
                            if (targetFound) {
                                Tco[0] = x + 1;
                                Tco[1] = y;
                                return Tco;
                            }
                        }
                    }
                    break;
                case 2:
                    if (isIn(x, y + 1)) {
                        if (nodeMap[x][y + 1].getState() == 0) {
                            targetFound = discoverNode(x, y + 1);
                            nodeMap[x][y + 1].setPreviousX(x);
                            nodeMap[x][y + 1].setPreviousY(y);
                            if (targetFound) {
                                Tco[0] = x;
                                Tco[1] = y + 1;
                                return Tco;
                            }
                        }
                    }
                    break;
                case 3:
                    if (isIn(x - 1, y)) {
                        if (nodeMap[x - 1][y].getState() == 0) {
                            targetFound = discoverNode(x - 1, y);
                            nodeMap[x - 1][y].setPreviousX(x);
                            nodeMap[x - 1][y].setPreviousY(y);
                            if (targetFound) {
                                Tco[0] = x - 1;
                                Tco[1] = y;
                                return Tco;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

            if (direction == 0) {
                if (isIn(x + 1, y)) {
                    if (nodeMap[x + 1][y].getState() == 0) {
                        targetFound = discoverNode(x + 1, y);
                        nodeMap[x + 1][y].setPreviousX(x);
                        nodeMap[x + 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x + 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
                if (isIn(x, y + 1)) {
                    if (nodeMap[x][y + 1].getState() == 0) {
                        targetFound = discoverNode(x, y + 1);
                        nodeMap[x][y + 1].setPreviousX(x);
                        nodeMap[x][y + 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y + 1;
                            return Tco;
                        }
                    }
                }
                if (isIn(x - 1, y)) {
                    if (nodeMap[x - 1][y].getState() == 0) {
                        targetFound = discoverNode(x - 1, y);
                        nodeMap[x - 1][y].setPreviousX(x);
                        nodeMap[x - 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x - 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
            } else if (direction == 1) {
                if (isIn(x, y - 1)) {
                    if (nodeMap[x][y - 1].getState() == 0) {
                        targetFound = discoverNode(x, y - 1);
                        nodeMap[x][y - 1].setPreviousX(x);
                        nodeMap[x][y - 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y - 1;
                            return Tco;
                        }
                    }
                }
                if (isIn(x, y + 1)) {
                    if (nodeMap[x][y + 1].getState() == 0) {
                        targetFound = discoverNode(x, y + 1);
                        nodeMap[x][y + 1].setPreviousX(x);
                        nodeMap[x][y + 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y + 1;
                            return Tco;
                        }
                    }
                }
                if (isIn(x - 1, y)) {
                    if (nodeMap[x - 1][y].getState() == 0) {
                        targetFound = discoverNode(x - 1, y);
                        nodeMap[x - 1][y].setPreviousX(x);
                        nodeMap[x - 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x - 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
            } else if (direction == 2) {
                if (isIn(x, y - 1)) {
                    if (nodeMap[x][y - 1].getState() == 0) {
                        targetFound = discoverNode(x, y - 1);
                        nodeMap[x][y - 1].setPreviousX(x);
                        nodeMap[x][y - 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y - 1;
                            return Tco;
                        }
                    }
                }
                if (isIn(x + 1, y)) {
                    if (nodeMap[x + 1][y].getState() == 0) {
                        targetFound = discoverNode(x + 1, y);
                        nodeMap[x + 1][y].setPreviousX(x);
                        nodeMap[x + 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x + 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
                if (isIn(x - 1, y)) {
                    if (nodeMap[x - 1][y].getState() == 0) {
                        targetFound = discoverNode(x - 1, y);
                        nodeMap[x - 1][y].setPreviousX(x);
                        nodeMap[x - 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x - 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
            } else if (direction == 3) {
                if (isIn(x, y - 1)) {
                    if (nodeMap[x][y - 1].getState() == 0) {
                        targetFound = discoverNode(x, y - 1);
                        nodeMap[x][y - 1].setPreviousX(x);
                        nodeMap[x][y - 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y - 1;
                            return Tco;
                        }
                    }
                }
                if (isIn(x + 1, y)) {
                    if (nodeMap[x + 1][y].getState() == 0) {
                        targetFound = discoverNode(x + 1, y);
                        nodeMap[x + 1][y].setPreviousX(x);
                        nodeMap[x + 1][y].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x + 1;
                            Tco[1] = y;
                            return Tco;
                        }
                    }
                }
                if (isIn(x, y + 1)) {
                    if (nodeMap[x][y + 1].getState() == 0) {
                        targetFound = discoverNode(x, y + 1);
                        nodeMap[x][y + 1].setPreviousX(x);
                        nodeMap[x][y + 1].setPreviousY(y);
                        if (targetFound) {
                            Tco[0] = x;
                            Tco[1] = y + 1;
                            return Tco;
                        }
                    }
                }
            }
            nodeMap[x][y].setState(2);
        }
        return Tco;
    }

    private boolean discoverNode(int x, int y) {
        if (isIn(x, y)) {
            if (isTarget(x, y)) {
                setAsTarget(x, y);
                return true;
            } else if (isObstacle(x, y)) {
                nodeMap[x][y].setIsStart(false);
                nodeMap[x][y].setIsTarget(false);
                nodeMap[x][y].setState(2);
                nodeMap[x][y].setX(x);
                nodeMap[x][y].setY(y);
                return false;
            } else {
                nodeMap[x][y].setIsStart(false);
                nodeMap[x][y].setIsTarget(false);
                nodeMap[x][y].setState(1);
                nodeMap[x][y].setX(x);
                nodeMap[x][y].setY(y);
                queue.enqueue(nodeMap[x][y]);
                return false;
            }
        }
        return false;
    }

    private void setAsTarget(int x, int y) {
        nodeMap[x][y].setIsStart(false);
        nodeMap[x][y].setIsTarget(true);
        nodeMap[x][y].setState(1);
        nodeMap[x][y].setX(x);
        nodeMap[x][y].setY(y);
    }

    private boolean isTarget(int x, int y) {
        if ((map[x][y] instanceof Coin) || ((map[x][y] instanceof LifePack) && thisPlayer.getHealth() < 50)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isObstacle(int x, int y) {
        if (map[x][y] instanceof Obstacle) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isIn(int x, int y) {
        if (x < GameEngine.SIZE && y < GameEngine.SIZE && x >= 0 && y >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void makeMove(int move) {
        switch (move) {
            case 1:
                GameEngine.getInstance().moveRight();
                break;
            case 3:
                GameEngine.getInstance().moveLeft();
                break;
            case 2:
                GameEngine.getInstance().moveDown();
                break;
            case 0:
                GameEngine.getInstance().moveUp();
                break;
            case 99:  
                GameEngine.getInstance().shoot();
                break;
            default:
                break;
        }
    }

    private boolean enemyAhead(int dist) {
        return enemyAhead(dist, thisPlayer.getDirection());
    }

    private boolean enemyAhead(int dist, int direction) {
        switch (direction) {
            case 0:
                if (isIn(thisPlayer.getX(), thisPlayer.getY() - dist)
                        && map[thisPlayer.getX()][thisPlayer.getY() - dist] instanceof Player
                        && !(map[thisPlayer.getX()][thisPlayer.getY() - dist] instanceof DeadPlayer)
                        && isShootable(thisPlayer.getX(), thisPlayer.getY() - dist, direction)) {
                    return true;
                } else {
                    return false;
                }
            case 1:
                if (isIn(thisPlayer.getX() + dist, thisPlayer.getY())
                        && map[thisPlayer.getX() + dist][thisPlayer.getY()] instanceof Player
                        && !(map[thisPlayer.getX() + dist][thisPlayer.getY()] instanceof DeadPlayer)
                        && isShootable(thisPlayer.getX() + dist, thisPlayer.getY(), direction)) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (isIn(thisPlayer.getX(), thisPlayer.getY() + dist)
                        && map[thisPlayer.getX()][thisPlayer.getY() + dist] instanceof Player
                        && !(map[thisPlayer.getX()][thisPlayer.getY() + dist] instanceof DeadPlayer)
                        && isShootable(thisPlayer.getX(), thisPlayer.getY() + dist, direction)) {
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (isIn(thisPlayer.getX() - dist, thisPlayer.getY())
                        && map[thisPlayer.getX() - dist][thisPlayer.getY()] instanceof Player
                        && !(map[thisPlayer.getX() - dist][thisPlayer.getY()] instanceof DeadPlayer)
                        && isShootable(thisPlayer.getX() - dist, thisPlayer.getY(), direction)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;

        }
    }

    private boolean isShootable(int x, int y, int direction) {
        Player enemy = (Player) map[x][y];
        if (enemy != null) {
            switch (direction) {
                case 0:
                case 2:
                    if (enemy.getDirection() == 0 || enemy.getDirection() == 1) {
                        return true;
                    } else {
                        return false;
                    }

                case 1:
                case 3:
                    if (enemy.getDirection() == 1 || enemy.getDirection() == 3) {
                        return true;
                    } else {
                        return false;
                    }

                default:
                    return false;
            }
        } else {
            return false;
        }
    }
}
