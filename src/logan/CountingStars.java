package logan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CountingStars {
	static int rowLength = 0;
	static int colLength = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		// long start = System.currentTimeMillis();
		int co = 0;
		while (flag) {
			String count = inp.readLine();
			if (count == null) {
				flag = false;
				inp.close();
			} else if (!count.isEmpty()) {
				String[] split = count.split("\\s+");
				rowLength = Integer.valueOf(split[0]);
				colLength = Integer.valueOf(split[1]);
				co++;
				Map<Integer, Node> indexList = new LinkedHashMap<>();
				int count1 = 1;
				char[][] arrayChar = new char[rowLength][colLength];
				List<Integer> openList = new ArrayList<>();
				for (int i = 0; i < rowLength; i++) {
					String s = inp.readLine();
					char[] ch = s.toCharArray();
					for (int j = 0; j < colLength; j++) {
						char c = ch[j];
						Node index = new Node();
						index.index = count1;
						index.rowX = i;
						index.columnY = j;
						indexList.put(count1, index);
						arrayChar[i][j] = c;
						if (c == 45) {
							openList.add(index.index);
						}
						count1 = count1 + 1;

					}
				}
				int result = findingStart(arrayChar, indexList, openList);
				System.out.println("Case " + co + ": " + result);
			} else {
				flag = false;
				inp.close();
			}
			rowLength = 0;
			colLength = 0;
		}
		// System.out.println("Total: " + (System.currentTimeMillis() - start)/1000F);
	}

	static int findingStart(char[][] arrayChar, Map<Integer, Node> indexList, List<Integer> openlist) {
		int result = 0;
		while (!openlist.isEmpty()) {
			result++;
			int currentIndex = openlist.get(0);
			Node currentNode = indexList.get(currentIndex);
			openlist.remove(0);
			List<Node> neighbor = addNeibour(currentNode, arrayChar, indexList);
			if (neighbor.isEmpty()) {
				continue;
			}
			loopNeighor(neighbor, arrayChar, indexList, openlist);
		}
		return result;
	}

	static boolean loopNeighor(List<Node> neighbor, char[][] arrayChar, Map<Integer, Node> indexList,
			List<Integer> openList) {
		for (Node node : neighbor) {
			Integer value = Integer.valueOf(node.index);
			if (openList.contains(value)) {
				openList.remove(value);
				List<Node> newNeighbor = addNeibour(node, arrayChar, indexList);
				if (newNeighbor.isEmpty()) {
					continue;
				}
				loopNeighor(newNeighbor, arrayChar, indexList, openList);
			}
		}
		return true;
	}

	static List<Node> addNeibour(Node currentNode, char[][] arrayChar, Map<Integer, Node> indexList) {
		List<Node> list = new ArrayList<>();
		int x = currentNode.rowX;
		int y = currentNode.columnY;
		int index = currentNode.index;
		moveTop(list, arrayChar, indexList, index, x, y);
		moveRight(list, arrayChar, indexList, index, x, y);
		moveBottom(list, arrayChar, indexList, index, x, y);
		moveLeft(list, arrayChar, indexList, index, x, y);
		return list;
	}

	static void moveTop(List<Node> neighbor, char[][] arrayChar, Map<Integer, Node> indexList, int index, int x,
			int y) {
		x = x - 1;
		if (x >= 0 && arrayChar[x][y] == 45) {
			neighbor.add(indexList.get(index - colLength));
		}
	}

	static void moveRight(List<Node> neighbor, char[][] arrayChar, Map<Integer, Node> indexList, int index, int x,
			int y) {
		y = y + 1;
		if (y < colLength && arrayChar[x][y] == 45) {
			neighbor.add(indexList.get(index + 1));
		}
	}

	static void moveLeft(List<Node> neighbor, char[][] arrayChar, Map<Integer, Node> indexList, int index, int x,
			int y) {
		y = y - 1;
		if (y >= 0 && arrayChar[x][y] == 45) {
			neighbor.add(indexList.get(index - 1));
		}
	}

	static void moveBottom(List<Node> neighbor, char[][] arrayChar, Map<Integer, Node> indexList, int index, int x,
			int y) {
		x = x + 1;
		if (x < rowLength && arrayChar[x][y] == 45) {
			neighbor.add(indexList.get(index + colLength));
		}
	}

	static class Node {
		int index;
		int rowX;
		int columnY;

		public Node() {

		}
	}
}
