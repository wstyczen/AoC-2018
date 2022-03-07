import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1 {
    public ArrayList<Integer> input = new ArrayList<>();
    public LinkedList<Node> nodes = new LinkedList<>();
    public int index=0;

    public Part1(String fileName) {
        readInput(fileName);
        readNodes(null);
    }

    public void readInput(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            input.addAll(
                    Stream.of(line.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList())
            );
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public class Node {
        private int [] metadata;
        private Node [] children;

        public Node(int metadataSize, int childrenSize) {
            metadata = new int[metadataSize];
            children = new Node[childrenSize];
        }
        public void addChild(Node node) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    children[i] = node;
                    break;
                }
            }
        }
        public int getTotal() {
            int total = 0;
            for (int meta : metadata) {
                total += meta;
            }
            return total;
        }
        public int addMeta(int start) {
            for (int i = 0; i < metadata.length; i++) {
                metadata[i] = input.get(start++);
            }
            return start;
        }
        public int getValue() {
            if (children.length == 0)
                return getTotal();
            int value = 0;
            for (int meta : metadata) {
                if (meta - 1 >= 0 && meta - 1 < children.length) {
                    value += children[meta - 1].getValue();
                }
            }
            return value;
        }
    }

    public int Part1() {
        int sum = 0;
        int childrenSize = input.get(index++);
        int metadataEntries = input.get(index++);
        while (childrenSize-- > 0) {
            sum += Part1();
        }
        while (metadataEntries-- > 0) {
            sum += input.get(index);
            index++;
        }
        return sum;
    }

    public void readNodes(Node parent) {
        int childrenSize = input.get(index++);
        int metadataSize = input.get(index++);
        Node thisNode = new Node(metadataSize, childrenSize);
        nodes.add(thisNode);
        if (parent != null)
            parent.addChild(thisNode);
        while (childrenSize-- > 0) {
            readNodes(thisNode);
        }
        index = thisNode.addMeta(index);
    }

    public int Part2() {
        Node root = nodes.get(0);
        return root.getValue();
    }

}
