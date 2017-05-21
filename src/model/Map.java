package model;

import java.util.ArrayList;

/**
 * Created by gy0p4k on 5/21/2017.
 */
public class Map {
  private ArrayList<ArrayList<Cell>> cells;
  private int row;
  private int col;

  public ArrayList<ArrayList<Cell>> getCells() {
    return cells;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Map(int row, int col, double probability){
    this.row = row;
    this.col = col;
    cells = new ArrayList<ArrayList<Cell>>();
    ArrayList<Cell> temp;
    for (int i = 0; i < col ; i++) {
      temp = new ArrayList<>();
      for (int j = 0; j < row ; j++) {
        temp.add(new Cell(i, j, Math.random() < probability));
      }
      cells.add(temp);
    }
  }

  public boolean isAlive(int x, int y ){
    return cells.get(y).get(x).isAlive();
  }

  public void manageAll(boolean live){
    for(ArrayList<Cell> tempList : cells){
      for(Cell tempCell : tempList ){
        tempCell.setAlive(live);
      }
    }
  }

  public int neighbourAliveCount(int x, int y){
    int counter = 0;
    for (int i = x-1; i < x+2 ; i++) {
      for (int j = y-1; j < y+2; j++) {
        try {
          if (i != j && isAlive(i, j)) counter++;
        }catch(IndexOutOfBoundsException e){};
      }

    }
    return counter;
  }

  public void nextLifeCycle(){
    ArrayList<Cell> survivingCells = new ArrayList<>();
    for(ArrayList<Cell> tempList : cells){
      for(Cell tempCell : tempList){
        int a = neighbourAliveCount(tempCell.getX(),tempCell.getY());
        if(tempCell.isAlive() ){
          if(a == 2 || a == 3) {
            survivingCells.add(tempCell);
          }
        }else{
          if(a == 3){
            survivingCells.add(tempCell);
          }
        }
      }
    }

    manageAll(false);
    for(Cell temp : survivingCells){
      cells.get(temp.getY()).get(temp.getX()).setAlive(true);
    }
  }

}
