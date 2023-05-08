package org.example.Controller;

import org.example.Model.BuildingGroups.Building;
import org.example.Model.BuildingGroups.Towers;
import org.example.Model.Tile;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class PathFinder {
    private Tile[][] map;
    private int height;
    private int width;
    /*
         0
       3 x 1
         2
    haveEdgeWith(i) : graph[x.y][x.x] & (1 << i) != 0
    */
    private int[][] graph0; // Normal pathfinder without climbing lathers
    private int[][] graph1; // Pathfinder with climbing lathers
    private int[][] graph2; // Super pathfinder with ignoring walls
    private boolean[][] startFound;
    private boolean[][] endFound;
    private int[][] father;

    public PathFinder(Tile[][] map) {
        this.map = map;
        height = map.length;
        width = map[0].length;
        graph0 = new int[height][width];
        graph1 = new int[height][width];
        graph2 = new int[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int f0 = 0, f1 = 0, f2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (i == 0 && k == 0 || i == height - 1 && k == 2 ||
                            j == 0 && k == 3 || j == width - 1 && k == 1) continue;
                    int ii = i + (k == 0 ? -1 : k == 2 ? 1 : 0);
                    int jj = j + (k == 1 ? 1 : k == 3 ? -1 : 0);
                    // Tiles are connected if their height difference is less than 2
                    if (Math.abs(map[i][j].getHeight() - map[ii][jj].getHeight()) < 2) {
                        f0 |= (1 << k);
                        f1 |= (1 << k);
                        f2 |= (1 << k);
                        continue;
                    }
                    Building b = map[i][j].getBuilding();
                    if (b != null && b instanceof Towers) {
                        f2 |= (1 << k);
                        if ((((Towers) b).lather & (1 << k)) != 0) f1 |= (1 << k);
                    }
                    b = map[ii][jj].getBuilding();
                    if (b != null && b instanceof Towers) {
                        f2 |= (1 << k);
                        if ((((Towers) b).lather & (1 << ((k + 2) % 4))) != 0) f1 |= (1 << k);
                    }
                }
                graph0[i][j] = f0;
                graph1[i][j] = f1;
                graph2[i][j] = f2;
            }
    }

    private void resetParent() {
        startFound = new boolean[height][width];
        endFound = new boolean[height][width];
        father = new int[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                startFound[i][j] = false;
                endFound[i][j] = false;
                father[i][j] = -1;
            }
    }

    public Deque<Tile> findPath(Tile start, Tile end) {
        return findPath(start, end, 0);
    }

    public Deque<Tile> findPath(Tile start, Tile end, int mode) {
        int[][] graph = mode == 0 ? graph0 : mode == 1 ? graph1 : graph2;
        resetParent();
        if (start == end) {
            Deque<Tile> dq = new ArrayDeque<>();
            dq.add(start);
            return dq;
        }
        if (end.getY() * width + end.getX() < start.getY() * width + end.getX())
            return reversePath(findPath(end, start));
        int si = start.getY();
        int sj = start.getX();
        int ei = end.getY();
        int ej = end.getX();
        // bfs from both start and end
        Queue<Integer> BFSFromStart = new ArrayDeque<>();
        Queue<Integer> BFSFromEnd = new ArrayDeque<>();
        BFSFromStart.add(si * width + sj);
        BFSFromEnd.add(ei * width + ej);
        startFound[si][sj] = true;
        endFound[ei][ej] = true;
        while (!BFSFromStart.isEmpty() && !BFSFromEnd.isEmpty()) {
            Deque<Tile> path1 = getTiles(start, end, BFSFromStart, endFound, startFound, graph);
            if (path1 != null) return path1;
            Deque<Tile> path2 = getTiles(end, start, BFSFromEnd, startFound, endFound, graph);
            if (path2 != null) return reversePath(path2);
        }
        return null;
    }

    private Deque<Tile> reversePath(Deque<Tile> path) {
        Deque<Tile> reversedPath = new ArrayDeque<>();
        if (path == null) return null;
        while (!path.isEmpty()) reversedPath.addFirst(path.pollFirst());
        return reversedPath;
    }

    private Deque<Tile> getTiles(Tile start, Tile end, Queue<Integer> BFSFromStart, boolean endFound[][], boolean startFound[][], int graph[][]) {
        int cur = BFSFromStart.poll();
        int i = cur / width;
        int j = cur % width;
        for (int k = 0; k < 4; k++) {
            if ((graph[i][j] & (1 << k)) == 0) continue;
            int ii = i + (k == 0 ? -1 : k == 2 ? 1 : 0);
            int jj = j + (k == 1 ? 1 : k == 3 ? -1 : 0);
            if (endFound[ii][jj]) {
                Deque<Tile> path = new ArrayDeque<>();
                while (father[i][j] != -1) {
                    path.addFirst(map[i][j]);
                    int tmp = father[i][j];
                    i = tmp / width;
                    j = tmp % width;
                }
                path.addFirst(start);
                while (father[ii][jj] != -1) {
                    path.addLast(map[ii][jj]);
                    int tmp = father[ii][jj];
                    ii = tmp / width;
                    jj = tmp % width;
                }
                path.addLast(end);
                return path;
            }
            if (!startFound[ii][jj]) {
                startFound[ii][jj] = true;
                father[ii][jj] = i * width + j;
                BFSFromStart.add(ii * width + jj);
            }
        }
        return null;
    }
}