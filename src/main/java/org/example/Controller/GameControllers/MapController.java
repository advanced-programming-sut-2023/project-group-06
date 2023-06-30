package org.example.Controller.GameControllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Model.*;
import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.BuildingType;
import org.example.Model.BuildingGroups.Trap;
import org.example.View.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapController {
    public static Game currentGame;
    public static int currentX;
    public static int currentY;

    public static String showMapRow(int x, int y) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 60; i++)
            result.append("-");
        result.append("-\n");
        for (int i = 0; i < 15; i++) {
            String tileColor = currentGame.getTileByCoordinates(y, x - 7 + i).getType().getColorNumber();
            if (currentGame.getTileByCoordinates(y, x - 7 + i).checkForVisibleSoldiers(currentGame.currentPlayer()))
                result.append("|" + "\u001B[").append(tileColor).append("m").append(" S ").append("\u001B[0m");
            else if (currentGame.getTileByCoordinates(y, x - 7 + i).checkForEngineers())
                result.append("|" + "\u001B[").append(tileColor).append("m").append(" E ").append("\u001B[0m");
            else if (currentGame.getTileByCoordinates(y, x - 7 + i).checkForCows())
                result.append("|" + "\u001B[").append(tileColor).append("m").append(" C ").append("\u001B[0m");
            else if (currentGame.getTileByCoordinates(y, x - 7 + i).checkForEquipment())
                result.append("|" + "\u001B[").append(tileColor).append("m").append(" Q ").append("\u001B[0m");
            else if (currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding() != null) {
                if (currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() != BuildingType.TREE &&
                        currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() != BuildingType.ROCK &&
                        !(currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding() instanceof Trap &&
                                currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getOwner() != GameController.currentPlayer &&
                                !((Trap) currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding()).canBeSeenByEnemy())) {
                    if (currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() != BuildingType.WALL)
                        result.append("|" + "\u001B[").append(tileColor).append("m").append(" B ").append("\u001B[0m");
                    else result.append("|" + "\u001B[").append(tileColor).append("m").append(" W ").append("\u001B[0m");
                } else if (currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() == BuildingType.TREE)
                    result.append("|" + "\u001B[").append(tileColor).append("m").append(" T ").append("\u001B[0m");
                else if (currentGame.getTileByCoordinates(y, x - 7 + i).getBuilding().getBuildingType() == BuildingType.ROCK)
                    result.append("|" + "\u001B[").append(tileColor).append("m").append(" R ").append("\u001B[0m");
                else result.append("|" + "\u001B[").append(tileColor).append("m").append("   ").append("\u001B[0m");
            } else if (currentGame.getTileByCoordinates(y, x - 7 + i).isDitch())
                result.append("|" + "\u001B[").append(tileColor).append("m").append(" D ").append("\u001B[0m");
            else result.append("|" + "\u001B[").append(tileColor).append("m").append("   ").append("\u001B[0m");
        }
        result.append("|\n");
        return result.toString();
    }

    public static String showMap(int x, int y) {
        currentX = x;
        currentY = y;
        StringBuilder result = new StringBuilder();
        result.append(showMapRow(x, y - 2));
        result.append(showMapRow(x, y - 1));
        result.append(showMapRow(x, y));
        result.append(showMapRow(x, y + 1));
        result.append(showMapRow(x, y + 2));
        for (int i = 0; i < 60; i++)
            result.append("-");
        result.append("-\n");
        return result.toString();
    }

    public static String moveMap(String input) {
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        String result = "";
        Matcher matcher;
        matcher = Pattern.compile("up").matcher(input);
        while (matcher.find())
            up++;
        matcher = Pattern.compile("down").matcher(input);
        while (matcher.find())
            down++;
        matcher = Pattern.compile("left").matcher(input);
        while (matcher.find())
            left++;
        matcher = Pattern.compile("right").matcher(input);
        while (matcher.find())
            right++;
        matcher = Pattern.compile("up (?<up>\\d+)").matcher(input);
        while (matcher.find())
            up += Integer.parseInt(matcher.group("up")) - 1;
        matcher = Pattern.compile("down (?<down>\\d+)").matcher(input);
        while (matcher.find())
            down += Integer.parseInt(matcher.group("down")) - 1;
        matcher = Pattern.compile("left (?<left>\\d+)").matcher(input);
        while (matcher.find())
            left += Integer.parseInt(matcher.group("left")) - 1;
        matcher = Pattern.compile("right (?<right>\\d+)").matcher(input);
        while (matcher.find())
            right += Integer.parseInt(matcher.group("right")) - 1;

        int newX = currentX + right - left;
        int newY = currentY + down - up;
        if (newX < 7 || newX > GameController.currentGame.getMapWidth() - 8 || newY < 2 || newY > GameController.currentGame.getMapHeight() - 3)
            result += "Can't show the map outside the boundaries\n";
        else {
            currentX = newX;
            currentY = newY;
            result += showMap(currentX, currentY);
        }
        return result;
    }

    public static String showDetails(Matcher matcher) {
        String xString = matcher.group("x");
        String yString = matcher.group("y");
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if (x < 0 || x >= currentGame.getMapWidth() || y < 0 || y >= currentGame.getMapHeight())
            return "Invalid coordinates\n";
        String result = "";
        result += "ground structure: " + currentGame.getTileByCoordinates(y, x).getType().toString().toLowerCase() + "\n";
        result += "-soldiers:" + '\n';
        for (Soldier soldier : currentGame.getTileByCoordinates(y, x).getSoldiers()) {
            if (!(soldier.getUnitType() == UnitType.ASSASSIN && soldier.getOwner() != currentGame.currentPlayer()
                    && GameController.findNearestEnemyTo(soldier, 4) == null))
                result += soldier.toString() + '\n';
        }
        result += "-engineers:\n";
        for (Unit unit : currentGame.getTileByCoordinates(y, x).getAllUnits()) {
            if (unit.getUnitType() == UnitType.ENGINEER)
                result += unit.toString() + '\n';
        }
        result += "-equipments:\n";
        for (Unit unit : currentGame.getTileByCoordinates(y, x).getAllUnits()) {
            if (unit instanceof Equipment)
                result += unit.toString() + '\n';
        }
        String buildingString = "empty\n";
        boolean tree = false;
        if (currentGame.getTileByCoordinates(y, x).getBuilding() != null) {
            if (currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() != BuildingType.TREE &&
                    !(currentGame.getTileByCoordinates(y, x).getBuilding() instanceof Trap &&
                            currentGame.getTileByCoordinates(y, x).getBuilding().getOwner() != GameController.currentPlayer &&
                            !((Trap) currentGame.getTileByCoordinates(y, x).getBuilding()).canBeSeenByEnemy()))
                buildingString = currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType().toString().toLowerCase() + "\n";
            else if (currentGame.getTileByCoordinates(y, x).getBuilding().getBuildingType() == BuildingType.TREE)
                tree = true;
        }
        result += "-buildings: " + buildingString;
        if (tree) {
            result += "there is a tree here!\n";
            result += "resources: wood\n";
        } else result += "resources: " + currentGame.getTileByCoordinates(y, x).getType().getResource() + "\n";
        return result;
    }

    public static Response saveMap(String name) {
        currentGame = GameController.currentGame;
        System.out.println(name);
        System.out.println(currentGame.getMap() == null);
        Data.saveMap(name, currentGame.getMap());
        return Response.SAVE_MAP_SUCCESSFUL;
    }

    public static void mapGraphicProcessor(Canvas canvas, Tile[][] map, int x, int y) {
        int mapHeight = map.length;
        int mapWidth = map[0].length;
        int canvasHeight = (int) canvas.getHeight();
        int canvasWidth = (int) canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        int sStart = Math.min(mapWidth + mapHeight - 2, y / 23 + 5);
        int sEnd = Math.max(0, (y - canvasHeight - 100) / 23);
        for (int s = sStart; s >= sEnd; s--) {
            int iStart = Math.max(0, (s - 1 - x / 46) / 2);
            iStart = Math.max(iStart, s + 1 - mapWidth);
            int iEnd = Math.min(mapHeight - 1, (s + 1 + (canvasWidth - x) / 46) / 2 + 5);
            iEnd = Math.min(iEnd, s);
            for (int i = iStart; i <= iEnd; i++) {
                int j = s - i;
                Building building = map[i][j].getBuilding();
                if (building == null || building.getXCoordinate() == j && building.getYCoordinate() == i)
                    drawBuildingAndFriends(gc, map, i, j, x, y);
                // drawTileAt(gc, map[i][j], x + (i - j) * 46, y - s * 23);
            }
        }
    }

    private static void drawBuildingAndFriends(GraphicsContext gc, Tile[][] map, int i, int j, int x, int y) {
        Building building = map[i][j].getBuilding();
        int graphicalHeight = graphicalHeightOf(building);
        if (building == null) {
            drawTileAt(gc, map[i][j], x + (i - j) * 46, y - (i + j) * 23);
            drawUnitsOfTile(gc, map, i, j, x, y, graphicalHeight);
        } else {
            int size = building.getBuildingType().getSize();
            for (int ii = i - (size / 2); ii <= i + size / 2; ii++)
                for (int jj = j - (size / 2); jj <= j + size / 2; jj++)
                    drawTileAt(gc, map[ii][jj], x + (ii - jj) * 46, y - (ii + jj) * 23);
            drawBuildingAt(gc, building, x + (i - j) * 46, y - (i + j) * 23);
            for (int ii = i - (size / 2); ii <= i + size / 2; ii++)
                for (int jj = j - (size / 2); jj <= j + size / 2; jj++)
                    drawUnitsOfTile(gc, map, ii, jj, x, y, graphicalHeight);
        }
        // soldiers todo
    }

    private static void drawUnitsOfTile(GraphicsContext gc, Tile[][] map, int i, int j, int x, int y, int h) {
        int centerX = x + (i - j) * 46;
        int centerY = y - (i + j) * 23 - h;
        for (int k = 0; k < 4 && k < map[i][j].getAllUnits().size(); k++) {
            int dx = k == 1 ? -23 : k == 2 ? 23 : 0;
            int dy = k == 0 ? -12 : k == 3 ? 12 : 0;
            Unit unit = map[i][j].getAllUnits().get(k);
            drawUnitAt(gc, unit, centerX + dx, centerY + dy);
        }
    }

    private static void drawUnitAt(GraphicsContext gc, Unit unit, int x, int y) {
        if (unit == null) return;
        gc.drawImage(unit.getImg().getImage(), x - unit.getImg().getXo(), y - unit.getImg().getYo());
    }

    private static int graphicalHeightOf(Building building) {
        if (building == null) return 0;
        return building.getImg().getYo() - 23 * building.getBuildingType().getSize();
    }

    private static void drawBuildingAt(GraphicsContext gc, Building building, int x, int y) {
        if (building == null) return;
        gc.drawImage(building.getImg().getImage(), x - building.getImg().getXo(), y - building.getImg().getYo());
    }

    private static void drawTileAt(GraphicsContext gc, Tile tile, int x, int y) {
        if (tile == null) return;
        gc.drawImage(tile.getImg().getImage(), x - tile.getImg().getXo(), y - tile.getImg().getYo());
    }
}
