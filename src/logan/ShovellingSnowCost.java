package logan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ShovellingSnowCost {
	  static int rowLength = 0;
	    static int colLength = 0;
	    static char DOT = 46;
	    static boolean IsDotExisted = false;

	    public static void main(String[] args) throws IOException {
	        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
	        boolean flag = true;
	        //long start = System.currentTimeMillis();
	        while (flag) {
	            IsDotExisted = false;
	            String count = inp.readLine();
	            if (!count.isEmpty()) {
	                String[] split = count.split("\\s+");
	                colLength = Integer.valueOf(split[0]);
	                rowLength = Integer.valueOf(split[1]);
	                char[][] arrayChar = new char[rowLength][colLength];
	                if (rowLength == 0 && colLength == 0) {
	                    flag = false;
	                    inp.close();
	                } else {
	                    List<Node> list = new LinkedList<Node>();
	                    int count1 = 1;
	                    /*LinkedList<Node> indexList = new LinkedList<>();*/
	                    Map<Integer, Node> indexList = new LinkedHashMap<>();
	                    StringBuilder builder = new StringBuilder();
	                    for (int i = 0; i < rowLength; i++) {
	                        String s = inp.readLine();
	                        char[] ch = s.toCharArray();
	                        for (int j = 0; j < colLength; j++) {
	                            Node index = new Node();
	                            char c = ch[j];
	                            index.setIndex(count1);
	                            index.setRowX(i);
	                            index.setColumnY(j);
	                            indexList.put(count1, index);
	                            String key = null;
	                            arrayChar[i][j] = c;
	                            if (c == 66) {
	                                key = "B";
	                                builder.append(key);
	                                index.setName(key);
	                                list.add(index);
	                            } else if (c == 67) {
	                                key = "C";
	                                builder.append(key);
	                                index.setName(key);
	                                list.add(index);
	                            } else if (c == 68) {
	                                key = "D";
	                                builder.append(key);
	                                index.setName(key);
	                                list.add(index);
	                            } else if (c == 65) {
	                                key = "A";
	                                builder.append(key);
	                                index.setName(key);
	                                list.add(index);
	                            }
	                            count1 = count1 + 1;
	                        }
	                    }
	                    /*int total = 0;
	                    List<Integer> result = new ArrayList<>();
	                    for (Node node : list) {
	                        Map<String, Double> heuristic  = sortHeuristic(list, node);
	                        total = 0;
	                        char[][] cloneArray = cloneChar(arrayChar);
	                        int countEntry = 0;
	                        String start = node.getName();
	                        for (Entry<String, Double> entry : heuristic.entrySet()) {
	                            String[] key = entry.getKey().split("-");
	                            String startNode = start;
	                            String goalNode = key[1];
	                            String nextKey = (new ArrayList<String>(heuristic.keySet()))
	                                    .get(countEntry < heuristic.size() - 1 ? ++countEntry : countEntry);
	                            Node nextGoalNode = getNode(list, nextKey.split("-")[1]);
	                            //System.out.println(startNode + " - " + goalNode);
	                            int value = aStarSearchClone(getNode(list, startNode), getNode(list, goalNode), cloneArray,
	                                    indexList, list, nextGoalNode);
	                            total += value;
	                        }
	                        result.add(total);
	                    }
	                    System.out.println(Collections.min(result));*/
	                    

	                    List<Integer> total = new ArrayList<>();
	                    /*Map<String, Double> heuristic = mappingTravel(list, builder.toString());

	                    for (Entry<String, Double> entry : heuristic.entrySet()) {
	                        String str = entry.getKey();
	                        int length = str.length();
	                        int totalCount = 0;
	                        char[][] cloneArray = cloneChar(arrayChar);
	                    
	                        for (int i = 0; i < length - 1 ; i++) {
	                            Node startNo = getNode(list, String.valueOf(str.charAt(i)));
	                            Node goalNo = getNode(list, String.valueOf(str.charAt(i + 1)));
	                            int value = aStarSearchClone(startNo, goalNo, cloneArray, indexList, list);
	                            totalCount += value;
	                        }
	                        total.add(totalCount);
	                        for (Node node: list) {
	                            node.setVisted(false);
	                        }
	                    }*/
	                    ShovellingSnowCost snow = new ShovellingSnowCost();
	                    Set<String> heuristic = snow.mappingTravel1(list, builder.toString());
	                    int length = builder.length() - 1;
	                    for (String str : heuristic) {
	                        int totalCount = 0;
	                        char[][] cloneArray = snow.cloneChar(arrayChar);
	                        for (int i = 0; i < length; i++) {
	                            Node startNo = snow.getNode(list, String.valueOf(str.charAt(i)));
	                            Node goalNo = snow.getNode(list, String.valueOf(str.charAt(i + 1)));
	                            int value = snow.aStarSearchClone(startNo, goalNo, cloneArray, indexList, list);
	                            totalCount += value;
	                        }
	                        total.add(totalCount);
	                        for (Node node: list) {
	                            node.setVisted(false);
	                        }
	                    }
	                    System.out.println(Collections.min(total));
	    
	                }
	            }
	            rowLength = 0;
	            colLength = 0;
	        }
	        //System.out.println("Total: " + (System.currentTimeMillis() - start) / 1000F);
	    }
	    
	    public Set<String> mappingTravel1(List<Node> list, String str) {
	        char[] ch = str.toCharArray();
	        int chLength = str.length();
	        Set<String> set = new LinkedHashSet<>();
	        for (int i = 0; i < chLength; i++) {
	            char startCh = ch[i];// first is A
	            for (int j = 0; j < chLength; j++) {
	                char second = ch[j];// Second is B and more....
	                char[] a = new char[2];
	                if (startCh != second) {
	                    int count = 0;
	                    for (int k = 0; k < chLength; k++) {
	                        if(count==2) {
	                            break;
	                        }
	                        char cha = ch[k];
	                        if (cha != startCh && cha != second) {
	                            a[count] = cha;
	                            count++;
	                        }
	                    }
	                    set.add(startCh + "" + second + "" + a[0] + "" + a[1]);
	                    set.add(startCh + "" + second + "" + a[1] + "" + a[0]);
	                }
	            }
	        }
	        return set;
	    }

	    public Map<String, Double> mappingTravel(List<Node> list, String str) {
	        char[] ch = str.toCharArray();
	        Set<String> set = new LinkedHashSet<>();
	        Map<String, Double> heuristic = new LinkedHashMap<>();
	        Map<String, Double> result = new LinkedHashMap<>();
	        for (int i = 0; i < ch.length; i++) {
	            double total = 0;
	            char startCh = ch[i];// first is A
	            for (int j = 0; j < ch.length; j++) {
	                char second = ch[j];// Second is B and more....
	                char[] a = new char[2];
	                if (startCh != second) {
	                    total += calculatHValue(getNode(list, String.valueOf(startCh)),
	                            getNode(list, String.valueOf(second)));
	                    int count = 0;
	                    for (int k = 0; k < ch.length; k++) {
	                        char cha = ch[k];
	                        if (cha != startCh && cha != second) {
	                            a[count] = cha;
	                            count++;
	                        }
	                    }
	                    total += calculatHValue(getNode(list, String.valueOf(second)), getNode(list, String.valueOf(a[0])));
	                    total += calculatHValue(getNode(list, String.valueOf(a[0])), getNode(list, String.valueOf(a[1])));
	                    total += calculatHValue(getNode(list, String.valueOf(a[1])), getNode(list, String.valueOf(startCh)));
	                    set.add(startCh + "" + second + "" + a[0] + "" + a[1]);
	                    heuristic.put(startCh + "" + second + "" + a[0] + "" + a[1], total);
	                    total = 0;
	                    total += calculatHValue(getNode(list, String.valueOf(startCh)),
	                            getNode(list, String.valueOf(second)));
	                    total += calculatHValue(getNode(list, String.valueOf(second)), getNode(list, String.valueOf(a[1])));
	                    total += calculatHValue(getNode(list, String.valueOf(a[1])), getNode(list, String.valueOf(a[0])));
	                    total += calculatHValue(getNode(list, String.valueOf(a[0])), getNode(list, String.valueOf(startCh)));
	                    heuristic.put(startCh + "" + second + "" + a[1] + "" + a[0], total);
	                    set.add(startCh + "" + second + "" + a[1] + "" + a[0]);
	                }
	            }
	        }
	        List<Entry<String, Double>> arrayList = new ArrayList<>(heuristic.entrySet());
	        arrayList.sort(new Comparator<Entry<String, Double>>() {
	            @Override
	            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
	                return Double.compare(o1.getValue(), o2.getValue());
	            }
	        });
	        Entry<String, Double> key = arrayList.get(0);
	        for (Entry<String, Double> entry : arrayList) {
	            Double keyIn = key.getValue();
	            Double en = entry.getValue();
	            if (Double.compare(keyIn, en) == 0) {
	                result.put(entry.getKey(), entry.getValue());
	            } else {
	                break;
	            }
	        }
	        return result;
	    }

	    public Map<String, Double> sortHeuristic(List<Node> listNode, Node startNode) {
	        Map<String, Double> heuristicList = new LinkedHashMap<>();
	        Map<String, Double> result = new LinkedHashMap<>();
	        for (Node node : listNode) {
	            if (startNode.getIndex() != node.getIndex()) {
	                String key = startNode.getName() + "-" + node.getName();
	                double heuristic = calculatHValue(startNode, node);
	                heuristicList.put(key, heuristic);
	            }
	        }
	        List<Entry<String, Double>> arrayList = new ArrayList<>(heuristicList.entrySet());
	        arrayList.sort(new Comparator<Entry<String, Double>>() {
	            @Override
	            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
	                return Double.compare(o1.getValue(), o2.getValue());
	            }
	        });
	        String key = "";
	        for (Entry<String, Double> entry : arrayList) {
	            key = entry.getKey();
	            result.put(key, entry.getValue());
	            break;
	        }
	        
	        heuristicList = new LinkedHashMap<>();
	        for (Node node : listNode) {
	            String key1 = key.split("-")[1];
	            if (!key1.equals(node.getName())  && !node.getName().equals(startNode.getName())) {
	                String key2 = key1 + "-" + node.getName();
	                double heuristic = calculateHValueWithElucine(getNode(listNode, key1), node);
	                heuristicList.put(key2, heuristic);
	            }
	        }
	        
	        arrayList = new ArrayList<>(heuristicList.entrySet());
	        arrayList.sort(new Comparator<Entry<String, Double>>() {
	            @Override
	            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
	                return Double.compare(o1.getValue(), o2.getValue());
	            }
	        });
	        
	        for (Entry<String, Double> entry : arrayList) {
	            key = entry.getKey();
	            result.put(key, entry.getValue());
	            
	        }
	        return result;
	    }
	    
	    public Node getNode(List<Node> list, String nodeName) {
	        for (Node node : list) {
	            if (nodeName.equals(node.getName())) {
	                return node;
	            }
	        }
	        return null;
	    }

	    public void moveDotInTopLinkedList(List<Node> openList, char[][] arrayChar) {
	        Collections.sort(openList, new Comparator<Node>() {
	            @Override
	            public int compare(Node c1, Node c2) {
	                return Double.compare(c1.getfValue(), c2.getfValue());
	            }
	        });
	    }
	    public int aStarSearchClone(Node start, Node goal, char[][] arrayChar, Map<Integer, Node> indexList, List<Node> list) {
	        Map<Integer, Integer> parentNodes = new LinkedHashMap<>();
	        List<Node> openList = new ArrayList<>();
	        List<Integer> closedList = new ArrayList<>();
	        openList.add(start);

	        while (!openList.isEmpty()) {
	            moveDotInTopLinkedList(openList, arrayChar);
	            Node currentNode = openList.get(0);
	            if (reachGoalNode(currentNode, goal)) {
	                return blowTheSnowClone(arrayChar, indexList, parentNodes, currentNode);
	            }
	            openList.remove(0);
	            closedList.add(currentNode.getIndex());
	            LinkedList<Node> neighbor = new LinkedList<>();
	            addMoves(goal, arrayChar, indexList, currentNode, neighbor, parentNodes);
	            for (Node node : neighbor) {
	                if (closedList.contains(node.getIndex())) {
	                    continue;
	                }
	                if (!isExistedNode(node, openList)) {
	                    char ch = arrayChar[node.getRowX()][node.getColumnY()];
	                    if( ch== 46 || isHouses(ch)) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setgValue(currentNode.getgValue());
	                        node.setfValue(currentNode.getfValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                    }else {
	                        double heuristic = calculatHValue(node, goal);
	                        node.sethValue(heuristic);
	                        node.setgValue(currentNode.getgValue() + 15);
	                        node.setfValue(node.getgValue() + heuristic);
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    }
	                    
	                    /*
	                    char currentCh = arrayChar[currentNode.getRowX()][currentNode.getColumnY()];
	                    char nodeCh = arrayChar[node.getRowX()][node.getColumnY()];
	                    if (isHouses(nodeCh)) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setfValue(currentNode.getfValue());
	                        node.setgValue(currentNode.getgValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    } else if (isHouses(currentCh) && nodeCh == DOT) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setfValue(currentNode.getfValue());
	                        node.setgValue(currentNode.getgValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    } else if (currentCh == DOT && nodeCh != DOT
	                            && nextNodeSnow(arrayChar, currentNode, node, indexList, closedList)) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setgValue(currentNode.getgValue());
	                        node.setfValue(node.gethValue() + node.getgValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    }
	                    else if (currentCh == DOT && nodeCh == DOT) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setfValue(currentNode.getfValue());
	                        node.setgValue(currentNode.getgValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    } else if (currentCh != DOT && nodeCh == DOT) {
	                        node.sethValue(currentNode.gethValue());
	                        node.setfValue(currentNode.getfValue());
	                        node.setgValue(currentNode.getgValue());
	                        openList.add(node);
	                        parentNodes.put(node.getIndex(), currentNode.getIndex());
	                        continue;
	                    }
	                    else {
	                        
	                    }*/
	                } else {
	                    // Update parent nodes if node in Neighbor node existed in Closed Node
	                    double tentativegScore = currentNode.getgValue() + 15;
	                    //Node neibourInList = getNodeWithIndex(openList, node.getIndex());
	                    if(Double.compare(tentativegScore, node.getgValue()) == 0 || Double.compare(tentativegScore, node.getgValue()) > 0) {
	                        continue;
	                    }
	                    parentNodes.put(node.getIndex(), currentNode.getIndex());
	                }
	            }
	        }
	        return 0;
	    }

	    
	     
	    public boolean isHouses(char currentCh) {
	        return currentCh == 65 || currentCh == 66 || currentCh == 67 || currentCh == 68;
	    }

	    public List<Node> getNodeSameF(LinkedList<Node> openList, Node currentNode) {
	        List<Node> result = new LinkedList<>();
	        for (Node node : openList) {
	            if (node.gethValue() == currentNode.gethValue() && node.getfValue() == currentNode.getfValue()) {
	                result.add(node);
	            }
	        }
	        return result;
	    }

	    public boolean nextNodeSnow(char[][] arrayChar, Node parentNode, Node currentNode, Map<Integer, Node> indexList,
	            LinkedList<Node> closedList) {
	        List<Node> list = new ArrayList<>();
	        moveTop(list, currentNode, arrayChar, indexList);
	        moveRight(list, currentNode, arrayChar, indexList);
	        moveBottom(list, currentNode, arrayChar, indexList);
	        moveLeft(list, currentNode, arrayChar, indexList);
	        boolean flag = true;
	        for (Node node : list) {
	            if(node.getIndex() != currentNode.getIndex()) {
	                if(arrayChar[node.getRowX()][node.getColumnY()] == 111 || arrayChar[node.getRowX()][node.getColumnY()] == 35) {
	                    flag = false;
	                }
	            }
	        }
	        return flag;
	    }

	    // Add move position
	    public void addMoves(Node goal, char[][] arrayChar, Map<Integer, Node> indexList, Node currentNode,
	            LinkedList<Node> neighbor, Map<Integer, Integer> parentNodes) {
	        List<Node> list = new ArrayList<>();
	        moveTop(list, currentNode, arrayChar, indexList);
	        moveRight(list, currentNode, arrayChar, indexList);
	        moveBottom(list, currentNode, arrayChar, indexList);
	        moveLeft(list, currentNode, arrayChar, indexList);
	        if(!parentNodes.isEmpty()) {
	            int parendIndex = parentNodes.get(currentNode.getIndex());
	            Node parendNode = indexList.get(parendIndex);
	            for (Node node : list) {
	                int nodeX = node.getRowX();
	                int nodeY = node.getColumnY();
	                if (nodeX == currentNode.getRowX() && nodeX == parendNode.getRowX()) {
	                    neighbor.add(0, node);
	                } else if (nodeY == currentNode.getColumnY() && nodeY == parendNode.getColumnY()) {
	                    neighbor.add(0, node);
	                } else {
	                    neighbor.add(node);
	                }
	            }
	        }else {
	            neighbor.addAll(list);
	        }
	    }

	    public void moveTop(List<Node> neighbor, Node currentNode, char[][] arrayChar, Map<Integer, Node> indexList) {
	        int x = currentNode.getRowX() - 1;
	        int y = currentNode.getColumnY();
	        if (x >= 0 && !isObstacle(arrayChar, x, y)) { // 35 is #
	            neighbor.add(indexList.get(currentNode.getIndex() - colLength));
	            return;
	        }
	    }

	    public void moveRight(List<Node> neighbor, Node currentNode, char[][] arrayChar, Map<Integer, Node> indexList) {
	        int x = currentNode.getRowX();
	        int y = currentNode.getColumnY() + 1;
	        if (y < colLength && !isObstacle(arrayChar, x, y)) {
	            neighbor.add(indexList.get(currentNode.getIndex() + 1));
	            return;
	        }
	    }

	    public void moveLeft(List<Node> neighbor, Node currentNode, char[][] arrayChar, Map<Integer, Node> indexList) {
	        int x = currentNode.getRowX();
	        int y = currentNode.getColumnY() - 1;
	        if (y >= 0 && !isObstacle(arrayChar, x, y)) {
	            neighbor.add(indexList.get(currentNode.getIndex() - 1));
	            return;
	        }
	    }

	    public void moveBottom(List<Node> neighbor, Node currentNode, char[][] arrayChar, Map<Integer, Node> indexList) {
	        int x = currentNode.getRowX() + 1;
	        int y = currentNode.getColumnY();
	        if (x < rowLength && !isObstacle(arrayChar, x, y)) {
	            neighbor.add(indexList.get(currentNode.getIndex() + colLength));
	            return;
	        }
	    }

	    public int blowTheSnowClone(char[][] arrayChar, Map<Integer, Node> indexList, Map<Integer, Integer> parentNodes,
	            Node currentNode) {
	        int count = 0;
	        int key = currentNode.getIndex();
	        while (parentNodes.containsKey(key)) {
	            int parentIndex = parentNodes.get(key);
	            Node node = indexList.get(parentIndex);
	            int x = node.getRowX();
	            int y = node.getColumnY();
	            if (arrayChar[x][y] == 111) {
	                arrayChar[x][y] = DOT;
	                count++;
	            }
	            key = parentIndex;
	        }
	        /*for (int k = 0; k < rowLength; k++) {
	            for (int j = 0; j < colLength; j++) {
	                System.out.print(arrayChar[k][j]);
	            }
	            System.out.println();
	        }
	        System.out.println();*/
	        return count;
	    }

	    public char[][] cloneChar(char[][] myArray) {
	        char[][] copyArray = new char[rowLength][colLength];
	        for (int i = 0; i < rowLength; ++i) {
	            copyArray[i] = myArray[i].clone();
	        }
	        return copyArray;
	    }

	    public int getNodeIndex(LinkedList<Node> indexList, int x, int y) {
	        for (Node node : indexList) {
	            if (node.getRowX() == x && node.getColumnY() == y) {
	                return node.getIndex();
	            }
	        }
	        return 0;
	    }

	    public Node getNodeWithIndex(List<Node> indexList, int index) {
	        for (Node node : indexList) {
	            if (node.getIndex() == index) {
	                return node;
	            }
	        }
	        return null;
	    }

	    public boolean reachGoalNode(Node current, Node goal) {
	        if(current.getIndex() == goal.getIndex()) {
	            goal.setVisted(true);
	            return true;
	        }
	        return false;
	    }

	    public void reachHourse(Node current, char[][] arrayChar, List<Node> list) {
	        for (Node node : list) {
	            if (current.getRowX() == node.getRowX() && current.getColumnY() == node.getColumnY()) {
	                node.setVisted(true);
	                return;
	            }
	        }
	    }

	    public boolean reachHourse(Node currentNode, List<Node> list) {
	        for (Node node : list) {
	            if (currentNode.getRowX() == node.getRowX() && currentNode.getColumnY() == node.getColumnY()) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public boolean isObstacle(char[][] arrayChar, int row, int col) {
	        return arrayChar[row][col] == 35;// 35 is #
	    }

	    public boolean isground(char[][] arrayChar, int x, int y) {
	        return arrayChar[x][y] == DOT || arrayChar[x][y] == 111 || arrayChar[x][y] == 65
	                || arrayChar[x][y] == 66 || arrayChar[x][y] == 67 || arrayChar[x][y] == 68;// 46 is . and 111 is o
	    }

	    // CHeck existed node in closeList
	    public boolean isExistedNode(Node currentNode, List<Node> closedList) {
	        for (Node node : closedList) {
	            if (node.getIndex() == currentNode.getIndex()) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public double calculatHValue(Node source, Node des) {
	        int sourceX = source.getRowX();
	        int sourceY = source.getColumnY();
	        int desX = des.getRowX();
	        int desY = des.getColumnY();
	        return Math.abs(sourceX - desX) + Math.abs(sourceY - desY);
	    }

	    public double calculateHValueWithElucine(Node source, Node des) {
	        int sourceX = source.getRowX();
	        int sourceY = source.getColumnY();
	        int desX = des.getRowX();
	        int desY = des.getColumnY();
	        return Math.sqrt((Math.abs(sourceX - desX) * Math.abs(sourceX - desX))
	                + (Math.abs(sourceY - desY) * Math.abs(sourceY - desY)));
	    }
	    
	    public double calculateH(List<Node> list, Node currentNode, Node start) {
	            int length = list.size();
	            int heuristic = 0;
	            for (int i = 0; i < length; i++) {
	                Node node = list.get(i);
	                if(!node.isVisted() && node.getIndex() != start.getIndex()) {
	                    heuristic += calculatHValue(currentNode, node);
	                }
	            }
	            return heuristic;
	        }
	     
	    public double calculatHValueForNeigbour(Node currentNode, Node startNode, Node des) {
	        double currentHeuristic = calculatHValue(currentNode, des);
	        
	        double nextHeuristic;
	        if(startNode == null) {
	            nextHeuristic = 0.0;
	        }else {
	         nextHeuristic = calculatHValue(currentNode, startNode);
	        }

	        return currentHeuristic + nextHeuristic;
	    }

	    static class Node {
	        String name;
	        int index;
	        int rowX;
	        int columnY;
	        double gValue;
	        double hValue;
	        boolean isVisted;

	        /**
	         * @return the name
	         */
	        public String getName() {
	            return name;
	        }

	        /**
	         * @param name
	         *            the name to set
	         */
	        public void setName(String name) {
	            this.name = name;
	        }

	        /**
	         * @return the isVisted
	         */
	        public boolean isVisted() {
	            return isVisted;
	        }

	        /**
	         * @param isVisted
	         *            the isVisted to set
	         */
	        public void setVisted(boolean isVisted) {
	            this.isVisted = isVisted;
	        }

	        /**
	         * @return the index
	         */
	        public int getIndex() {
	            return index;
	        }

	        /**
	         * @param index
	         *            the index to set
	         */
	        public void setIndex(int index) {
	            this.index = index;
	        }

	        double fValue;

	        public Node() {

	        }

	        /**
	         * @return the row
	         */
	        public int getRowX() {
	            return rowX;
	        }

	        /**
	         * @param row
	         *            the row to set
	         */
	        public void setRowX(int rowX) {
	            this.rowX = rowX;
	        }

	        /**
	         * @return the column
	         */
	        public int getColumnY() {
	            return columnY;
	        }

	        /**
	         * @param column
	         *            the column to set
	         */
	        public void setColumnY(int columnY) {
	            this.columnY = columnY;
	        }

	        /**
	         * @return the gValue
	         */
	        public double getgValue() {
	            return gValue;
	        }

	        /**
	         * @param gValue
	         *            the gValue to set
	         */
	        public void setgValue(double gValue) {
	            this.gValue = gValue;
	        }

	        /**
	         * @return the hValue
	         */
	        public double gethValue() {
	            return hValue;
	        }

	        /**
	         * @param hValue
	         *            the hValue to set
	         */
	        public void sethValue(double hValue) {
	            this.hValue = hValue;
	        }

	        /**
	         * @return the fValue
	         */
	        public double getfValue() {
	            return fValue;
	        }

	        /**
	         * @param fValue
	         *            the fValue to set
	         */
	        public void setfValue(double fValue) {
	            this.fValue = fValue;
	        }

	    }
}
