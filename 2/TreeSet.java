public class TreeSet {

  private class Node {
    private int value;
    private Node left;
    private Node right;

    private Node (int value) { this.value = value; }
  }

  private Node root;
  private int size;

  public TreeSet() {
    size = 0;
    root = null;
  }

  public void add(int value) {
    Node tmp = new Node(value);
    if (root == null) {
      root = tmp;
      size += 1;
    } else {
      Node node = root;
      while (true) {
        if (value < node.value) {
          if (node.left == null) {
            node.left = tmp;
            size += 1;
            break;
          } else {
            node = node.left;
          }
        } else if (value > node.value) {
          if (node.right == null) {
            node.right = tmp;
            size += 1;
            break;
          } else {
            node = node.right;
          }
        } else {
          return;
        }
      }
    }
  }

  public int size() { return size; }

  public boolean contains(int value) {
    Node node = root;
    while (node != null) {
      if (value < node.value) {
        node = node.left;
      } else if (value > node.value) {
        node = node.right;
      } else {
        return true;
      }
    }
    return false;
  }
}
