import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The use of this class is to loose the arrows and find their path to the targets.
 * We are not looking for the shortest path neither for the best,
 * we just looking for paths so arrows can find their way to the targets with some preferences:<br>
 * <b>1.</b> Target cell.<br>
 * <b>2.</b> Same direction.<br>
 * <b>3.</b> Cross path cell (with smallest index, if duplicated).<br>
 * <b>4.</b> Vertical or Horizontal path cell (with smallest index, if duplicated).<br>
 *
 * @author hala elewa
 */

public class StartSearch {

    /**
     * An object of the <b>Map</b> class, initialized in the constructor.
     */
    private final Map targetMap;
    /**
     * The number of arrow cupid has fired so far.
     */
    private int numArrows;
    /**
     * Counter to keep track of how many times arrow travel in the same direction.
     */
    private int inertia;
    /**
     * The number of the direction that arrow traveling to (0: up, 1: right, 2: down, 3: left).
     */
    private int direction;

    /**
     * Used to initialize the <b>targetMap</b>.
     *
     * @param filename Represents the map file name.
     * @throws InvalidMapException if the format of the map not valid.
     * @throws IOException         if the file does not exist or can't be opened.
     */
    public StartSearch(String filename) throws InvalidMapException, IOException {
        targetMap = new Map(filename);
    }

    /**
     * The main method of the program, used to take the inputs as command line arguments and start the program.
     *
     * @param args The command line argument list, used to pass the <b>mapFileName</b> as arg[0], and <b>maxPathLength</b> (optional).
     * @throws InvalidMapException if the format of the map not valid.
     * @throws IOException if the  file does not exist or can't be opened.
     */
    public static void main(String[] args) throws InvalidMapException, IOException {
        // check that the file name is provided
        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        }

        String mapFileName = args[0];

        /* if max path length was given assign it to the maxPathLength variable
           if it's not given the assign it to a large value (e.g. 500) */
        int maxPathLength;
        try {
            maxPathLength = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            maxPathLength = 500;
        }

        StartSearch ss = new StartSearch(mapFileName); // create an object of the StartSearch class

        ArrayStack<MapCell> s = new ArrayStack<>(maxPathLength); // create a Stack with given input

        MapCell startPoint = ss.targetMap.getStart(); // get the start cell where cupid is

        int targets = 0; // used as a counter to keep track of how many targets are hit

        // loop through the number of arrow that cupid has
        for (ss.numArrows = 1; ss.numArrows <= ss.targetMap.quiverSize(); ss.numArrows++) {
            s.push(startPoint); // push the initial cell into the stack
            startPoint.markInitial(); // mark the initial cell as initial
            startPoint.markInStack(); // mark the initial cell as in stack

            int pathLength = 0; // used as a counter to keep track of the maxPathLength

            // initialize the inertia to keep track how many times the arrow traveled the same direction
            ss.inertia = 0;

            // initialize the direction to -1 so that we know whether the arrow find a neighbor cell, or not
            ss.direction = -1;

            /* while the stack not empty and path length not reach to its maximum
               search for a valid path for the arrow to find a way to the targets */
            while (!s.isEmpty() && pathLength < maxPathLength) {

                MapCell topCell = s.peek(); // peek at the current cell
                MapCell nextCell = ss.nextCell(topCell); // go find the best cell to travel into for the current cell

                // if there is a valid cell
                if (nextCell != null) {
                    nextCell.markInStack(); // mark it as in stack
                    s.push(nextCell); // push into the stack, so we peek at it later
                    pathLength++;  // the arrow travel a cell, so increase the pathLength counter

                    // this cell is the target, so mark the cell as target
                    if (nextCell.isTarget()) {
                        nextCell.markTarget();
                        targets++; // increase the targets counter
                        break;
                    }

                }
                /* not valid neighbor cell to travel into
                   pop it from the stack and mark it as out stack, and increase the path length counter
                */
                else {
                    MapCell poppedCell = s.pop();
                    poppedCell.markOutStack();
                    pathLength++;

                    /* if inertia still -1, that because the arrow did not find its first direction
                       and cannot get to any cell
                    */
                    if (ss.inertia == -1)
                        while (!s.isEmpty())
                            s.pop().markOutStack();
                }
            }

            /* if the arrow hit the target, pathLength reach maxPathLength or cupid is trapped, then
               pop all the element from the stack and mark them as out stack
            */
            while (!s.isEmpty())
                s.pop().markOutStack();
        }

        // print how many targets are hit
        System.out.println("The number of targets were hit : " + targets);
    }

    /**
     * Used to find the best next cell neighboring to the <b>cell</b> param.
     *
     * @param cell the current cell to find its best neighbor cell.
     * @return <b>MapCell</b> the best next cell to travel into.
     */
    public MapCell nextCell(MapCell cell) {
        MapCell bestCell = null; // used to catch the best next cell

        /* after each arrow fired we push the initial cell into the stack, and "push0" will be appended
           to the ArrayStack.sequence, using that we indicate the first move of the arrow and direction
        */
        if (ArrayStack.sequence.endsWith("push0")) {
            for (int i = 0; i < 4; i++) {
                MapCell nextCell = cell.getNeighbour(i);

                // check that cell are exist, not blackhole and not used before
                if (nextCell != null && !nextCell.isMarked() && !nextCell.isBlackHole()) {
                    // check the cross path cell first
                    if (checkCross(cell, nextCell, i)) {
                        bestCell = nextCell;
                        direction = i;
                        break; // break the loop once there is a cross path cell as neighbor
                    }

                    // check the horizontal and vertical path cell
                    if (checkHorizontal(cell, nextCell, i) || checkVertical(cell, nextCell, i)) {
                        bestCell = nextCell;
                        direction = i;
                    }
                }
            }

            return bestCell;
        }

        // no neighbor cell are valid
        if (direction == -1) return null;

        for (int i = 0; i < 4; i++) {

            // try to travel in the same direction first (and increase inertia) if it could
            MapCell directCell = cell.getNeighbour(direction);
            if (directCell != null && !directCell.isBlackHole() && !directCell.isMarked()) {
                if (samePath(cell, direction)) {
                    bestCell = directCell;
                    inertia++;
                    break;
                }
                // not able to travel in the same direction
            }


            MapCell nextCell = cell.getNeighbour(i);

            // check that cell are exist, not blackhole and not used before
            if (nextCell != null && !nextCell.isBlackHole() && !nextCell.isMarked()) {
                // check target cells first
                if (nextCell.isTarget()) {
                    bestCell = nextCell;
                    inertia = 0; // reset inertia
                    break;
                }

                // if not target cell, check cross path cell
                if (checkCross(cell, nextCell, i)) {
                    bestCell = nextCell;
                    direction = i; // keep track of the direction
                    break;
                }

                // if not cross path cell, check horizontal, vertical path cell
                if (checkVertical(cell, nextCell, i) || checkHorizontal(cell, nextCell, i)) {
                    bestCell = nextCell;
                    direction = i; // keep track of the direction
                    break;
                }
            }

            /* reset the inertia to -1, to indicate that arrow did not find its first
               direction and cannot get to any cell
            */
            if (inertia >= 3) {
                inertia = -1;
                return null;
            }
        }

        return bestCell;
    }

    /**
     * Use this method to check if the arrow can travel in the same direction.
     *
     * @param cell      the current cell.
     * @param direction the current direction.
     * @return <b>true</b> if the arrow can continue in the same direction, <b>false</b> otherwise.
     */
    private boolean samePath(MapCell cell, int direction) {
        return checkCross(cell, cell.getNeighbour(direction), direction) ||
                checkHorizontal(cell, cell.getNeighbour(direction), direction) ||
                checkVertical(cell, cell.getNeighbour(direction), direction);
    }

    /**
     * @param cell      the current cell.
     * @param nextCell  the next cell to check its validity.
     * @param direction the next cell direction.
     * @return <b>true</b> if the there is a cross path cell neighboring to the current cell, <b>false</b> otherwise.
     */
    private boolean checkCross(MapCell cell, MapCell nextCell, int direction) {
        // next cell exists
        if (nextCell != null) {
            // current cell is cross path cell or cupid
            if (cell.isCrossPath() || cell.isStart()) {
                if (nextCell.isTarget()) // next cell is target cell
                    return true;

                else if (nextCell.isCrossPath()) // next cell is cross path cell
                    return true;
            }

            // current cell is only cross path cell, not cupid
            if (cell.isCrossPath()) {
                /* check if arrow can go from cross path cell into vertical path cell
                   direction % 2 == 0 (= 0 (up) | 2 (down) */
                if (nextCell.isVerticalPath() && (direction % 2 == 0))
                    return true;

                /* check if arrow can go from cross path cell into horizontal path cell
                   direction % 2 == 1 (= 1 (right) | 3 (left) */
                else if (nextCell.isHorizontalPath() && (direction % 2 == 1))
                    return true;
            }
        }

        return false;
    }

    /**
     * @param cell      the current cell.
     * @param nextCell  the next cell to check its validity.
     * @param direction the next cell direction.
     * @return <b>true</b> if the there is a cross path cell neighboring to the current cell, <b>false</b> otherwise.
     */
    private boolean checkVertical(MapCell cell, MapCell nextCell, int direction) {
        // next cell exists
        if (nextCell != null)
            // current cell is vertical or cupid
            if ((cell.isVerticalPath() || cell.isStart()) && (direction % 2 == 0)) {
                if (nextCell.isTarget()) // next cell is target cell
                    return true;

                else if (nextCell.isCrossPath()) // next cell is cross path cell
                    return true;

                else if (nextCell.isVerticalPath())  // next cell is vertical cell
                    return true;
            }

        return false;
    }

    /**
     * @param cell      the current cell.
     * @param nextCell  the next cell to check its validity.
     * @param direction the next cell direction
     * @return <b>true</b> if the there is a cross path cell neighboring to the current cell, <b>false</b> otherwise.
     */
    private boolean checkHorizontal(MapCell cell, MapCell nextCell, int direction) {
        // next cell exists
        if (nextCell != null)
            // current cell is horizontal or cupid
            if ((cell.isHorizontalPath() || cell.isStart()) && (direction % 2 == 1)) {
                if (nextCell.isTarget()) // this cell is target cell
                    return true;

                else if (nextCell.isCrossPath()) // this cell is cross path cell
                    return true;

                else if (nextCell.isHorizontalPath()) // this cell is horizontal cell
                    return true;
            }

        return false;
    }
}
