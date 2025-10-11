package Lab7;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public class PathingMain extends PApplet {
   private List<PImage> imgs;
   private int current_image;
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private List<Point> path;

   private static final int TILE_SIZE = 32;

   private static final int ANIMATION_TIME = 100;

   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;

   private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, SEARCHED };

   private Point wPos;

   private boolean drawPath = false;

	public void settings() {
      size(640,480);
	}
	
	/* runs once to set up world */
   public void setup()    {

      path = new LinkedList<>();
      wPos = new Point(2, 2);
      imgs = new ArrayList<>();
      imgs.add(loadImage("images/wyvern1.bmp"));
      imgs.add(loadImage("images/wyvern2.bmp"));
      imgs.add(loadImage("images/wyvern3.bmp"));

      background = loadImage("images/grass.bmp");
      obstacle = loadImage("images/vein.bmp");
      goal = loadImage("images/water.bmp");

      grid = new GridValues[ROWS][COLS];
      initialize_grid(grid);

      current_image = 0;
      next_time = System.currentTimeMillis() + ANIMATION_TIME;
      noLoop();
      draw();
   }

	/* set up a 2D grid to represent the world */
   private static void initialize_grid(GridValues[][] grid)    {
      // Sets up the 20 column x 15 row Grid and use this to store background tiles, obstacle tiles, and a goal tile
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)      {
            grid[row][col] = GridValues.BACKGROUND;
         }
      }

		//set up some obstacles
      // 2, 8
      for (int row = 2; row < 8; row++)      {
         grid[row][row + 5] = GridValues.OBSTACLE;
      }

      // 8, 12
      for (int row = 8; row < 12; row++)       {
         grid[row][19 - row] = GridValues.OBSTACLE;
      }

      // 1, 8
      for (int col = 1; col < 8; col++)       {
         grid[11][col] = GridValues.OBSTACLE;
      }

      // Default goal is 13, 14
      //grid[13][14] = GridValues.GOAL;
      //grid[1][10] = GridValues.GOAL;
      //grid[8][17] = GridValues.GOAL;
      grid[10][10] = GridValues.GOAL;

   }

   private void next_image()    {
      current_image = (current_image + 1) % imgs.size();
   }

	/* runs over and over by processing and will contain the control logic of the sketch*/
   public void draw()    {
      // A simplified action scheduling handler
      long time = System.currentTimeMillis();
      if (time >= next_time)       {
         next_image();
         next_time = time + ANIMATION_TIME;
      }

      draw_grid();
      draw_path();

      image(imgs.get(current_image), wPos.x * TILE_SIZE, wPos.y * TILE_SIZE);
   }

   private void draw_grid()    {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            draw_tile(row, col);
         }
      }
   }

   private void draw_path()    {
      if (drawPath)       {
         for (Point p : path)          {
            fill(128, 0, 0);
            rect(p.x * TILE_SIZE + TILE_SIZE * 3 / 8,
               p.y * TILE_SIZE + TILE_SIZE * 3 / 8,
               TILE_SIZE / 4, TILE_SIZE / 4);
         }
      }
   }

   private void draw_tile(int row, int col)    {
      switch (grid[row][col])       {
         case BACKGROUND:
            image(background, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case OBSTACLE:
            image(obstacle, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case SEARCHED:
            fill(0, 128);
            rect(col * TILE_SIZE + TILE_SIZE / 4,
               row * TILE_SIZE + TILE_SIZE / 4,
               TILE_SIZE / 2, TILE_SIZE / 2);
            break;
         case GOAL:
            image(goal, col * TILE_SIZE, row * TILE_SIZE);
            break;
      }
   }

   ///  Need this method to reset all SEARCHED cells back to BACKGROUND before starting a new DFS
   private void resetSearched(GridValues[][] grid){
      for(int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            if (grid[row][col] == GridValues.SEARCHED){
               grid[row][col] = GridValues.BACKGROUND;
            }
         }
      }
   }


   public static void main(String args[])    {
      PApplet.main("Lab7.PathingMain");
   }

   public void keyPressed()    {
      if (key == ' ')       {
			//clear out prior path
         path.clear();
			//example - replace with dfs	
         //moveOnce(wPos, grid, path);

         // Reset all searched cells before running dfs
         resetSearched(grid);

         DFS(wPos, grid, path);
         redraw();
      }
      else if (key == 'p')       {
         drawPath ^= true;
         redraw();
      }
   }

	/* replace the below with a depth first search 
		this code provided only as an example of moving in
		in one direction for one tile - it mostly is for illustrating
		how you might test the occupancy grid and add nodes to path!
	*/
   private boolean moveOnce(Point pos, GridValues[][] grid, List<Point> path)    {
      try {
         Thread.sleep(200);
      } catch (Exception e) {}
      redraw();

      Point rightN = new Point(pos.x +1, pos.y );
     
		//test if this is a valid grid cell 
		if (withinBounds(rightN, grid)  &&
         grid[rightN.y][rightN.x] != GridValues.OBSTACLE &&
         grid[rightN.y][rightN.x] != GridValues.SEARCHED)
        {
			//check if my right neighbor is the goal
      	    if (grid[rightN.y][rightN.x] == GridValues.GOAL) {
         	   path.add(0, rightN);
         	   return true;
      	    }

			//set this value as searched
      	    grid[rightN.y][rightN.x] = GridValues.SEARCHED;
         }
        return false;
   }


   private boolean DFS(Point pos, GridValues[][] grid, List<Point> path) {
   /// Implement DFS with a stack, empty visited (SEARCHED) list.
   /// Push nodes to stack and once you check it with ops pop it then push children of it to the stack and repeat

      /// Create a stack and push the starting point WPos
      Stack<Point> stack = new Stack<>();
      stack.push(pos);

      /// HashMap that maps each tile (point) to its previous tiles
      /// In order to retrace path once a goal has been found by walking backwards from the goal using the map
      HashMap<Point, Point> retrace = new HashMap<>();

      int visitedCount = 0;

      /// While the stack is not empty
      while(!(stack.isEmpty()))
      {
         /// Pop the current point
         Point current = stack.pop();

         /// Check if Current Point is out of bounds or an obstacle or already searched
         if (!withinBounds(current, grid) ||
              grid[current.y][current.x] == GridValues.OBSTACLE ||
              grid[current.y][current.x] == GridValues.SEARCHED) {
            continue;
         }

         System.out.println("Visited: (" + current.x + ", " + current.y + ")");

            /// Check if current the goal
         if (grid[current.y][current.x] == GridValues.GOAL)
         {
            /// If so build a path list and return
            /// While current is not the starting position because we want to retrace back to it
            /// From goal to start, walk backward using retrace map
            Point step = current;
            while(!step.equals(pos)) {
               path.add(0, step);
               step = retrace.get(step);
            }
            /// Then add the starting point at the end
            path.add(0, pos);

            System.out.print("Goal found: visited tiles: " + visitedCount);
            return true;
         }

         /// Otherwise mark Current Point as searched
         grid[current.y][current.x] = GridValues.SEARCHED;
         visitedCount++;

         /// Then check if neighbors (right down left up) are valid
         Point[] neighbor = new Point[]{
                 new Point(current.x, current.y - 1), /// Up
                 new Point(current.x - 1, current.y), /// Left
                 new Point(current.x, current.y+1),   /// Down
                 new Point(current.x+1, current.y)    /// Right will be the last to be pushed to the stack because we want to prioritize this
         };

         for (Point n : neighbor) {
            /// Also check if it's in retrace which means it's already been seen or blocked
            if (withinBounds(n, grid) &&
                    grid[n.y][n.x] != GridValues.OBSTACLE &&
                    grid[n.y][n.x] != GridValues.SEARCHED &&
                    !retrace.containsKey(n)) {
               /// Push its neighbors onto the stack
               System.out.println("Push neighbor: (" + n.x + ", " + n.y + ")");
               stack.push(n);
               retrace.put(n, current);
            }
         }
      }
      /// If out of bounds, obstacle, or searched, skip it, move onto next iteration of while loop
      return false;
   }

   private static boolean withinBounds(Point p, GridValues[][] grid)    {
      return p.y >= 0 && p.y < grid.length &&
         p.x >= 0 && p.x < grid[0].length;
   }
}
