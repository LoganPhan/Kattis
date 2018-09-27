package logan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

public class RobotProtection {
	private static Node p0;
	public static void main(String[] args) throws IOException {
		BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
		boolean isAlive = true;
		while (isAlive) {
			p0 = new Node();
			Stack<Node> hull = new Stack<>();
			StringTokenizer st = new StringTokenizer(inp.readLine());
			int count = Integer.valueOf(st.nextToken());
			Node[] listNode = new Node[count];
			if (count == 0) {
				isAlive = false;
				inp.close();
				break;
			}
			for (int i = 0; i < count; i++) {
				st = new StringTokenizer(inp.readLine());
				listNode[i] = new Node(Integer.valueOf(st.nextToken()), Integer.valueOf(st.nextToken()));
			}
			if(count <= 2) {
				System.out.println("0.0");
				continue;
			}
			findLowestY(listNode);
			sort(listNode);
			int n = listNode.length;
			int m = 1; // Initialize size of modified array
			   for (int i=1; i<n; i++)
			   {
			       // Keep removing i while angle of i and i+1 is same
			       // with respect to p0
			       while (i < n-1 && orientation(p0, listNode[i],
			    		   listNode[i+1]) == 0)
			          i++;
			       listNode[m] = listNode[i];
			       m++;  // Update size of modified array
			   }
		   if (m < 3) return;
			hull.push(listNode[0]);
			hull.push(listNode[1]);
			hull.push(listNode[2]);
			for (int i = 3; i < m; ++i) {
				Node top = hull.peek();
				Node nextToTop = hull.get(hull.size() - 2);
				while (orientation(nextToTop, top, listNode[i]) != 2) {
					hull.pop();
					top = hull.peek();
					nextToTop = hull.get(hull.size() - 2);
				}
				hull.push(listNode[i]);
			}
			System.out.println(areaOfPolygon(hull));
		}
	}

	static double areaOfPolygon(Stack<Node> hull) {
		double total = 0.0;
		for (int i = 0, j = 1; i < hull.size(); i++, j = (i + 1) % hull.size()) {
			total += (hull.get(i).x * hull.get(j).y) - (hull.get(i).y * hull.get(j).x);
		}
		return Math.abs(total) / 2;
	}

	static void sort(Node[] list) {
		Arrays.sort(list, 1, list.length, new Comparator<Node>() {
			@Override
			public int compare(Node o2, Node o1) {
				int value = orientation(p0, o2, o1);
				if (value == 0) {
					return (distanceSq(p0, o1) >= distanceSq(p0, o2)) ? -1 : 1;
				}
				return (value == 2) ? -1 : 1;
			}
		});
	}

	static int orientation(Node p1, Node p2, Node p3) {
		int value = ((p2.y - p1.y) * (p3.x - p2.x)) - ((p3.y - p2.y) * (p2.x - p1.x));
		// System.out.println("P1: (" + p1.x + "," + p1.y + ") P2: (" + p2.x + "," +
		// p2.y + ") P3: (" + p3.x + "," + p3.y+ ") = " + value);
		if (value == 0)
			return 0;
		return (value > 0) ? 1 : 2;
	}

	static int distanceSq(Node p1, Node p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}

	static void findLowestY(Node[] list) {
		int minY = list[0].y;
		int index = 0;
		int size = list.length;
		for (int i = 1; i < size; i++) {
			Node n = list[i];
			if (minY > n.y || (minY == n.y && list[index].x > n.x)) {
				minY = n.y;
				index = i;
			}
		}
		Node temp = list[index];
		list[index] = list[0];
		list[0] = temp;
		p0 = temp;
	}

	static class Node {
		int x;
		int y;

		Node() {
		}
		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}
}
